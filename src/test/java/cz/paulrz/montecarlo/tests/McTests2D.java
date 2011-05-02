package cz.paulrz.montecarlo.tests;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cz.paulrz.montecarlo.accumulator.SimpleAccumulator;
import cz.paulrz.montecarlo.multi.GeometricBrownianMotion;
import cz.paulrz.montecarlo.multi.LogArrivedPointValuation;
import cz.paulrz.montecarlo.multi.MonteCarloModel;
import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;
import junit.framework.TestCase;
import org.apache.commons.math.MathException;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 13:30 PM
 */
public class McTests2D extends TestCase {
    private GeometricBrownianMotion process;
    private MonteCarloModel mcm;
    private SimpleAccumulator summary;

    // as GBM is a log-normal process, Logs of arrival points work better

    // GBM mean is x0*exp(mu*t), but log(x_t/x_0) = (mu-sigma^2/2)*t
    private final static double expectedMean = -0.5;
    // GBM variance = x0^2 * exp(2*mu*t) * (exp(sigma^2*t)-1) but log variance
    // is sigma^2*t
    private final static double expectedStdDev = 1.0;

    public McTests2D() throws Exception {
        // mean should be 1.0, and stddev = sqrt(e-1)
        double[] start = new double[] {1.0, 1.0 };
        double[] mu    = new double[] {0.0, 0.0 };
        double[][] sigma = new double[][] { {1.0, 0.0}, {0.0, 1.0} };

        process = new GeometricBrownianMotion(DoubleFactory1D.dense.make(start),
                DoubleFactory1D.dense.make(mu), DoubleFactory2D.dense.make(sigma));
        LogArrivedPointValuation apv = new LogArrivedPointValuation(0);
        summary = new SimpleAccumulator();
        mcm = new MonteCarloModel<Double>(new FastGaussianRandomGenerator(), process, 1.0, 100, apv, summary, true);
    }

    public void testMeanAndVariance() throws MathException {
        long time = System.currentTimeMillis();
        int iters = mcm.addSamples(100000);
        time = System.currentTimeMillis() - time;
        double mean = summary.stats.getMean();
        double stddev = summary.stats.getStandardDeviation();

        System.out.println(iters);
        System.out.println(time*1000.0/iters + " ms/1000");
        assertEquals(expectedMean, mean, 0.01);
        assertEquals(expectedStdDev, stddev, 0.05);
    }
}
