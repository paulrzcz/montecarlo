package cz.paulrz.montecarlo.estimator;

import cz.paulrz.montecarlo.single.StochasticProcess1D;

public class FokkerEulerOperator implements ImplicitStencil  {
    private final int _size;
    private final double _dt;
    private final double _dx;
    private final double _alpha;
    private final double _beta;
    private final StochasticProcess1D _process;
    private final Mesh _mesh;

    public FokkerEulerOperator(StochasticProcess1D process1D, Mesh mesh) {
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
                (2*ksi(t1, _mesh.xStart) + 1),
                _alpha*mu(t1, _mesh.getX(1)) - _beta * ksi(t1, _mesh.getX(1))
        );

        result.setLastRow(
                -(_alpha*mu(t1, _mesh.xEnd - _dx) + _beta*ksi(t1, _mesh.xEnd - _dx)),
                (2*ksi(t1, _mesh.xEnd) + 1)
        );

        for(int i=1; i < _size -1 ; ++i) {
            final double xn = _mesh.getX(i - 1);
            final double x0 = _mesh.getX(i);
            final double xp = _mesh.getX(i+1);

            result.setMidRow(
                    i,
                    -(_alpha*mu(t1, xn) + _beta*ksi(t1, xn)),
                    (2*ksi(t1, x0) + 1),
                    _alpha*mu(t1, xp) - _beta * ksi(t1, xp)
            );
        }

        return result;
    }

    public double[] createRhs(double[] f, double t) {
        return f.clone();
    }

    private double mu(double t, double x) {
        return _process.drift(t, x);
    }

    private double ksi(double t, double x) {
        final double v = _process.diffusion(t, x);
        return v*v/2;
    }
}
