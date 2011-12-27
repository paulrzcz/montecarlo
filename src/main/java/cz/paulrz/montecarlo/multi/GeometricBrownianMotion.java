package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

/**
 * This class represent multidimensional <a
 * href="http://en.wikipedia.org/wiki/Geometric_Brownian_motion">Geometric
 * Brownian Motion</a> with constant drift and diffusion.
 *
 */
public final class GeometricBrownianMotion extends StochasticProcess {

    private final DoubleMatrix1D mu;
    private final DoubleMatrix2D sigma;
    private final int dim;

    public GeometricBrownianMotion(DoubleMatrix1D x0, DoubleMatrix1D mu, DoubleMatrix2D sigma) {
        super(x0, new EulerDiscretization());
        dim = x0.size();

        if (mu.size() != dim)
            throw new IllegalArgumentException("mu");
        if (sigma.rows() != dim && sigma.columns() != dim)
            throw new IllegalArgumentException("sigma");

        this.mu = mu;
        this.sigma = sigma;
    }

    @Override
    public DoubleMatrix1D drift(double t, DoubleMatrix1D x) {
        final DoubleMatrix1D result = DoubleFactory1D.dense.make(dim, 0.0);

        for(int i=0; i<dim; ++i)
        {
            result.setQuick(i, mu.getQuick(i) * x.getQuick(i));
        }

        return result;
    }

    @Override
    public DoubleMatrix2D diffusion(double t, DoubleMatrix1D x) {
        final DoubleMatrix2D result = DoubleFactory2D.dense.make(dim, dim, 0.0);
        final DoubleMatrix2D xs     = DoubleFactory2D.dense.diagonal(x);

        return sigma.zMult(xs, result);
    }

    public DoubleMatrix1D getInitialVector() {
        return x0;
    }

    public int getDimension() {
        return dim;
    }
}
