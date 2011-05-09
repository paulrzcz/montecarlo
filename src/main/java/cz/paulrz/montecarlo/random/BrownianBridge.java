package cz.paulrz.montecarlo.random;

import org.apache.commons.math.util.FastMath;

/**
 * User: paul
 * Date: 9/5/11
 * Time: 10:36 AM
 */
public final class BrownianBridge {
    private final int size_;
    private final double dt;
    private final double sqrtdt;
    private final int[] bridgeIndex_, leftIndex_, rightIndex_;
    private final double[] leftWeight_, rightWeight_, stdDev_;
    private final double[] t_;

    public BrownianBridge(int steps, double dt) {
        size_ = steps;
        this.dt = dt;
        sqrtdt = FastMath.sqrt(dt);
        bridgeIndex_ = new int[steps];
        leftIndex_ = new int[steps];
        rightIndex_ = new int[steps];
        leftWeight_ = new double[steps];
        rightWeight_ = new double[steps];
        stdDev_ = new double[steps];
        t_ = new double[steps];

        for (int i = 0; i < size_; ++i) {
            t_[i] = i * dt;
        }

        initialize();
    }

    private void initialize() {
        // map is used to indicate which points are already constructed.
        // If map[i] is zero, path point i is yet unconstructed.
        // map[i]-1 is the index of the variate that constructs
        // the path point # i.
        int[] map = new int[size_];

        //  The first point in the construction is the global step.
        map[size_ - 1] = 1;
        //  The global step is constructed from the first variate.
        bridgeIndex_[0] = size_ - 1;
        //  The variance of the global step
        stdDev_[0] = FastMath.sqrt(t_[size_ - 1]);
        //  The global step to the last point in time is special.
        leftWeight_[0] = rightWeight_[0] = 0.0;
        for (int j = 0, i = 1; i < size_; ++i) {
            // Find the next unpopulated entry in the map.
            while (map[j] != 0)
                ++j;
            int k = j;
            // Find the next populated entry in the map from there.
            while (map[k] == 0)
                ++k;
            // l-1 is now the index of the point to be constructed next.
            int l = j + ((k - 1 - j) >> 1);
            map[l] = i;
            // The i-th Gaussian variate will be used to set point l-1.
            bridgeIndex_[i] = l;
            leftIndex_[i] = j;
            rightIndex_[i] = k;
            if (j != 0) {
                leftWeight_[i] = (t_[k] - t_[l]) / (t_[k] - t_[j - 1]);
                rightWeight_[i] = (t_[l] - t_[j - 1]) / (t_[k] - t_[j - 1]);
                stdDev_[i] =
                        FastMath.sqrt(((t_[l] - t_[j - 1]) * (t_[k] - t_[l]))
                                / (t_[k] - t_[j - 1]));
            } else {
                leftWeight_[i] = (t_[k] - t_[l]) / t_[k];
                rightWeight_[i] = t_[l] / t_[k];
                stdDev_[i] = FastMath.sqrt(t_[l] * (t_[k] - t_[l]) / t_[k]);
            }
            j = k + 1;
            if (j >= size_)
                j = 0;    //  wrap around
        }

    }


    //! Brownian-bridge generator function
    /*! Transforms an input sequence of random variates into a
        sequence of variations in a Brownian bridge path.

        \param begin  The start iterator of the input sequence.
        \param end    The end iterator of the input sequence.
        \param output The start iterator of the output sequence.

        \note To get the canonical Brownian bridge which starts
                  and finishes at the same value, the first element of
                  the input sequence must be zero. Conversely, to get
                  a sloped bridge set the first element to a non-zero
                  value. In this case, the final value in the bridge
                  will be sqrt(last time point)*(first element of
                  input sequence).
    */
    public double[] transform(final double[] input) {
        final double[] output = new double[size_];

        output[size_ - 1] = stdDev_[0] * input[0];
        for (int i = 1; i < size_; ++i) {
            final int j = leftIndex_[i];
            final int k = rightIndex_[i];
            final int l = bridgeIndex_[i];
            if (j != 0) {
                output[l] =
                        leftWeight_[i] * output[j - 1] +
                                rightWeight_[i] * output[k] +
                                stdDev_[i] * input[i];
            } else {
                output[l] =
                        rightWeight_[i] * output[k] +
                                stdDev_[i] * input[i];
            }
        }
        // ...after which, we calculate the variations and
        // normalize to unit times
        for (int i = size_ - 1; i >= 1; --i) {
            output[i] -= output[i - 1];
            output[i] /= sqrtdt;
        }
        output[0] /= sqrtdt;

        return output;
    }

}
