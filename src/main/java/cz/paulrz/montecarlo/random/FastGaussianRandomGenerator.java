package cz.paulrz.montecarlo.random;

import org.apache.commons.math.random.NormalizedRandomGenerator;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * User: paul
 * Date: 21/4/11
 * Time: 09:28 AM
 */
public final class FastGaussianRandomGenerator implements NormalizedRandomGenerator {

    private final MersenneTwister mt = new MersenneTwister();

    public final double nextNormalizedDouble() {
        final double rnd = nextDouble();
        return InverseCumulativeNormal.op(rnd);
    }

    public final double nextDouble() {
        final long high = ((long) mt.next(26)) << 26;
        final long low  = mt.next(26);
        // final long x    = ((high | low) << 11) | 0x03fc;

        // return convert(x);
        return (high | low) * 0x1.0p-52d;
    }

    private static Unsafe unsafe;

    static {
        try {
            unsafe = getUnsafeInstance();
            buffer = unsafe.allocateMemory(8);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static Unsafe getUnsafeInstance() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
    IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }

    private static long buffer;

    public static double convert(long x) {
        unsafe.putLong(buffer, x);
        return unsafe.getDouble(buffer);
    }

    public static long convert(double x) {
        unsafe.putDouble(buffer, x);
        return unsafe.getLong(buffer);
    }
}
