package cz.paulrz.montecarlo.single;

import org.apache.commons.math.MathException;

/**
 * User: paul
 * Date: 20/4/11
 * Time: 13:18 PM
 */
public interface PathGenerator1D {
    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    Path next() throws MathException;
}
