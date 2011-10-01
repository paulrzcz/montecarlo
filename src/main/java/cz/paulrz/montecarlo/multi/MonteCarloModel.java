package cz.paulrz.montecarlo.multi;

import cz.paulrz.montecarlo.accumulator.Accumulator;
import cz.paulrz.montecarlo.single.IMonteCarloModel;
import org.apache.commons.math.MathException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 12:43 PM
 */
public class MonteCarloModel<TValue> implements IMonteCarloModel<TValue> {
    private final Accumulator<TValue> summary;
    private final PathGenerator pathGenerator;
    private final PathValuation<TValue> pathValuation;
    private final boolean useAntithetic;

    /**
     * Constructor of Monte Carlo model
     *
     * @param random Underlying random number generator
     * @param process Underlying stochastic process
     * @param duration Duration of paths in time units
     * @param timeSteps Path discretization time step
     * @param valuation Path valuation function
     * @param statistics Statistics summary
     */
    public MonteCarloModel(NormalizedRandomGenerator random,
            GenericProcess process, double duration, int timeSteps,
            PathValuation<TValue> valuation, Accumulator<TValue> statistics,
            boolean useAntithetic) {
        this.summary = statistics;
        this.useAntithetic = useAntithetic;
        if (useAntithetic)
            this.pathGenerator = new AntitheticPathGenerator(process, timeSteps, duration, random);
        else
            this.pathGenerator = new SimplePathGenerator(process, timeSteps, duration,
                    random);
        this.pathValuation = valuation;
    }

    /**
     *
     * Constructor of Quasi Monte Carlo Model with Sobol generator
     *
     * @param process Underlying stochastic process
     * @param duration Duration of paths in time units
     * @param timeSteps Path discretization time step
     * @param valuation Path valuation function
     * @param statistics Statistics summary
     */
    public MonteCarloModel(GenericProcess process, double duration, int timeSteps,
                           PathValuation<TValue> valuation, Accumulator<TValue> statistics) throws Exception {
        this.summary = statistics;
        this.pathGenerator = new SobolPathGenerator(process, timeSteps, duration);
        this.pathValuation = valuation;
        this.useAntithetic = false;
    }

    /**
     * Adds path samples to statistics
     *
     * @param samples Number of paths to add
     */
    public int addSamples(int samples) throws MathException {
        final int allSamples = useAntithetic ? samples*2 : samples;

        for (int i = 0; i < allSamples; ++i) {
            final Path path = pathGenerator.next();
            final TValue pathValue = pathValuation.value(path);
            summary.addValue(pathValue);
        }
        return samples;
    }

    public int addSamples(int minSamples, double eps, int maxSteps) throws MathException {
        addSamples(minSamples);
        Accumulator<TValue> prev = summary.deepCopy();
        int steps = 1;
        while(steps==1 || summary.norm(prev) > eps) {
            prev = summary.deepCopy();
            addSamples(minSamples);
            steps++;

            if (steps>maxSteps)
                return steps*minSamples;
        }
        return steps*minSamples;
    }

    /**
     * Gets statistics summary
     *
     * @return Statistics summary
     */
    public Accumulator<TValue> getStats() {
        return summary;
    }
}
