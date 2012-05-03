package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.accumulator.Accumulator;
import org.apache.commons.math3.exception.MathArithmeticException;

/**
 * This is an interface of Monte Carlo model.
 *
 */
public interface IMonteCarloModel<TValue, OutValue> {
    /**
     * Adds path samples to statistics
     *
     * @param samples Number of paths to add
     */
    int addSamples(int samples) throws MathArithmeticException;

    /**
     * Adds path sample to statistics until either statistics converges
     * or max steps are reached
     *
     * @param minSamples
     * @param eps
     * @param maxSteps
     * @return
     * @throws MathArithmeticException
     */
    int addSamples(int minSamples, double eps, int maxSteps) throws MathArithmeticException;

    /**
     * Gets statistics summary
     *
     * @return Statistics summary
     */
    Accumulator<TValue, OutValue> getStats();
}
