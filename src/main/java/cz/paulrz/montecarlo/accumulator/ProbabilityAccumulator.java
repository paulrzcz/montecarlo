package cz.paulrz.montecarlo.accumulator;

import org.apache.commons.math.util.FastMath;

/**
 * User: paul
 * Date: 3/5/11
 * Time: 09:33 AM
 */
public class ProbabilityAccumulator implements Accumulator<Double> {

    private final double x;
    private final long xlong;
    private long total = 0;
    private long hits  = 0;
    private final long scale;

    private ProbabilityAccumulator(double x, long xlong, long total, long hits, long scale)
    {
        this.x= x;
        this.xlong = xlong;
        this.total = total;
        this.hits = hits;
        this.scale = scale;
    }

    public ProbabilityAccumulator(double x, int precision) {
        this.x = x;
        scale = FastMath.round(FastMath.pow(10.0, precision));
        xlong = round(x);
    }

    public ProbabilityAccumulator(double x) {
        this.x = x;
        scale = 10000;
        xlong = round(x);
    }

    public double probability() {
        if (total == 0)
            return 0.0;

        return hits*1.0/total;
    }

    public void addValue(Double value) {
        final long v = round(value);
        total++;

        if (xlong == v)
            hits++;
    }

    @Override
    public String toString() {
        return "Hits="+hits+"; Total="+total;
    }

    public double norm(Accumulator<Double> other) {
        ProbabilityAccumulator pa = (ProbabilityAccumulator)other;
        if (pa!=null)
        {
            return this.probability() - pa.probability();
        }

        return 1e8;
    }

    public Accumulator<Double> deepCopy() {
        return new ProbabilityAccumulator(x, xlong, total, hits, scale);
    }

    private long round(double x) {
        return FastMath.round(x*scale);
    }
}
