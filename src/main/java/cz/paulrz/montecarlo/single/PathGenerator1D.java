package cz.paulrz.montecarlo.single;

import org.apache.commons.math.MathException;

/**
 * Generates a random path following the stochastic process
 */
public interface PathGenerator1D {
    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    Path next() throws MathException;
}
