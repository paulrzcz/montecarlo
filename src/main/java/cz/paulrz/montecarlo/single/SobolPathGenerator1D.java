package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.random.BrownianBridge;
import cz.paulrz.montecarlo.random.InverseCumulativeNormal;
import cz.paulrz.montecarlo.random.Sobol;
import org.apache.commons.math.MathException;

/**
 * User: paul
 * Date: 20/4/11
 * Time: 13:20 PM
 */
public final class SobolPathGenerator1D implements PathGenerator1D {
    private final GenericProcess1D process;
    private final int timeSteps;
    private final double dt;
    private final Sobol generator;
    private final BrownianBridge bridge;

    public SobolPathGenerator1D(GenericProcess1D process, int timeSteps,
                                double duration, boolean useBridge) throws Exception {
        this.process = process;
        this.timeSteps = timeSteps;
        this.generator = new Sobol(timeSteps-1); // first point is known
        this.dt = duration / timeSteps;
        bridge = useBridge ? new BrownianBridge(timeSteps, dt) : null;
    }

    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    public Path next() throws MathException {
        Path path = null;

        while(path == null)
        {
            path = getNext();
        }

        return path;
    }

    private Path getNext() throws MathException {
        final double[] uniform = generator.nextPoint();
        Path path = new Path(timeSteps, dt);
        path.addValue(process.getInitialX());

        double[] dw = new double[timeSteps];
        dw[0] = 0.0;
        for(int i=1; i<timeSteps; ++i) {
            final double value = uniform[i-1];
            if (value >=1.0 || value<=0.0)
                return null;

            dw[i] = InverseCumulativeNormal.op(value);
        }

        if (bridge!=null)
            dw = bridge.transform(dw);

        double t = 0.0;
        for(int i=1; i<timeSteps; ++i){
            path.addValue(process.evolve(t, path.getValues()[i - 1], dt, dw[i]));
            t += dt;
        }
        return path;
    }
}
