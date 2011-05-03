package cz.paulrz.montecarlo.multi;

import cz.paulrz.montecarlo.single.MaxMinClose;

/**
 * User: paul
 * Date: 3/5/11
 * Time: 09:42 AM
 */
public class MaxMinCloseValuation implements PathValuation<MaxMinClose> {
    public MaxMinClose value(Path path) {
        double[] values = path.getRow(0);
        int len = values.length;
        double close = values[len-1];
        double max = values[0];
        double min = values[0];

        for(int i=1; i<len; ++i)
        {
            double v = values[i];
            if (max < v) max = v;
            if (min > v) min = v;
        }

        return new MaxMinClose(max, min , close);
    }
}
