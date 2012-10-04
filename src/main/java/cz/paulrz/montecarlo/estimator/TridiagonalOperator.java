package cz.paulrz.montecarlo.estimator;

public class TridiagonalOperator {

    final int _size;
    final double[] _diagonal;
    final double[] _lowerDiagonal;
    final double[] _upperDiagonal;
    final double[] _temp;

    public TridiagonalOperator(int size) {
        _size = size;
        _diagonal = new double[size];
        _lowerDiagonal = new double[size - 1];
        _upperDiagonal = new double[size - 1];
        _temp = new double[size];
    }

    public TridiagonalOperator(int size, double lower, double diag, double upper) {
        this(size);

        for(int i=0; i < _size; ++i) {
            _diagonal[i] = diag;
            if (i < _size-1) {
                _lowerDiagonal[i] = lower;
                _upperDiagonal[i] = upper;
            }
        }
    }

    public void setFirstRow(double v0, double vp) {
        _diagonal[0] = v0;
        _upperDiagonal[0] = v0;
    }

    public void setLastRow(double vn, double v0) {
        _lowerDiagonal[_size - 2] = vn;
        _diagonal[_size - 1] = v0;
    }

    public void setMidRow(int index, double vn, double v0, double vp) {
        _lowerDiagonal[index-1] = vn;
        _diagonal[index] = v0;
        _upperDiagonal[index] = vp;
    }

    public double[] solveFor(double[] rhs) {
        if (rhs.length != _size)
            throw new IllegalArgumentException("Size of array doesn't match to operator size");

        double bet = _diagonal[0];
        final double[] result = new double[_size];

        result[0] = rhs[0] / bet;

        for(int j=1; j < _size; ++j) {
            _temp[j] = _upperDiagonal[j-1] / bet;
            bet = _diagonal[j]-_lowerDiagonal[j-1]*_temp[j];
            result[j] = (rhs[j] - _lowerDiagonal[j-1]*result[j-1])/bet;
        }

        for(int j=_size-2; j>0; --j) {
            result[j] -= _temp[j+1]*result[j+1];
        }

        result[0] -= _temp[1]*result[1];

        return result;
    }
}
