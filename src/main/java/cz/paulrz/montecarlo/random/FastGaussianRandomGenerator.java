package cz.paulrz.montecarlo.random;

import org.apache.commons.math3.random.NormalizedRandomGenerator;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * User: paul
 * Date: 21/4/11
 * Time: 09:28 AM
 */
public final class FastGaussianRandomGenerator implements NormalizedRandomGenerator {

    private final MersenneTwister mt;

    public FastGaussianRandomGenerator() {
        mt = new MersenneTwister();
    }

    public FastGaussianRandomGenerator(int shift) {
        mt = new MersenneTwister(System.currentTimeMillis()+shift);
    }

    public final double nextNormalizedDouble() {
        final double rnd = mt.nextDouble();
        return InverseCumulativeNormal.op(rnd);
    }
}
