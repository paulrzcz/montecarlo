package cz.paulrz.montecarlo.mle;

import cz.paulrz.montecarlo.accumulator.ProbabilityAccumulator;
import cz.paulrz.montecarlo.single.ArrivedPointValuation;
import cz.paulrz.montecarlo.single.IMonteCarloModel;
import cz.paulrz.montecarlo.single.SingleMcFactory;
import cz.paulrz.montecarlo.single.StochasticProcess1D;

/**
 *
 */
public abstract class DefaultMcFactory implements McFactory {

    private final double duration;
    private final int timeSteps;

    public DefaultMcFactory(double duration, int timeSteps ) {
        this.duration = duration;
        this.timeSteps = timeSteps;
    }

    private final ArrivedPointValuation apv = new ArrivedPointValuation();

    public IMonteCarloModel<Double, Double> createModel(double x0, double x1, double[] parameters) {
        StochasticProcess1D sp = createProcess(x0, parameters);
        ProbabilityAccumulator pa = new ProbabilityAccumulator(x1);

        return SingleMcFactory.createSimpleMc(sp, duration, timeSteps, apv, pa);
    }
}
