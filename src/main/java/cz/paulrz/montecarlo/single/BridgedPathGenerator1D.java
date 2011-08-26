package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.random.BrownianBridge;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 25/8/11
 * Time: 17:46 PM
 */
public final class BridgedPathGenerator1D implements PathGenerator1D {

    private final GenericProcess1D process;
    private final int timeSteps;
    private final double dt;
    private final NormalizedRandomGenerator generator;
    private final BrownianBridge bridge;

    public BridgedPathGenerator1D(GenericProcess1D process, int timeSteps,
                                 double duration, NormalizedRandomGenerator generator) {
        this.process = process;
        this.timeSteps = timeSteps;
        this.generator = generator;
        this.dt = duration / timeSteps;
        bridge = new BrownianBridge(timeSteps, dt);
    }

    public Path next() throws FunctionEvaluationException {
        final Path path = new Path(timeSteps, dt);
        path.addValue(process.getInitialX());

        double[] dw = new double[timeSteps];
        dw[0] = 0.0;
        for(int i = 1; i<timeSteps; ++i) {
            dw[i] = generator.nextNormalizedDouble();
        }

        dw = bridge.transform(dw);

        double t = 0.0;
        for (int i = 1; i < timeSteps; ++i) {
            path.addValue(process.evolve(t, path.getValues()[i - 1], dt, dw[i]));
            t += dt;
        }

        return path;
    }

}
