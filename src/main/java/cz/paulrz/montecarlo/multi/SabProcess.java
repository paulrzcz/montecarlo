package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import org.apache.commons.math3.util.FastMath;

/**
 * User: paul
 * Date: 8/5/11
 * Time: 00:41 AM
 */
public final class SabProcess extends StochasticProcess {
    private final double alfa;
    private final double beta;

    public SabProcess(double f0, double sigma0, double alfa, double beta) {
        super(DoubleFactory1D.dense.make(new double[] {f0, sigma0}), new EulerDiscretization());
        this.alfa = alfa;
        this.beta = beta;
    }

    private final static DoubleMatrix1D mu = DoubleFactory1D.dense.make(2, 0.0);

    @Override
    public DoubleMatrix1D drift(double t, DoubleMatrix1D x) {
        return mu;
    }

    @Override
    public DoubleMatrix2D diffusion(double t, DoubleMatrix1D x) {
        final double[][] result = new double[2][2];
        final double sigma = x.getQuick(1);
        final double f     = x.getQuick(0);
        final double fsigma= sigma*FastMath.pow(f, beta);

        result[0][0] = fsigma;     result[0][1] = 0.0;
        result[1][0] = 0.0; result[1][1] = sigma*alfa;

        return DoubleFactory2D.dense.make(result);
    }

    public int getDimension() {
        return 2;
    }
}
