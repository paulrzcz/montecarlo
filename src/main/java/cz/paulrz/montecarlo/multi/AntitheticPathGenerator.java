package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 12:46 PM
 */
public final class AntitheticPathGenerator implements PathGenerator {
    private final GenericProcess process;
    private final int dim;
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
    public AntitheticPathGenerator(GenericProcess process, int timeSteps,
                                     double duration, NormalizedRandomGenerator generator) {
        this.process = process;
        dim = process.getDimension();
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

        final Path path = new Path(dim, timeSteps, dt);
        nextPath = new Path(dim, timeSteps, dt);
        path.addValue(process.getInitialVector());
        nextPath.addValue(process.getInitialVector());

        double t = 0.0;
        for (int i = 1; i < timeSteps; ++i) {
            final DoubleMatrix1D[] dw = generate();
            path.addValue(process.evolveMatrix(t, path.getValues(i - 1), dt, dw[0]));
            nextPath.addValue(process.evolveMatrix(t, path.getValues(i - 1), dt, dw[1]));
            t += dt;
        }

        return path;
    }

    private DoubleMatrix1D[] generate()
    {
        final double[] result = new double[dim];
        final double[] antiresult = new double[dim];
        for(int i=0; i< dim; ++i)
        {
            result[i] = generator.nextNormalizedDouble();
            antiresult[i] = - result[i];
        }
        return new DoubleMatrix1D[] {
                DoubleFactory1D.dense.make(result), DoubleFactory1D.dense.make(antiresult) };
    }
}
