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

        // return convert(high | low);
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
    private static final long mantissaMask = (0x1L << 52) - 1;

    public static double convert(long x) {
        if (x==0)
            return 0.0;

        final long p = findHighSetPosition(x);
        final long exponent = (1023-52+p) << 52;

        unsafe.putLong(buffer, ((x << (52-p)) & mantissaMask) | exponent);
        return unsafe.getDouble(buffer);
    }

    public static long convert(double x) {
        unsafe.putDouble(buffer, x);
        return unsafe.getLong(buffer);
    }

    private static final long b[] = {0x2, 0xC, 0xF0, 0xFF00, 0xFFFF0000, 0x7FFFFFFF00000000L};
    private static final long S[] = {1, 2, 4, 8, 16, 32};


    public static long findHighSetPosition(long x) {
        int i;

        long r = 0; // result of log2(v) will go here
        for (i = 4; i >= 0; i--) // unroll for speed...
        {
            if ((x & b[i]) != 0)
            {
                x >>= S[i];
                r |= S[i];
            }
        }
        return r;
    }
}
