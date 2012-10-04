package cz.paulrz.montecarlo.estimator;

public interface ImplicitStencil {
    TridiagonalOperator createOperator(double t);
    double[] createRhs(double[] f, double t);
}
