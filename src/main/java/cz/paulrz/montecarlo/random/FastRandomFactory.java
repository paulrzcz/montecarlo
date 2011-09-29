package cz.paulrz.montecarlo.random;

import org.apache.commons.math.random.NormalizedRandomGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: paul
 * Date: 29/9/11
 * Time: 09:24 AM
 */
public class FastRandomFactory implements RandomGeneratorFactory {
    private AtomicInteger shift = new AtomicInteger(0);

    public NormalizedRandomGenerator newGenerator() {
        return new FastGaussianRandomGenerator(shift.getAndIncrement());
    }
}
