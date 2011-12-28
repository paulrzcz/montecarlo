package cz.paulrz.montecarlo.accumulator;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.commons.math.util.FastMath;

/**
 * Simple accumulator that collects double values in SummaryStatistics class
 */
public class SimpleAccumulator implements Accumulator<Double, SummaryStatistics> {
    public SimpleAccumulator() {
        stats = new SummaryStatistics();
    }

    public SimpleAccumulator(SimpleAccumulator s) {
        stats = s.stats.copy();
    }

    public SummaryStatistics stats;

    public void addValue(final Double value) {
        stats.addValue(value);
    }

    public SummaryStatistics value() {
        return stats;
    }

    public double norm(final Accumulator<Double, SummaryStatistics> other) {
        final SimpleAccumulator that = (SimpleAccumulator) other;
        return FastMath.abs(stats.getMean()-that.stats.getMean());
    }

    public SimpleAccumulator deepCopy() {
        return new SimpleAccumulator(this);
    }
}
