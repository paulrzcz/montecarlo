package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.accumulator.Accumulator;
import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;

/**
 * User: paul
 * Date: 9/5/11
 * Time: 12:29 PM
 */
public final class SingleMcFactory {

    public static <T> MonteCarloModel<T> createSimpleMc(
            StochasticProcess1D sp, double duration, int timeSteps,
            PathValuation<T> valuation, Accumulator<T> statistics) {
        return new MonteCarloModel<T>(new FastGaussianRandomGenerator(),
                sp, duration, timeSteps, valuation, statistics, false, false);
    }

    public static <T> MonteCarloModel<T> createSobolMc(
            StochasticProcess1D sp, double duration, int timeSteps,
            PathValuation<T> valuation, Accumulator<T> statistics) throws Exception {
        return new MonteCarloModel<T>(sp, duration, timeSteps, valuation, statistics, true);
    }

    public static <T> MonteCarloModel<T> createBridgedMc(
            StochasticProcess1D sp, double duration, int timeSteps,
            PathValuation<T> valuation, Accumulator<T> statistics) {

        return new MonteCarloModel<T>(new FastGaussianRandomGenerator(),
                sp, duration, timeSteps, valuation, statistics, true, true);

    }
}
