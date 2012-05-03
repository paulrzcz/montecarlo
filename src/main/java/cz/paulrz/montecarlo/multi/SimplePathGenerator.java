package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.math3.random.NormalizedRandomGenerator;

/**
 * Ordinary path generator. Suitable in the most of scenarios.
 */
public final class SimplePathGenerator implements PathGenerator {
    private final GenericProcess process;
    private final int timeSteps;
    private final double dt;
    private final NormalizedRandomGenerator generator;
    private final int processDimension;

    /**
     * Constructor of SimplePathGenerator1D
     *
     * @param process Stochastic Process
     * @param timeSteps Number of time steps
     * @param duration Total duration of the process
     * @param generator Random generator of normalized real values
     */
    public SimplePathGenerator(GenericProcess process, int timeSteps,
                                 double duration, NormalizedRandomGenerator generator) {
        this.process = process;
        processDimension = process.getDimension();
        this.timeSteps = timeSteps;
        this.generator = generator;
        this.dt = duration / timeSteps;
    }

    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    public Path next() {
        final Path path = new Path(processDimension, timeSteps, dt);
        path.addValue(process.getInitialVector());

        double t = 0.0;
        for (int i = 1; i < timeSteps; ++i) {
            final DoubleMatrix1D dw = generate();
            path.addValue(process.evolveMatrix(t, path.getValues(i - 1), dt, dw));
            t += dt;
        }

        return path;
    }

    private DoubleMatrix1D generate()
    {
        final double[] result = new double[processDimension];
        for(int i=0; i< processDimension; ++i)
            result[i] = generator.nextNormalizedDouble();
        return DoubleFactory1D.dense.make(result);
    }

}
