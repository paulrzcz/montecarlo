package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.random.FastGaussianRandomGenerator;
import junit.framework.Assert;
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

    public void testConvert() {
        double x = 0.00000005;
        long   c = FastGaussianRandomGenerator.convert(x);
        System.out.format("%X%n", c);
        Assert.assertEquals(x, FastGaussianRandomGenerator.convert(c));
    }

    public void testConvertLong() {
        long   x = 0x00342345L;
        long   xd = (x) | (971L << 51);
        double dd = x* 0x1.0p-52d;
        double cd = FastGaussianRandomGenerator.convert(xd);
        Assert.assertEquals(dd, cd);
    }

    /*
    public void testClGenerator() {
        System.out.println("OpenCl generator");
        ClGaussianRandomGenerator grg = new ClGaussianRandomGenerator();
        measure(grg);
    } */
}
