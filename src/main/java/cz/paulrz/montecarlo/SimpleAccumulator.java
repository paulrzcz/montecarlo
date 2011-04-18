package cz.paulrz.montecarlo;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.commons.math.util.FastMath;

/**
 * Created by IntelliJ IDEA.
 * User: paul
 * Date: 12/4/11
 * Time: 09:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAccumulator implements Accumulator<Double> {
    public SimpleAccumulator() {
        stats = new SummaryStatistics();
    }

    public SimpleAccumulator(SimpleAccumulator s) {
        stats = s.stats.copy();
    }

    public SummaryStatistics stats;

    public void addValue(Double value) {
        stats.addValue(value);
    }

    public double norm(Accumulator<Double> other) {
        SimpleAccumulator that = (SimpleAccumulator) other;
        return FastMath.abs(stats.getMean()-that.stats.getMean());
    }

    public SimpleAccumulator deepCopy() {
        return new SimpleAccumulator(this);
    }
}
