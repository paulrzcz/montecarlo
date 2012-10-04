package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.estimator.TridiagonalOperator;
import junit.framework.TestCase;

public class TridiagonalTests extends TestCase {

    public void testIdentity() {
        TridiagonalOperator operator = new TridiagonalOperator(5, 0.0, 1.0, 0.0);
        double[] x = new double[] {1, 2, 3, 4, 5};

        double[] result = operator.solveFor(x);

        for(int i=0; i<x.length; ++i) {
            assertEquals(x[i], result[i]);
        }
    }

    /*
    Maple Result:
    [[-13.4307692307693],[20.6153846153847],[-17.0000000000000],[13.8461538461539],[-1.92307692307694]]
     */

    public void testSimple() {
        TridiagonalOperator operator = new TridiagonalOperator(5, 0.5, 1.0, 0.7);
        double[] x = new double[] {1, 2, 3, 4, 5};
        final double[] expectedResult = new double[]
                {-13.4307692307693,20.6153846153847,-17.0000000000000,13.8461538461539,-1.92307692307694};

        double[] result = operator.solveFor(x);

        for(int i=0; i<x.length; ++i) {
            assertTrue(expectedResult[i] - result[i] < 1e-10);
        }
    }
}
