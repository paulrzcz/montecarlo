package cz.paulrz.montecarlo.multi;

import org.apache.commons.math3.util.FastMath;

/**
 * This valuation provides a logarithm of a final, or arrived, point of the path
 * as path value at dimension index dim
 *
 */
public class LogArrivedPointValuation implements PathValuation<Double> {
    private final int _dim;

    /**
     * Constructor of log-arrived point valuation
     * @param dim  - index of dimension to use
     */
    public LogArrivedPointValuation(int dim) {
        _dim = dim;
    }

    /**
     *
     * @param path Path given by Monte Carlo engine
     * @return
     */
    public Double value(Path path) {
        final double value = path.getValues(path.getLength() - 1).getQuick(_dim);
        final double initial = path.getValues(0).getQuick(_dim);
        return FastMath.log(value / initial);
    }
}
