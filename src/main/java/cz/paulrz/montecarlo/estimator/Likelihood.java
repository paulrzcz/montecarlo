package cz.paulrz.montecarlo.estimator;

import cz.paulrz.montecarlo.single.StochasticProcess1D;

public interface Likelihood {
    double value(StochasticProcess1D process, double[] points, double dt);
}
