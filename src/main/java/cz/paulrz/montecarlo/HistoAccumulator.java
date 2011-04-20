package cz.paulrz.montecarlo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: paul
 * Date: 12/4/11
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class HistoAccumulator implements Accumulator<Double> {

    public HistoAccumulator() {
        histo = new HashMap<Double, Integer>();
    }

    public HistoAccumulator(HistoAccumulator c) {
        histo = (HashMap<Double, Integer>)c.histo.clone();
    }

    public final HashMap<Double, Integer> histo;

    public void addValue(Double x) {
        double rounded = Math.round(x*1000.0)/1000.0;
        if (histo.containsKey(rounded))
        {
            int v = histo.get(rounded);
            histo.put(rounded, v+1);
        }
        else
            histo.put(rounded, 1);
    }

    public double norm(Accumulator<Double> other) {
        return 0;
    }

    public Accumulator<Double> deepCopy() {
        return new HistoAccumulator(this);
    }

    public HistoAccumulator clone() {
        return new HistoAccumulator(this);
    }
}
