package cz.paulrz.montecarlo.accumulator;

/**
 *
 * @param <T>
 */
public interface Accumulator<T>{
    void addValue(final T value);
    double norm(final Accumulator<T> other);
    Accumulator<T> deepCopy();
}
