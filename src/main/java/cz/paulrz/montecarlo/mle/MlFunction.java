package cz.paulrz.montecarlo.mle;

import cz.paulrz.montecarlo.accumulator.ProbabilityAccumulator;
import cz.paulrz.montecarlo.single.IMonteCarloModel;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.util.FastMath;

/**
 * User: paul
 * Date: 1/11/11
 * Time: 15:03 PM
 */
final class MlFunction implements MultivariateRealFunction {

    private final double[] data;
    private final McFactory factory;
    private final static int samples = 10000;

    public MlFunction(double[] data, McFactory factory) {
        this.data = data;
        this.factory = factory;
    }

    public double value(double[] point) throws FunctionEvaluationException, IllegalArgumentException {
        double sum = 0.0;
        for(int i=1; i < data.length; ++i) {
            sum += onePoint(data[i-1], data[i], point);
        }

        return sum;
    }

    private double onePoint(double x0, double x1, double[] point) throws FunctionEvaluationException {
        final IMonteCarloModel<Double> mc = factory.createModel(x0, x1, point);
        try{
            mc.addSamples(samples);
            final double value = ((ProbabilityAccumulator) mc.getStats()).probability();

            return FastMath.log(value);
        }
        catch (MathException e)
        {
            throw new FunctionEvaluationException(e, point);
        }
    }
}
