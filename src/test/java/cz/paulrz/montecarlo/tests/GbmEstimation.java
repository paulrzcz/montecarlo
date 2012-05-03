package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.mle.GbmMcFactory;
import cz.paulrz.montecarlo.mle.McMlEstimator;
import cz.paulrz.montecarlo.single.GeometricBrownianMotionProcess;
import junit.framework.TestCase;
import org.apache.commons.math3.optimization.PointValuePair;

/**
 * User: paul
 * Date: 1/11/11
 * Time: 15:24 PM
 */
public class GbmEstimation extends TestCase {

    private final GbmMcFactory factory = new GbmMcFactory(1.0, 100);
    private final double[] data = new double[] {1.0, 0.9, 1.1, 0.9, 1.0};
    private McMlEstimator estimator;
    private final GeometricBrownianMotionProcess process;

    public GbmEstimation() {
        estimator = new McMlEstimator(factory, data);

        GbmEstimator linear =new GbmEstimator(data);
        process = linear.estimate(1.0);
    }

    public void testEstimate()  {
        PointValuePair result = estimator.estimate();

        System.out.println(process.toString());

        System.out.printf("ML    = %.7f\n", result.getValue());
        System.out.printf("mu    = %.7f\n", result.getPointRef()[0]);
        System.out.printf("sigma = %.7f\n", result.getPointRef()[1]);
    }

}
