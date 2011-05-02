package cz.paulrz.montecarlo.accumulator;

import cz.paulrz.montecarlo.single.MaxMinClose;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.commons.math.util.FastMath;

/**
 * Created by IntelliJ IDEA.
 * User: paul
 * Date: 12/4/11
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class MaxMinCloseAccumulator implements Accumulator<MaxMinClose> {

    public MaxMinCloseAccumulator() {
        max   = new SummaryStatistics();
        min   = new SummaryStatistics();
        close = new SummaryStatistics();
    }

    public MaxMinCloseAccumulator(MaxMinCloseAccumulator a) {
        max   = a.max.copy();
        min   = a.min.copy();
        close = a.close.copy();
    }

    public SummaryStatistics max = new SummaryStatistics();
    public SummaryStatistics min = new SummaryStatistics();
    public SummaryStatistics close = new SummaryStatistics();

    public void addValue(MaxMinClose value) {
        max.addValue(value.max);
        min.addValue(value.min);
        close.addValue(value.close);
    }

    public double norm(Accumulator<MaxMinClose> other) {
        MaxMinCloseAccumulator that = (MaxMinCloseAccumulator) other;

        return FastMath.abs(max.getMean() - that.max.getMean()) +
                FastMath.abs(min.getMean() - that.min.getMean()) +
                FastMath.abs(close.getMean() - that.close.getMean());
    }

    public MaxMinCloseAccumulator deepCopy() {
        return new MaxMinCloseAccumulator(this);
    }

    public String toString() {
        return "Max stats:\n"+max.toString()
                + "\nMin stats:\n"+min.toString() + "\nClose stats:\n" + close.toString();
    }
}
