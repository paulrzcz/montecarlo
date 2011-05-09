package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.random.BrownianBridge;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 9/5/11
 * Time: 12:24 PM
 */
public final class BridgePathGenerator1D implements PathGenerator1D {
    private final GenericProcess1D process;
    private final int timeSteps;
    private final double dt;
    private final NormalizedRandomGenerator generator;
    private final BrownianBridge bridge;

    /**
     * Constructor of SimplePathGenerator1D
     *
     * @param process Stochastic Process
     * @param timeSteps Number of time steps
     * @param duration Total duration of the process
     * @param generator Random generator of normalized real values
     */
    public BridgePathGenerator1D(GenericProcess1D process, int timeSteps,
                                 double duration, NormalizedRandomGenerator generator) {
        this.process = process;
        this.timeSteps = timeSteps;
        this.generator = generator;
        this.dt = duration / timeSteps;
        this.bridge = new BrownianBridge(timeSteps, dt);
    }

    public Path next() throws FunctionEvaluationException {
        final Path path = new Path(timeSteps, dt);
        path.addValue(process.getInitialX());
        final double[] dw = new double[timeSteps];
        dw[0] = 0.0;
        for(int i = 1; i<timeSteps; ++i) {
            dw[i] = generator.nextNormalizedDouble();
        }

        final double[] bridgedDw = bridge.transform(dw);

        double t = 0.0;
        for (int i = 1; i < timeSteps; ++i) {
            path.addValue(process.evolve(t, path.getValues()[i - 1], dt, bridgedDw[i]));
            t += dt;
        }

        return path;
    }
}
