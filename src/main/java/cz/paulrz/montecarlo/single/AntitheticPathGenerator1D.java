package cz.paulrz.montecarlo.single;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 25/4/11
 * Time: 15:33 PM
 */
public final class AntitheticPathGenerator1D implements PathGenerator1D {
    private final GenericProcess1D process;
    private final int timeSteps;
    private final double dt;
    private final NormalizedRandomGenerator generator;
    private Path nextPath = null;

    /**
     * Constructor of SimplePathGenerator1D
     *
     * @param process Stochastic Process
     * @param timeSteps Number of time steps
     * @param duration Total duration of the process
     * @param generator Random generator of normalized real values
     */
    public AntitheticPathGenerator1D(GenericProcess1D process, int timeSteps,
                                     double duration, NormalizedRandomGenerator generator) {
        this.process = process;
        this.timeSteps = timeSteps;
        this.generator = generator;
        this.dt = duration / timeSteps;
    }

    public Path next() throws FunctionEvaluationException {
        if (nextPath!=null)
        {
            final Path retPath = nextPath;
            nextPath = null;
            return retPath;
        }

        final Path path = new Path(timeSteps, dt);
        nextPath = new Path(timeSteps, dt);
        path.addValue(process.getInitialX());
        nextPath.addValue(process.getInitialX());

        double t = 0.0;
        for (int i = 1; i < timeSteps; ++i) {
            final double dw = generator.nextNormalizedDouble();
            path.addValue(process.evolve(t, path.getValues()[i - 1], dt, dw));
            nextPath.addValue(process.evolve(t, path.getValues()[i - 1], dt, -dw));
            t += dt;
        }

        return path;
    }
}
