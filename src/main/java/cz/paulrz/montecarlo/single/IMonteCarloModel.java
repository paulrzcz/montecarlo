package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.accumulator.Accumulator;
import org.apache.commons.math.MathException;

/**
 * User: paul
 * Date: 29/9/11
 * Time: 08:34 AM
 */
public interface IMonteCarloModel<TValue> {
    /**
     * Adds path samples to statistics
     *
     * @param samples Number of paths to add
     */
    int addSamples(int samples) throws MathException;

    int addSamples(int minSamples, double eps, int maxSteps) throws MathException;

    /**
     * Gets statistics summary
     *
     * @return Statistics summary
     */
    Accumulator<TValue> getStats();
}
