package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import org.apache.commons.math.util.FastMath;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 10:51 AM
 */
public final class EulerDiscretization implements Discretization {
    public DoubleMatrix1D drift(StochasticProcess process, double t, DoubleMatrix1D x, double dt) {
        final DoubleMatrix1D result =DoubleFactory1D.dense.make(x.size(), 0.0);

        // result = result + drift*dt
        MatrixHelper.blas.daxpy(dt, process.drift(t, x), result);

        return result;
    }

    public DoubleMatrix2D diffusion(StochasticProcess process, double t, DoubleMatrix1D x, double dt) {
        final DoubleMatrix2D diff   = process.diffusion(t, x);
        final double sqrtDt = FastMath.sqrt(dt);
        final DoubleMatrix2D result = diff.copy();

        MatrixHelper.blas.dscal(sqrtDt, result);

        return result;
    }
}
