package cz.paulrz.montecarlo.multi;

/**
 * This interface represents a mapping from path to real value of the path. It
 * is called from the Monte Carlo engine to include path value into statistics
 * summary
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
