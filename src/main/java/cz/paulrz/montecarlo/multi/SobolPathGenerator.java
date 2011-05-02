package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cz.paulrz.montecarlo.random.InverseCumulativeNormal;
import cz.paulrz.montecarlo.random.Sobol;
import org.apache.commons.math.MathException;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 12:46 PM
 */
public final class SobolPathGenerator implements PathGenerator {
    private final GenericProcess process;
    private final int timeSteps;
    private final double dt;
    private final Sobol generator;
    private final int dim;

    public SobolPathGenerator(GenericProcess process, int timeSteps,
                                double duration) throws Exception {
        this.process = process;
        dim = process.getDimension();
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

        Path path = new Path(dim, timeSteps, dt);
        path.addValue(process.getInitialVector());

        double t = 0.0;
        for(int i=1; i<timeSteps; ++i){

            DoubleMatrix1D dw = null;
            while(dw == null)
                dw = generate();

            path.addValue(process.evolveMatrix(t, path.getValues(i - 1), dt, dw));
            t += dt;
        }
        return path;
    }

    private DoubleMatrix1D generate() {
        final double[] uniform = generator.nextPoint();
        final double[] result  = new double[dim];
        for(int i=0; i<dim; ++i)
        {
            final double value = uniform[i];
            if (value >=1.0 || value<=0.0)
                return null;

            result[i] = InverseCumulativeNormal.op(value);
        }

        return DoubleFactory1D.dense.make(result);
    }
}
