package cz.paulrz.montecarlo.accumulator;

import cz.paulrz.montecarlo.single.MaxMinClose;
import org.apache.commons.math.util.FastMath;

/**
 * User: paul
 * Date: 3/5/11
 * Time: 09:44 AM
 */
public class MaxMinCloseProbabilityAccumulator implements Accumulator<MaxMinClose> {

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

    public double norm(Accumulator<MaxMinClose> other) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Accumulator<MaxMinClose> deepCopy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private long round(double x) {
        return FastMath.round(x * scale);
    }
}
