package cz.paulrz.montecarlo;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;

/**
 * User: paul
 * Date: 20/4/11
 * Time: 13:18 PM
 */
public interface PathGenerator {
    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    Path next() throws MathException;
}
