package cz.paulrz.montecarlo.single;

/**
 * Generates a random path following the stochastic process
 */
public interface PathGenerator1D {
    /**
     * Generates a new path
     *
     * @return Path for the stochastic process
     */
    Path next();
}
