package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import org.apache.commons.math.util.FastMath;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 15:22 PM
 *
 *      dS(t, S)  &=& \mu S dt + \sqrt{v} S dW_1 \\
        dv(t, S)  &=& \kappa (\theta - v) dt + \sigma \sqrt{v} dW_2 \\
        dW_1 dW_2 &=& \rho dt

 */
public final class HestonProcess extends StochasticProcess {

    private final double riskFreeRate;
    private final double v0;
    private final double kappa;
    private final double theta;
    private final double sigma;
    private final double rho;
    private final double sqrhov;

    public HestonProcess(double riskFreeRate, double s0, double v0, double kappa,
                         double theta, double sigma, double rho)
    {
        super(DoubleFactory1D.dense.make(new double[] {s0, v0}),
                new EulerDiscretization());
        this.riskFreeRate = riskFreeRate;
        this.v0 = v0;
        this.kappa = kappa;
        this.theta = theta;
        this.sigma = sigma;
        this.rho   = rho;
        sqrhov = FastMath.sqrt(1.0-rho*rho);
    }

    /**
     * Uses Feller condition
     * @return
     */
    public boolean isStrictlyPositive() {
        return (2*kappa*theta) > sigma*sigma;
    }

    @Override
    public DoubleMatrix1D drift(double t, DoubleMatrix1D x) {
        final double[] result = new double[2];
        final double x1  = x.getQuick(1);
        final double volvol = x1>0 ? x1 : 0.0;

        result[0] = riskFreeRate - volvol;
        result[1] = kappa*(theta - volvol);

        return DoubleFactory1D.dense.make(result);
    }

    @Override
    public DoubleMatrix2D diffusion(double t, DoubleMatrix1D x) {
        /* the correlation matrix is
           |  1   rho |
           | rho   1  |
           whose square root (which is used here) is
           |  1          0       |
           | rho   sqrt(1-rho^2) |
        */
        final double[][] result = new double[2][2];
        final double x1 = x.getQuick(1);
        final double vol = x1 > 0 ? FastMath.sqrt(x1) : 1e-8;
        final double sigma2 = sigma*vol;

        result[0][0] = vol;        result[0][1] = 0.0;
        result[1][0] = rho*sigma2; result[1][1] = sqrhov*sigma2;

        return DoubleFactory2D.dense.make(result);
    }

    public DoubleMatrix1D getInitialVector() {
        return x0;
    }

    public int getDimension() {
        return 2;
    }

    @Override
    public DoubleMatrix1D evolveMatrix(final double t, final DoubleMatrix1D x, final double dt, final DoubleMatrix1D dw) {
        final double[] result = new double[2];
        final double x1 = x.getQuick(1);
        final double volvol = x1 > 0 ? x1 : 0.0;
        final double vol = FastMath.sqrt(volvol);
        final double vol2= sigma*vol;
        final double mu  = riskFreeRate - 0.5*volvol;
        final double nu  = kappa*(theta - x1);
        final double sdt = FastMath.sqrt(dt);
        final double dw0 = dw.getQuick(0);
        final double dw1 = dw.getQuick(1);

        result[0] = x.getQuick(0) * FastMath.exp(mu*dt + vol*dw0*sdt);
        result[1] = x1 + nu*dt + vol2*sdt*(rho*dw0 + sqrhov*dw1);

        return DoubleFactory1D.dense.make(result);
    }
}
