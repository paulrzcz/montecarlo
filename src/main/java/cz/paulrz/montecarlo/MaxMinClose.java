package cz.paulrz.montecarlo;

/**
 * Created by IntelliJ IDEA.
 * User: paul
 * Date: 12/4/11
 * Time: 09:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class MaxMinClose {

    public MaxMinClose(double smax, double smin, double sclose) {
        max = smax;
        min = smin;
        close = sclose;
    }

    public final double max;
    public final double min;
    public final double close;
}
