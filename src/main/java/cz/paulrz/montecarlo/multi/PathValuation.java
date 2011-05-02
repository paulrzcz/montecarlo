package cz.paulrz.montecarlo.multi;

/**
 * User: paul
 * Date: 26/4/11
 * Time: 10:17 AM
 */
public interface PathValuation<T> {
    /**
     * Calculates path's value
     *
     * @param path Path given by Monte Carlo engine
     * @return Valuation of the path
     */
    T value(final Path path);
}
