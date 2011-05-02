package cz.paulrz.montecarlo.single;

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

    public SobolPathGenerator1D(GenericProcess1D process, int timeSteps,
                                double duration) throws Exception {
        this.process = process;
        this.timeSteps = timeSteps;
        this.generator = new Sobol(timeSteps-1); // first point is known
        this.dt = duration / timeSteps;
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

        double t = 0.0;
        for(int i=1; i<timeSteps; ++i){
            final double value = uniform[i-1];
            if (value >=1.0 || value<=0.0)
                return null;

            final double dw = InverseCumulativeNormal.op(value);
            path.addValue(process.evolve(t, path.getValues()[i - 1], dt, dw));
            t += dt;
        }
        return path;
    }
}
