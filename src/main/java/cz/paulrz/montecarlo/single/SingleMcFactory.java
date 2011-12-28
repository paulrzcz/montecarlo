package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.accumulator.Accumulator;
import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;

/**
 * Factory class for common Monte-Carlo models
 */
public final class SingleMcFactory {

    public static <T, O> IMonteCarloModel<T, O> createSimpleMc(
            StochasticProcess1D sp, double duration, int timeSteps,
            PathValuation<T> valuation, Accumulator<T, O> statistics) {
        return new MonteCarloModel<T, O>(new FastGaussianRandomGenerator(),
                sp, duration, timeSteps, valuation, statistics, false, false);
    }

    public static <T, O> IMonteCarloModel<T, O> createSobolMc(
            StochasticProcess1D sp, double duration, int timeSteps,
            PathValuation<T> valuation, Accumulator<T, O> statistics) throws Exception {
        return new MonteCarloModel<T, O>(sp, duration, timeSteps, valuation, statistics, true);
    }

    public static <T, O> IMonteCarloModel<T, O> createBridgedMc(
            StochasticProcess1D sp, double duration, int timeSteps,
            PathValuation<T> valuation, Accumulator<T, O> statistics) {

        return new MonteCarloModel<T, O>(new FastGaussianRandomGenerator(),
                sp, duration, timeSteps, valuation, statistics, true, true);

    }
}
