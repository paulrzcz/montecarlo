package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;

/**
 * User: paul
 * Date: 26/4/11
 * Time: 10:20 AM
 */
public abstract class StochasticProcess implements GenericProcess {
    protected Discretization discretization;
    protected final DoubleMatrix1D x0;

    /**
     * Stochastic process constructor
     *
     * @param x0 initial position value
     * @param d Discretization
     */
    protected StochasticProcess(DoubleMatrix1D x0, Discretization d) {
        this.x0 = x0;
        discretization = d;
    }

    /**
     * Gets initial position value
     *
     * @return
     */
    public DoubleMatrix1D getInitialX() {
        return x0;
    }

    /**
     * Drift component of stochastic process
     *
     * @param t Time
     * @param x Position
     * @return drift
     */
    public abstract DoubleMatrix1D drift(double t, DoubleMatrix1D x);

    /**
     * Diffusion component of stochastic process
     *
     * @param t Time
     * @param x Position
     * @return diffusion
     */
    public abstract DoubleMatrix2D diffusion(double t, DoubleMatrix1D x);

    /**
     * Returns position after time interval dt
     *
     * @param t Time
     * @param x Position
     * @param dt Time step
     * @param dw Standard Brownian Step
     * @return Position after time step
     */
    public DoubleMatrix1D evolveMatrix(final double t, final DoubleMatrix1D x, final double dt, final DoubleMatrix1D dw) {
        final DoubleMatrix1D mu = discretization.drift(this, t, x, dt).assign(x, Functions.plus); // x+mu
        final DoubleMatrix2D sigma = discretization.diffusion(this, t, x ,dt);

        // (x + mu) + sigma*dw
        MatrixHelper.blas.dgemv(false, 1.0, sigma, dw, 1.0, mu);

        return mu;
    }
}
