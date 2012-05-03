package cz.paulrz.montecarlo.mle;

import cz.paulrz.montecarlo.single.IMonteCarloModel;
import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.util.FastMath;

/**
 *
 */
final class MlFunction implements MultivariateFunction {

    private final double[] data;
    private final McFactory factory;
    private final static int samples = 50000;

    public MlFunction(double[] data, McFactory factory) {
        this.data = data;
        this.factory = factory;
    }

    public double value(double[] point) throws IllegalArgumentException {
        double sum = 0.0;
        for(int i=1; i < data.length; ++i) {
            sum += onePoint(data[i-1], data[i], point);
        }

        System.out.printf("Mu = %.05f; Sigma = %.05f; Sum = %f\n", point[0], point[1], sum);

        return sum;
    }

    private double onePoint(double x0, double x1, double[] point) {
        final IMonteCarloModel<Double, Double> mc = factory.createModel(x0, x1, point);
        mc.addSamples(samples);
        final double value = mc.getStats().value();

        return FastMath.log(value);
    }
}
