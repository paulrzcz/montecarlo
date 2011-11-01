package cz.paulrz.montecarlo.mle;

import cz.paulrz.montecarlo.single.GeometricBrownianMotionProcess;
import cz.paulrz.montecarlo.single.StochasticProcess1D;

/**
 * User: paul
 * Date: 1/11/11
 * Time: 15:21 PM
 */
public class GbmMcFactory extends DefaultMcFactory {

    public GbmMcFactory(double duration, int timeSteps) {
        super(duration, timeSteps);
    }

    public double[] getStartingPoint() {
        return new double[] {0.0, 0.0 };
    }

    public StochasticProcess1D createProcess(double x0, double[] parameters) {
        return new GeometricBrownianMotionProcess(x0, parameters[0], parameters[1]);
    }
}
