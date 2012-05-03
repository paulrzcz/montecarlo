package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;
import cz.paulrz.montecarlo.random.FastRandomFactory;
import cz.paulrz.montecarlo.single.*;
import cz.paulrz.montecarlo.accumulator.SimpleAccumulator;
import junit.framework.TestCase;
import org.apache.commons.math3.random.NormalizedRandomGenerator;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * User: paul
 * Date: 18/4/11
 * Time: 14:29 PM
 */
public class McTests extends TestCase {
    private GeometricBrownianMotionProcess process;
    private IMonteCarloModel mcm;
    private SimpleAccumulator summary;

    // as GBM is a log-normal process, Logs of arrival points work better

    // GBM mean is x0*exp(mu*t), but log(x_t/x_0) = (mu-sigma^2/2)*t
    private final static double expectedMean = -0.5;
    // GBM variance = x0^2 * exp(2*mu*t) * (exp(sigma^2*t)-1) but log variance
    // is sigma^2*t
    private final static double expectedStdDev = 1.0;

    public McTests() throws Exception {
        // mean should be 1.0, and stddev = sqrt(e-1)
        process = new GeometricBrownianMotionProcess(1.0, 0.0, expectedStdDev);
        LogArrivedPointValuation apv = new LogArrivedPointValuation();
        summary = new SimpleAccumulator();
        NormalizedRandomGenerator nrg = new FastGaussianRandomGenerator();
        mcm = new MonteCarloModel<Double, SummaryStatistics>(nrg, process, 1.0, 100, apv, summary, true, false);
    }

    public void testMeanAndVariance() {
        long ms = System.currentTimeMillis();
        int iters = mcm.addSamples(500000);
        ms = System.currentTimeMillis() - ms;
        System.out.println(ms+" ms");
        double rate = iters*1000.0/ms;
        System.out.format("%f iters/s %n", rate);

        double mean = summary.stats.getMean();
        double stddev = summary.stats.getStandardDeviation();

        System.out.println(iters);
        System.out.println(mean);
        System.out.println(stddev);
        assertEquals(expectedMean, mean, 0.01);
        // assertEquals(expectedStdDev, stddev, 0.05);
    }

    public void testMeanAndVarianceParallel() {
        LogArrivedPointValuation apv = new LogArrivedPointValuation();
        mcm = new ParallelMonteCarloModel<Double, SummaryStatistics>(new FastRandomFactory(),
                process, 1.0, 100, apv, summary, true, false);

        long ms = System.currentTimeMillis();
        int iters = mcm.addSamples(500000);
        ms = System.currentTimeMillis() - ms;
        System.out.println(ms+" ms");
        double rate = iters*1000.0/ms;
        System.out.format("%f iters/s %n", rate);

        double mean = summary.stats.getMean();
        double stddev = summary.stats.getStandardDeviation();

        System.out.println(iters);
        System.out.println(mean);
        System.out.println(stddev);
        assertEquals(expectedMean, mean, 0.01);
        assertEquals(expectedStdDev, stddev, 0.02);
    }

}
