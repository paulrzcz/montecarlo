package cz.paulrz.montecarlo.single;

public class MaxMinCloseValuation implements PathValuation<MaxMinClose> {
    public MaxMinClose value(Path path) {
        double[] values = path.getValues();
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
