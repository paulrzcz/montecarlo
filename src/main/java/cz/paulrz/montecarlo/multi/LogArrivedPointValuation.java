package cz.paulrz.montecarlo.multi;

import org.apache.commons.math.util.FastMath;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 13:31 PM
 */
public class LogArrivedPointValuation implements PathValuation<Double> {
    private final int _dim;

    public LogArrivedPointValuation(int dim) {
        _dim = dim;
    }

    public Double value(Path path) {
        final double value = path.getValues(path.getLength() - 1).getQuick(_dim);
        final double initial = path.getValues(0).getQuick(_dim);
        return FastMath.log(value / initial);
    }
}
