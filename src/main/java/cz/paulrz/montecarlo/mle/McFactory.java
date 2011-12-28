package cz.paulrz.montecarlo.mle;

import cz.paulrz.montecarlo.single.IMonteCarloModel;
import cz.paulrz.montecarlo.single.StochasticProcess1D;

/**
 *
 */
public interface McFactory {

    double[] getStartConfiguration();

    double[] getStartingPoint();

    StochasticProcess1D createProcess(double x0, double[] parameters);

    IMonteCarloModel<Double, Double> createModel(double x0, double x1, double[] parameters);
}
