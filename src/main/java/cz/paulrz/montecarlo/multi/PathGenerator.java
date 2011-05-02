package cz.paulrz.montecarlo.multi;

import org.apache.commons.math.MathException;

/**
 * User: paul
 * Date: 26/4/11
 * Time: 10:15 AM
 */
public interface PathGenerator {
    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    Path next() throws MathException;
}
