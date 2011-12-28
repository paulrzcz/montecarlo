package cz.paulrz.montecarlo.accumulator;

/**
 * Accumulator collects statistics of path valuations
 * @param <T>
 */
public interface Accumulator<T, O>{

    /**
     * Add single value
     * @param value Path value
     */
    void addValue(final T value);

    /**
     * Calculates a value of accumulator
     * @return value
     */
    O value();

    /**
     * Calculates a distance between two accumulators.
     * Used for converge testing
     * @param other Second accumulator
     * @return Distance
     */
    double norm(final Accumulator<T, O> other);

    /**
     * Deep copy
     * @return Accumulator copy
     */
    Accumulator<T, O> deepCopy();
}
