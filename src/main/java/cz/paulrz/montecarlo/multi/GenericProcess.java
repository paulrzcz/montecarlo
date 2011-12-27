package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleMatrix1D;

/**
 * Generic stochastic process X(t)
 *
 */
public interface GenericProcess {

    /**
     * Access initial vector at t=0
     *
     * @return
     */
    DoubleMatrix1D getInitialVector();

    /**
     * Dimension of stochastic process
     *
     * @return
     */
    int getDimension();


    /**
     * Single step of the process from x in time interval dt with given
     * stochastic part
     *
     * @param t Time
     * @param x Position
     * @param dt Time interval
     * @param dw Stochastic part
     * @return Position at t+dt, i.e. X(t+dt)
     */
    DoubleMatrix1D evolveMatrix(final double t, final DoubleMatrix1D x, final double dt, final DoubleMatrix1D dw);
}
