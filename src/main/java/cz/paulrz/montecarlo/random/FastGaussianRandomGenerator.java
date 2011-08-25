package cz.paulrz.montecarlo.random;

import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 21/4/11
 * Time: 09:28 AM
 */
public final class FastGaussianRandomGenerator implements NormalizedRandomGenerator {

    private final MersenneTwister mt = new MersenneTwister();

    public double nextNormalizedDouble() {
        final double rnd = mt.nextDouble();
        return InverseCumulativeNormal.op(rnd);
    }
}
