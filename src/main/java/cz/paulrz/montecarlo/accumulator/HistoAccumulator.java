package cz.paulrz.montecarlo.accumulator;

import java.util.HashMap;

/**
 * Accumulator building a histogram of path valuations
 */
public class HistoAccumulator implements Accumulator<Double, HashMap<Double, Integer>> {

    /**
     * Default constructor
     */
    public HistoAccumulator() {
        _histo = new HashMap<Double, Integer>();
    }

    /**
     * Copy constructor
     * @param c Source accumulator
     */
    public HistoAccumulator(HistoAccumulator c) {
        _histo = (HashMap<Double, Integer>)c._histo.clone();
    }

    /**
     * Histogram map
     */
    private final HashMap<Double, Integer> _histo;

    /**
     * Add single value
     * @param x Path value
     */
    public void addValue(final Double x) {
        final double rounded = Math.round(x*1000.0)/1000.0;
        if (_histo.containsKey(rounded))
        {
            int v = _histo.get(rounded);
            _histo.put(rounded, v+1);
        }
        else
            _histo.put(rounded, 1);
    }

    /**
     * Calculates a value of accumulator
     * @return value
     */
    public HashMap<Double, Integer> value() {
        return _histo;
    }

    /**
     * Norm is not implemented
     * @param other Second accumulator
     * @return Zero
     */
    public double norm(Accumulator<Double, HashMap<Double, Integer>> other) {
        return 0;
    }

    /**
     * Deep copy
     * @return Accumulator copy
     */
    public Accumulator<Double, HashMap<Double, Integer>> deepCopy() {
        return new HistoAccumulator(this);
    }

    /**
     * Clone. Same as deep copy
     * @return Copy
     */
    public HistoAccumulator clone() throws CloneNotSupportedException {
        return new HistoAccumulator(this);
    }
}
