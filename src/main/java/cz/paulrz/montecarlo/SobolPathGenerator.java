package cz.paulrz.montecarlo;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 20/4/11
 * Time: 13:20 PM
 */
public final class SobolPathGenerator implements PathGenerator {
    private final GenericProcess process;
    private final int timeSteps;
    private final double dt;
    private final Sobol generator;

    public SobolPathGenerator(GenericProcess process, int timeSteps,
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
