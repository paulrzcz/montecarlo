package cz.paulrz.montecarlo.accumulator;

import cz.paulrz.montecarlo.single.MaxMinClose;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.FastMath;

/**
 * Accumulator that collects statistics over 3 parameters:
 * maximum, minimum and close value of path
 */
public class MaxMinCloseAccumulator implements Accumulator<MaxMinClose, SummaryStatistics> {

    /**
     * Default constructor
     */
    public MaxMinCloseAccumulator() {
        max   = new SummaryStatistics();
        min   = new SummaryStatistics();
        close = new SummaryStatistics();
    }

    /**
     * Copy constructor
     * @param a Source accumulator
     */
    public MaxMinCloseAccumulator(MaxMinCloseAccumulator a) {
        max   = a.max.copy();
        min   = a.min.copy();
        close = a.close.copy();
    }

    public SummaryStatistics max = new SummaryStatistics();
    public SummaryStatistics min = new SummaryStatistics();
    public SummaryStatistics close = new SummaryStatistics();


    /**
     * Add single value
     * @param value Path value
     */
    public void addValue(MaxMinClose value) {
        max.addValue(value.max);
        min.addValue(value.min);
        close.addValue(value.close);
    }

    /**
     * Provides a close statistics
     * @return close field
     */
    public SummaryStatistics value() {
        return close;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Calculates a distance between two accumulators.
     * Used for converge testing
     * @param other Second accumulator
     * @return Distance
     */
    public double norm(Accumulator<MaxMinClose, SummaryStatistics> other) {
        MaxMinCloseAccumulator that = (MaxMinCloseAccumulator) other;

        return FastMath.abs(max.getMean() - that.max.getMean()) +
                FastMath.abs(min.getMean() - that.min.getMean()) +
                FastMath.abs(close.getMean() - that.close.getMean());
    }

    /**
     * Deep copy
     * @return Accumulator copy
     */
    public MaxMinCloseAccumulator deepCopy() {
        return new MaxMinCloseAccumulator(this);
    }

    public String toString() {
        return "Max stats:\n"+max.toString()
                + "\nMin stats:\n"+min.toString() + "\nClose stats:\n" + close.toString();
    }
}
