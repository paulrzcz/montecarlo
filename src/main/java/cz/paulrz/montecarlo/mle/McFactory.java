package cz.paulrz.montecarlo.mle;

import cz.paulrz.montecarlo.single.IMonteCarloModel;
import cz.paulrz.montecarlo.single.StochasticProcess1D;

/**
 * User: paul
 * Date: 1/11/11
 * Time: 14:50 PM
 */
public interface McFactory {

    double[] getStartingPoint();

    StochasticProcess1D createProcess(double x0, double[] parameters);

    IMonteCarloModel<Double> createModel(double x0, double x1, double[] parameters);
}
