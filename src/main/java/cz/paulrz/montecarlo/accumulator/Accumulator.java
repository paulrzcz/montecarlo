package cz.paulrz.montecarlo.accumulator;

/**
 * Created by IntelliJ IDEA.
 * User: paul
 * Date: 12/4/11
 * Time: 09:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Accumulator<T>{
    void addValue(final T value);
    double norm(final Accumulator<T> other);
    Accumulator<T> deepCopy();
}
