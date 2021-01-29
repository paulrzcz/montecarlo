package cz.paulrz.montecarlo.mle;

import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.SimplePointChecker;
import org.apache.commons.math3.optimization.direct.NelderMeadSimplex;
import org.apache.commons.math3.optimization.direct.SimplexOptimizer;

/**
 *
 */
public class McMlEstimator {

    private final McFactory factory;
    private final MlFunction function;

    public McMlEstimator(McFactory factory, double[] data) {
        this.factory = factory;
        function = new MlFunction(data, factory);
    }

    public PointValuePair estimate() {
        NelderMeadSimplex simplex = new NelderMeadSimplex(factory.getStartConfiguration());
        SimplexOptimizer optimizer = new SimplexOptimizer(new SimplePointChecker<PointValuePair>(0.01, 0.3));

        return
                optimizer.optimize(5000, function,
                        GoalType.MAXIMIZE, simplex);
    }
}
