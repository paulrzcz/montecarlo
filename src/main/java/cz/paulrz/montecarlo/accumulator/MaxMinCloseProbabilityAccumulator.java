package cz.paulrz.montecarlo.accumulator;

import cz.paulrz.montecarlo.single.MaxMinClose;
import org.apache.commons.math3.util.FastMath;

/**
 * User: paul
 * Date: 3/5/11
 * Time: 09:44 AM
 */
public class MaxMinCloseProbabilityAccumulator implements Accumulator<MaxMinClose, Double> {

    private final double close;
    private final long lclose;
    private final double min;
    private final long lmin;
    private final double max;
    private final long lmax;
    private final long scale;

    private long total = 0;
    private long chits = 0;
    private long minhits = 0;
    private long maxhits = 0;

    public MaxMinCloseProbabilityAccumulator(double close, double min, double max)
    {
        scale = 10000;
        this.close = close;
        lclose = round(close);
        this.min = min;
        lmin = round(min);
        this.max = max;
        lmax = round(max);
    }

    public MaxMinCloseProbabilityAccumulator(MaxMinCloseProbabilityAccumulator that) {
        scale = that.scale;
        close = that.close;
        lclose = that.lclose;
        min = that.min;
        lmin = that.lmin;
        max = that.max;
        lmax = that.lmax;

        total = that.total;
        chits = that.chits;
        minhits = that.minhits;
        maxhits = that.maxhits;
    }

    public double pmax() {
        if (total==0)
            return 0.0;

        return maxhits*1.0/total;
    }

    public double pmin() {
        if (total==0)
            return 0.0;

        return minhits*1.0/total;
    }

    public double pclose() {
        if (total==0)
            return 0.0;

        return chits*1.0/total;
    }

    public void addValue(MaxMinClose value) {
        total++;
        final long vclose = round(value.close);
        final long vmin   = round(value.min);
        final long vmax   = round(value.max);

        if (vclose == lclose)
            chits++;
        if (vmin == lmin)
            minhits++;
        if (vmax == lmax)
            maxhits++;
    }

    public Double value() {
        return pclose();
    }

    public double norm(Accumulator<MaxMinClose, Double> other) {
        MaxMinCloseProbabilityAccumulator that = (MaxMinCloseProbabilityAccumulator) other;

        return FastMath.abs(that.pclose() - pclose()) +
                FastMath.abs(that.pmax() - pmax()) +
                FastMath.abs(that.pmin() - pmin());
    }

    public Accumulator<MaxMinClose, Double> deepCopy() {
        return new MaxMinCloseProbabilityAccumulator(this);
    }

    private long round(double x) {
        return FastMath.round(x * scale);
    }
}
