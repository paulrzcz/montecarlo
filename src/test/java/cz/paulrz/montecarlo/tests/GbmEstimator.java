package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.single.GeometricBrownianMotionProcess;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.FastMath;

/**
 * User: paul
 * Date: 2/11/11
 * Time: 10:31 AM
 */
public class GbmEstimator {

    private final double[] data;

    public GbmEstimator(double[] data) {
        this.data = data;
    }

    public GeometricBrownianMotionProcess estimate(double delta) {
        double openPrice    = data[0];
        SummaryStatistics stats = new SummaryStatistics();

        for(int i=1; i<data.length; ++i) {
            double logChange = FastMath.log(data[i]) - FastMath.log(data[i-1]);
            stats.addValue(logChange);
        }

        double r = stats.getMean();
        double s = stats.getVariance();

        double sigma =  FastMath.sqrt(s/delta);
        double mu    =  (r + s/2)/delta;
        return new GeometricBrownianMotionProcess(openPrice, mu, sigma);
    }

}
