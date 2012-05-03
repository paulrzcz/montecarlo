package cz.paulrz.montecarlo.multi;

/**
 * Generates a random path following the stochastic process
 */
public interface PathGenerator {
    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    Path next();
}
