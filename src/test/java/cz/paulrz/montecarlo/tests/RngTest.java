package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;
import junit.framework.TestCase;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 21/4/11
 * Time: 09:32 AM
 */
public class RngTest extends TestCase {

    private static final int count = 100000000;

    private void measure(NormalizedRandomGenerator grg) {
        grg.nextNormalizedDouble();
        long ms = System.currentTimeMillis();
        for(int i=0; i<count; ++i)
            grg.nextNormalizedDouble();

        ms = System.currentTimeMillis() - ms;
        System.out.println(ms+" ms");
        double rate = count*1000.0/ms;
        System.out.format("%f numbers/s %n", rate);
    }

    /*
    public void testApacheGenerator() {
        System.out.println("Apache Commons generator");
        GaussianRandomGenerator grg = new GaussianRandomGenerator(new MersenneTwister());
        measure(grg);
    }  */

    public void testFastGenerator() {

        System.out.println("Fast generator");
        FastGaussianRandomGenerator fgrg = new FastGaussianRandomGenerator();
        measure(fgrg);
    }

    /*
    public void testClGenerator() {
        System.out.println("OpenCl generator");
        ClGaussianRandomGenerator grg = new ClGaussianRandomGenerator();
        measure(grg);
    } */
}
