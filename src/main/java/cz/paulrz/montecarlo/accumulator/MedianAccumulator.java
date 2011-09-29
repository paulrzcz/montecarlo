package cz.paulrz.montecarlo.accumulator;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

/**
 * User: paul
 * Date: 29/9/11
 * Time: 16:22 PM
 */
public class MedianAccumulator implements Accumulator<Double> {

    private DescriptiveStatistics statistics = new DescriptiveStatistics();

    public void addValue(Double value) {
        statistics.addValue(value);
    }

    public double value() {
        return statistics.getPercentile(50.0);
    }

    public double norm(Accumulator<Double> other) {
        return 0.0;
    }

    public Accumulator<Double> deepCopy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
