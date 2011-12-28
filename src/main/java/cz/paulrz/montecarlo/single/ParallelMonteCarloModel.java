package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.accumulator.Accumulator;
import cz.paulrz.montecarlo.parallel.CpuPool;
import cz.paulrz.montecarlo.random.RandomGeneratorFactory;
import org.apache.commons.math.MathException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: paul
 * Date: 29/9/11
 * Time: 08:25 AM
 */
public final class ParallelMonteCarloModel<TValue, OutValue> implements IMonteCarloModel<TValue, OutValue> {
    private final Accumulator<TValue, OutValue> summary;
    private final PathValuation<TValue> pathValuation;
    private final RandomGeneratorFactory randomFactory;
    private final boolean useAntithetic;
    private final boolean useBridge;
    private final GenericProcess1D process;
    private final double duration;
    private final int timeSteps;

    public ParallelMonteCarloModel(RandomGeneratorFactory random,
                                   GenericProcess1D process, double duration, int timeSteps,
                                   PathValuation<TValue> valuation, Accumulator<TValue, OutValue> statistics,
                                   boolean useAntithetic, boolean useBridge) {
        this.summary = statistics;
        this.useAntithetic = useAntithetic;
        this.useBridge = useBridge;
        this.randomFactory = random;
        this.duration = duration;
        this.process = process;
        this.timeSteps = timeSteps;
        this.pathValuation = valuation;
    }


    public int addSamples(int samples) throws MathException {
        List<Future<List<TValue>>> executionList = new ArrayList<Future<List<TValue>>>(CpuPool.numOfCpu);
        final int samplesPerPartition = samples / CpuPool.numOfCpu;

        for (int i = 0; i < CpuPool.numOfCpu; ++i) {
            Callable<List<TValue>> callable = new ValuatedPathsWorker(samplesPerPartition);
            Future<List<TValue>> submit = CpuPool.executorService.submit(callable);
            executionList.add(submit);
        }

        for (Future<List<TValue>> future : executionList) {
            try {
                List<TValue> result = future.get();
                for (TValue value : result) {
                    summary.addValue(value);
                }
            } catch (InterruptedException e) {
                throw new MathException(e);
            } catch (ExecutionException e) {
                throw new MathException(e);
            }


        }

        return samplesPerPartition * CpuPool.numOfCpu;
    }

    public int addSamples(int minSamples, double eps, int maxSteps) throws MathException {
        throw new MathException("Not implemented!");
    }

    public Accumulator<TValue, OutValue> getStats() {
        return summary;
    }

    private final class ValuatedPathsWorker implements Callable<List<TValue>> {
        private final int samples;

        public ValuatedPathsWorker(int samples) {
            this.samples = samples;
        }

        public List<TValue> call() throws Exception {
            final PathGenerator1D pathGenerator = createPathGenerator();
            final int allSamples = useAntithetic ? samples * 2 : samples;
            final List<TValue> result = new ArrayList<TValue>(allSamples);

            for (int i = 0; i < allSamples; ++i) {
                final Path path = pathGenerator.next();
                final TValue pathValue = pathValuation.value(path);
                result.add(pathValue);
            }

            return result;
        }

        private PathGenerator1D createPathGenerator() {
            if (randomFactory == null){
                try {
                    return new SobolPathGenerator1D(process, timeSteps, duration, useBridge);
                } catch (Exception e) {
                    // fallback...
                }
            }

            final NormalizedRandomGenerator random = randomFactory.newGenerator();

            if (useAntithetic && useBridge)
                return new AntitheticBridgedPathGenerator1D(process, timeSteps,
                        duration, random);
            else if (useAntithetic && !useBridge)
                return new AntitheticPathGenerator1D(process, timeSteps,
                        duration, random);
            else if (!useAntithetic && useBridge)
                return new BridgedPathGenerator1D(process, timeSteps, duration, random);
            else
                return new SimplePathGenerator1D(process, timeSteps, duration, random);

        }
    }
}
