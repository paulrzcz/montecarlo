package cz.paulrz.montecarlo.multi;

import cz.paulrz.montecarlo.accumulator.Accumulator;
import cz.paulrz.montecarlo.parallel.CpuPool;
import cz.paulrz.montecarlo.random.RandomGeneratorFactory;
import cz.paulrz.montecarlo.single.IMonteCarloModel;
import org.apache.commons.math.MathException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: paul
 * Date: 1/10/11
 * Time: 17:58 PM
 */
public final class ParallelMonteCarloModel<TValue, OutValue> implements IMonteCarloModel<TValue, OutValue> {
    private final Accumulator<TValue, OutValue> summary;
    private final RandomGeneratorFactory factory;
    private final PathValuation<TValue> pathValuation;
    private final boolean useAntithetic;
    private final GenericProcess process;
    private final double duration;
    private final int timeSteps;


    /**
     * Constructor of Monte Carlo model
     *
     * @param random     Underlying random number generator
     * @param process    Underlying stochastic process
     * @param duration   Duration of paths in time units
     * @param timeSteps  Path discretization time step
     * @param valuation  Path valuation function
     * @param statistics Statistics summary
     */
    public ParallelMonteCarloModel(RandomGeneratorFactory random,
                                   GenericProcess process, double duration, int timeSteps,
                                   PathValuation<TValue> valuation, Accumulator<TValue, OutValue> statistics,
                                   boolean useAntithetic) {
        this.factory = random;
        this.summary = statistics;
        this.useAntithetic = useAntithetic;
        this.pathValuation = valuation;
        this.process = process;
        this.duration = duration;
        this.timeSteps = timeSteps;
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
        return 0;
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
            final PathGenerator pathGenerator = createPathGenerator();
            final int allSamples = useAntithetic ? samples * 2 : samples;
            final List<TValue> result = new ArrayList<TValue>(allSamples);

            for (int i = 0; i < allSamples; ++i) {
                final Path path = pathGenerator.next();
                final TValue pathValue = pathValuation.value(path);
                result.add(pathValue);
            }

            return result;
        }

        private PathGenerator createPathGenerator() {
            if (factory == null) {
                try {
                    return new SobolPathGenerator(process, timeSteps, duration);
                } catch (Exception e) {
                    // fallback...
                }
            }
            final NormalizedRandomGenerator random = factory.newGenerator();

            if (useAntithetic)
                return new AntitheticPathGenerator(process, timeSteps, duration, random);
            else
                return new SimplePathGenerator(process, timeSteps, duration, random);
        }
    }
}
