package cz.paulrz.montecarlo.estimator;

import cz.paulrz.montecarlo.single.StochasticProcess1D;

public class FokkerOperator implements ImplicitStencil {

    private final int _size;
    private final double _dt;
    private final double _dx;
    private final double _alpha;
    private final double _beta;
    private final StochasticProcess1D _process;
    private final Mesh _mesh;

    public FokkerOperator(StochasticProcess1D process1D, Mesh mesh) {
        _size = mesh.sizeX;
        _process = process1D;
        _dt = mesh.dt;
        _dx = mesh.dx;
        _alpha = _dt/_dx/4;
        _beta = _dt/_dx/_dx/2;
        _mesh = mesh;
    }

    public TridiagonalOperator createOperator(double t) {
        final TridiagonalOperator result = new TridiagonalOperator(_size);
        double t1 = t + _dt;

        result.setFirstRow(
                -(2*ksi(t1, _mesh.xStart) + 1),
                _beta * ksi(t1, _mesh.getX(1)) - _alpha*_process.drift(t1, _mesh.getX(1))
        );

        result.setLastRow(
                _alpha*_process.drift(t1, _mesh.xEnd - _dx) + _beta*ksi(t1, _mesh.xEnd - _dx),
                -(2*ksi(t1, _mesh.xEnd) + 1)
        );

        for(int i=1; i < _size -1 ; ++i) {
            final double xn = _mesh.getX(i - 1);
            final double x0 = _mesh.getX(i);
            final double xp = _mesh.getX(i+1);

            result.setMidRow(
                    i,
                    _alpha*_process.drift(t1, xn) + _beta*ksi(t1, xn),
                    -(2*ksi(t1, x0) + 1),
                    _beta * ksi(t1, xp) - _alpha*_process.drift(t1, xp)
            );
        }

        return result;
    }

    public double[] createRhs(double[] f, double t) {
        final double[] result = new double[_size];

        result[0] = rightHandFunction(t, _mesh.xStart, 0.0, f[0], f[1]);
        result[_size - 1] = rightHandFunction(t, _mesh.xEnd, f[_size-2], f[_size-1], 0.0);

        for(int i=1; i<_size-1; ++i) {
            result[i] = rightHandFunction(t, _mesh.getX(i), f[i-1], f[i], f[i+1]);
        }

        return result;
    }

    private double rightHandFunction(double t, double x, double fn, double f0, double fp) {
        final double xp = x + _dx;
        final double xn = x - _dx;
        final double dmf = mu(t, xp)*fp - mu(t, xn) * fn;
        final double dxf = ksi(t, xp)*fp - 2*ksi(t, x)*f0 + ksi(t, xn)*fn;

        return _alpha*dmf - _beta*dxf - f0;
    }

    private double mu(double t, double x) {
        return _process.drift(t, x);
    }

    private double ksi(double t, double x) {
        final double v = _process.diffusion(t, x);
        return v*v/2;
    }
}
