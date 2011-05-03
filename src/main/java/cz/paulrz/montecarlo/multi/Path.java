package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import org.apache.commons.math.exception.OutOfRangeException;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 12:24 PM
 */
public final class Path {

    private final DoubleMatrix2D _path;
    private final double _dt;
    private final int _length;
    private int currentColumn = 0;
    private final int _dim;

    public Path(int dim, int length, double dt) {
        _dt = dt;
        _dim = dim;
        _length = length;
        _path = DoubleFactory2D.dense.make(dim, length);
    }


    /**
     * Gets time step (dt)
     *
     * @return Value of the step
     */
    public double getTimeStep() {
        return _dt;
    }

    /**
     * Gets length of the path
     *
     * @return Number of points in the path
     */
    public int getLength() {
        return _length;
    }

    public void addValue(DoubleMatrix1D vector) {
        if (currentColumn>= _length)
            throw new OutOfRangeException(currentColumn, 0, _length);

        if (vector.size() != _dim)
            throw new OutOfRangeException(vector.size(), 0, _dim);

        for(int i=0; i<_dim; ++i)
            _path.setQuick(i, currentColumn, vector.get(i));

        currentColumn++;
    }

    public DoubleMatrix1D getValues(int i) {
        return _path.viewColumn(i);
    }

    public double[] getRow(int i) {
        return _path.viewRow(i).toArray();
    }
}
