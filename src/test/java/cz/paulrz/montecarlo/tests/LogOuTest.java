package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.accumulator.SimpleAccumulator;
import cz.paulrz.montecarlo.single.ArrivedPointValuation;
import cz.paulrz.montecarlo.single.IMonteCarloModel;
import cz.paulrz.montecarlo.single.LogOrnsteinUhlenbeckProcess;
import cz.paulrz.montecarlo.single.MonteCarloModel;
import junit.framework.TestCase;
import org.apache.commons.math.MathException;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

/**
 * User: paul
 * Date: 26/5/11
 * Time: 16:23 PM
 */
public class LogOuTest extends TestCase {
    private LogOrnsteinUhlenbeckProcess process;
    private IMonteCarloModel mcm;
    private SimpleAccumulator summary;

    // as GBM is a log-normal process, Logs of arrival points work better

    // GBM mean is x0*exp(mu*t), but log(x_t/x_0) = (mu-sigma^2/2)*t
    private final static double expectedMean = -0.5;
    // GBM variance = x0^2 * exp(2*mu*t) * (exp(sigma^2*t)-1) but log variance
    // is sigma^2*t
    private final static double expectedStdDev = 1.0;

    public LogOuTest() throws Exception {
        // x0 = 1.2872; mu = 0.7881886345009712; sigma = 0.021632095253768753; theta = -11.940878437742658
        process = new LogOrnsteinUhlenbeckProcess(1.2872, 11.940878437742658,
                0.7881886345009712, 0.021632095253768753) ;
        ArrivedPointValuation apv = new ArrivedPointValuation();
        summary = new SimpleAccumulator();
        mcm = new MonteCarloModel<Double, SummaryStatistics>(process, 1.0, 100, apv, summary, true);
    }

    public void testMeanAndVariance() throws MathException {
        int iters = mcm.addSamples(10000, 1e-5, 100);
        double mean = summary.stats.getMean();
        double stddev = summary.stats.getStandardDeviation();

        System.out.println(iters);
        System.out.println(mean);
        System.out.println(stddev);
    }
}
