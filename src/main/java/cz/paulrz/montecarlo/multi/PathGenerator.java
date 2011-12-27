package cz.paulrz.montecarlo.multi;

import org.apache.commons.math.MathException;

/**
 * Generates a random path following the stochastic process
 */
public interface PathGenerator {
    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    Path next() throws MathException;
}
