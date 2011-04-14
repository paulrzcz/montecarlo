package cz.paulrz.montecarlo;

/**
 * Created by IntelliJ IDEA.
 * User: paul
 * Date: 12/4/11
 * Time: 09:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Accumulator<T> {
    void addValue(T value);
    double norm(Accumulator<T> other);
    Accumulator<T> clone();
}
