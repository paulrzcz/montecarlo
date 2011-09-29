package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.accumulator.MedianAccumulator;
import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;
import cz.paulrz.montecarlo.random.FastRandomFactory;
import cz.paulrz.montecarlo.random.ParallelPercentile;
import cz.paulrz.montecarlo.single.GeometricBrownianMotionProcess;
import cz.paulrz.montecarlo.single.IMonteCarloModel;
import cz.paulrz.montecarlo.single.LogArrivedPointValuation;
import cz.paulrz.montecarlo.single.ParallelMonteCarloModel;
import junit.framework.TestCase;
import org.apache.commons.math.MathException;
import org.apache.commons.math.random.NormalizedRandomGenerator;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

/**
 * User: paul
 * Date: 29/9/11
 * Time: 16:03 PM
 */
public class PercentileTests extends TestCase {

    private final int size = 1000000;

    public void testPerformance() {
        NormalizedRandomGenerator nrg = new FastGaussianRandomGenerator();
        double[] values = new double[size];
        for(int i =0 ; i<size; ++i)
            values[i] = nrg.nextNormalizedDouble();

        long ms = System.currentTimeMillis();
        ParallelPercentile percentile = new ParallelPercentile(50.0);
        DescriptiveStatistics ds = new DescriptiveStatistics();
        ds.setPercentileImpl(percentile);

        for(int i=0; i<size; ++i)
            ds.addValue(values[i]);

        double median = ds.getPercentile(50.0);
        ms = System.currentTimeMillis() - ms;
        System.out.println(ms+" ms");

        System.out.println(median);
    }

    public void testMcMedian() throws MathException {
        // mean should be 1.0, and stddev = sqrt(e-1)
        GeometricBrownianMotionProcess process = new GeometricBrownianMotionProcess(1.0, 0.0, 1.0);
        LogArrivedPointValuation apv = new LogArrivedPointValuation();
        MedianAccumulator summary = new MedianAccumulator();
        IMonteCarloModel mcm = new ParallelMonteCarloModel<Double>(new FastRandomFactory(),
                process, 1.0, 100, apv, summary, true, true);

        mcm.addSamples(50000);

        System.out.println(summary.value());
    }
}
