package cz.paulrz.montecarlo.estimator;

import cz.paulrz.montecarlo.single.StochasticProcess1D;
import org.apache.commons.math3.util.FastMath;

public class FokkerLikelihood implements Likelihood {
    private final int xsize;

    public FokkerLikelihood() {
        this(100);
    }

    public FokkerLikelihood(int xsize) {
        this.xsize = xsize;
    }

    public double value(StochasticProcess1D process, double[] points, double dt) {
        double min, max;
        {
            min = points[0];
            max = points[0];
            for(int i=1; i < points.length; ++i) {
                if (min > points[i])
                    min = points[i];
                else if (max < points[i])
                    max = points[i];
            }
        }
        if (max == min) {
            max += 0.1;
            min -= 0.1;
        }

        final double dx = (max - min) / (xsize - 2);

        min = min - dx;
        max = max + dx;

        Mesh mesh = new Mesh(min, xsize, max, 0.0, points.length, points.length*dt);

        // make a delta function at the start
        double[] x = makeInitialDistribution(points[0], min, max, dx);

        double y = 0.0;

        final FokkerOperator operator = new FokkerOperator(process, mesh);

        for(int i=1; i< points.length; ++i) {
            // estimate in dt time
            x = makeStep(x, operator, dt*(i-1));
            // find log probability
            // sum probability
            y += FastMath.log(interpolate(x, min, dx, points[i]));
        }

        return y;
    }

    private double interpolate(double[] x, double min, double dx, double point) {
        final double index = (point - min) / dx;
        final int minIndex = (int)FastMath.floor(index);
        final int maxIndex = (int)FastMath.ceil(index);

        if (minIndex == maxIndex) {
            return x[minIndex];
        }

        return x[minIndex] * (index - minIndex) + x[maxIndex] * (maxIndex - index);
    }

    private static double[] makeStep(double[] x, ImplicitStencil operator, double t) {
        final int length = x.length;
        final double[] result = new double[length];

        final TridiagonalOperator trop = operator.createOperator(t);
        final double[] rhs = operator.createRhs(x, t);

        return trop.solveFor(rhs);
    }

    private double[] makeInitialDistribution(double point, double min, double max, double dx) {
        final double[] x0 = new double[xsize];
        final double index = (point - min) / dx;
        final int minIndex = (int)FastMath.floor(index);
        final int maxIndex = (int)FastMath.ceil(index);

        if (maxIndex == minIndex) {
            for(int i=0; i<xsize; ++i) {
                x0[i] = 0.0;
                if (i == minIndex) {
                    x0[i] = 1/dx;
                }
            }
        } else {
            final double b = (index - minIndex)/dx;
            final double a = (maxIndex - index)/dx;
            for(int i=0; i<xsize; ++i) {
                x0[i] = 0.0;
                if (i == minIndex) {
                    x0[i] = a;
                } else if (i == maxIndex) {
                    x0[i] = b;
                }
            }
        }
        return x0;
    }
}
