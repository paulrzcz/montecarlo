package cz.paulrz.montecarlo.random;


/**
 * The inverse normal cumulative distribution is a non-linear function for which
 * no closed-form solution exists. The function is continuous, monotonically increasing,
 * infinitely differentiable, and maps the open interval (0,1) to the whole real line.
 *
 * @see <a href="http://home.online.no/~pjacklam/notes/invnorm/">
 * 		An algorithm for computing the inverse normal cumulative distribution function</a>
 *
 */

public final class InverseCumulativeNormal {

    //
    // static final private fields
    //

    static final private double a1 = -3.969683028665376e+01;
    static final private double a2 =  2.209460984245205e+02;
    static final private double a3 = -2.759285104469687e+02;
    static final private double a4 =  1.383577518672690e+02;
    static final private double a5 = -3.066479806614716e+01;
    static final private double a6 =  2.506628277459239e+00;

    static final private double b1 = -5.447609879822406e+01;
    static final private double b2 =  1.615858368580409e+02;
    static final private double b3 = -1.556989798598866e+02;
    static final private double b4 =  6.680131188771972e+01;
    static final private double b5 = -1.328068155288572e+01;

    static final private double c1 = -7.784894002430293e-03;
    static final private double c2 = -3.223964580411365e-01;
    static final private double c3 = -2.400758277161838e+00;
    static final private double c4 = -2.549732539343734e+00;
    static final private double c5 =  4.374664141464968e+00;
    static final private double c6 =  2.938163982698783e+00;

    static final private double d1 =  7.784695709041462e-03;
    static final private double d2 =  3.224671290700398e-01;
    static final private double d3 =  2.445134137142996e+00;
    static final private double d4 =  3.754408661907416e+00;

    //
    // Limits of the approximation regions (break-points)
    //
    static final private double xlow = 0.02425;
    static final private double xhigh = 1.0 - xlow;


    //
    // public constructors
    //

    private InverseCumulativeNormal() {

    }

    public final static double QL_EPSILON           =   Math.ulp(1.0);
    private final static double Tolerance           = 42*QL_EPSILON;

    public static double op(final double x)/* @ReadOnly */{

        if (x < xlow) {
            // Rational approximation for the lower region 0<x<u_low
            final double z = Math.sqrt(-2.0 * Math.log(x));
            return (((((c1*z+c2)*z+c3)*z+c4)*z+c5)*z+c6) / ((((d1*z+d2)*z+d3)*z+d4)*z+1.0);
        } else if (x <= xhigh) {
            // Rational approximation for the central region u_low<=x<=u_high
            final double z = x-0.5;
            final double r = z*z;
            return (((((a1*r+a2)*r+a3)*r+a4)*r+a5)*r+a6)*z / (((((b1*r+b2)*r+b3)*r+b4)*r+b5)*r+1.0);
        } else {
            // Rational approximation for the upper region u_high<x<1
            final double z = Math.sqrt(-2.0 * Math.log(1.0 - x));
            return -(((((c1*z+c2)*z+c3)*z+c4)*z+c5)*z+c6) / ((((d1*z+d2)*z+d3)*z+d4)*z+1.0);
        }

        // return z;
    }

    static public boolean isClose(final double x, final double y) {
        return isClose(x, y, 42);
    }

    static public boolean isClose(final double x, final double y, final int n) {
        final double diff = Math.abs(x-y);
        final double tolerance = n * QL_EPSILON;
        return diff <= tolerance*Math.abs(x) &&
           diff <= tolerance*Math.abs(y);
    }

    static public boolean isCloseEnough(final double x, final double y) {
        final double diff = Math.abs(x-y);
        return diff <= Tolerance*Math.abs(x) ||
            diff <= Tolerance*Math.abs(y);
    }

    static public boolean isCloseEnoughToOne(final double x) {
        final double diff = Math.abs(x-1.0);
        return diff <= Tolerance || diff <= Tolerance*Math.abs(x);
    }

    static public boolean isCloseEnough(final double x, final double y, final int n) {
        final double diff = Math.abs(x-y);
        final double tolerance = n * QL_EPSILON;
        return diff <= tolerance*Math.abs(x) ||
            diff <= tolerance*Math.abs(y);
    }

}
