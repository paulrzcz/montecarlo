package cz.paulrz.montecarlo.mle;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.SimpleRealPointChecker;
import org.apache.commons.math.optimization.direct.NelderMead;

/**
 * User: paul
 * Date: 1/11/11
 * Time: 15:00 PM
 */
public class McMlEstimator {

    private final McFactory factory;
    private final MlFunction function;

    public McMlEstimator(McFactory factory, double[] data) {
        this.factory = factory;
        function = new MlFunction(data, factory);
    }

    public RealPointValuePair estimate() throws FunctionEvaluationException, OptimizationException {
        NelderMead optimizer = new NelderMead();
        optimizer.setStartConfiguration(factory.getStartConfiguration());
        optimizer.setConvergenceChecker(new SimpleRealPointChecker(-0.001, 1e-10));

        RealPointValuePair result = optimizer.optimize(function, GoalType.MAXIMIZE, factory.getStartingPoint());

        return result;
    }
}
