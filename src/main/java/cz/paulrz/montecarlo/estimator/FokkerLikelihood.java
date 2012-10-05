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


        double y = 0.0;

        final ImplicitStencil operator = new FokkerEulerOperator(process, mesh);

        for(int i=0; i< points.length - 1; ++i) {
            double[] x = makeInitialDistribution(points[i], min, dx);
            // estimate in dt time
            x = makeStep(x, operator, dt*i);
            // find log probability
            // sum probability
            final double p = interpolate(x, min, dx, points[i+1]);
            y += FastMath.log(p);
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
        final TridiagonalOperator trop = operator.createOperator(t);
        final double[] rhs = operator.createRhs(x, t);

        return trop.solveFor(rhs);
    }

    private double[] makeInitialDistribution(double point, double min, double dx) {
        final double[] x0 = new double[xsize];
        final double d = min - point;

        for(int i=0; i < xsize; ++i) {
            x0[i] = nascent(d + dx*i, dx);
        }

        return x0;
    }


    private static double nascent(double x, double eps) {
        return FastMath.max(1 - FastMath.abs(x/eps), 0.0) / eps;
    }
}
