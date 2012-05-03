package cz.paulrz.montecarlo.accumulator;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.FastMath;

/**
 * Accumulator that calculates a median
 */
public class MedianAccumulator implements Accumulator<Double, Double> {

    private final DescriptiveStatistics statistics;

    /**
     * Default constructor
     */
    public MedianAccumulator() {
        statistics = new DescriptiveStatistics();
    }

    /**
     * Copy constructor
     * @param src  Source accumulator
     */
    public MedianAccumulator(MedianAccumulator src) {
        statistics = src.statistics.copy();
    }

    /**
     * Add single value
     * @param value Path value
     */
    public void addValue(Double value) {
        statistics.addValue(value);
    }


    public Double value() {
        return statistics.getPercentile(50.0);
    }


    /**
     * Calculates a distance between two accumulators.
     * Used for converge testing. Might be quite expensive to calculate
     * @param other Second accumulator
     * @return Distance
     */
    public double norm(Accumulator<Double, Double> other) {
        return FastMath.abs(value() - other.value());
    }

    public Accumulator<Double, Double> deepCopy() {
        return new MedianAccumulator(this);
    }
}
