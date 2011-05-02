package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;
import junit.framework.TestCase;
import org.apache.commons.math.random.GaussianRandomGenerator;
import org.apache.commons.math.random.MersenneTwister;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 21/4/11
 * Time: 09:32 AM
 */
public class RngTest extends TestCase {

    private void measure(NormalizedRandomGenerator grg) {
        long ms = System.currentTimeMillis();
        for(int i=0; i<1000000; ++i)
            grg.nextNormalizedDouble();
        ms = System.currentTimeMillis() - ms;
        System.out.println(ms+" ms");
    }

    public void testApacheGenerator() {
        System.out.println("Apache Commons generator");
        GaussianRandomGenerator grg = new GaussianRandomGenerator(new MersenneTwister());
        measure(grg);
    }

    public void testFastGenerator() {
        System.out.println("Fast generator");
        FastGaussianRandomGenerator fgrg = new FastGaussianRandomGenerator();
        measure(fgrg);
    }
}
