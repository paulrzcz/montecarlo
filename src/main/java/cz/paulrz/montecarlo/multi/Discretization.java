package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

/**
 * Discretization of a stochastic process over given time interval
 *
 */
public interface Discretization {
    /**
     * Drift component discretization
     *
     * @param process Stochastic process
     * @param t Time
     * @param x Position
     * @param dt Time step
     * @return Drift value step
     */
    DoubleMatrix1D drift(StochasticProcess process, double t, DoubleMatrix1D x, double dt);

    /**
     * Diffusion component discretization
     *
     * @param process Stochastic process
     * @param t Time
     * @param x Position
     * @param dt Time step
     * @return Diffusion value step
     */
    DoubleMatrix2D diffusion(StochasticProcess process, double t, DoubleMatrix1D x, double dt);
}
