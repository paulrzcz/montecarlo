package cz.paulrz.montecarlo.tests;

import cz.paulrz.montecarlo.estimator.FokkerLikelihood;
import cz.paulrz.montecarlo.single.GeometricBrownianMotionProcess;
import junit.framework.TestCase;

public class FokkerTests  extends TestCase {

    private final int size = 10;

    private FokkerLikelihood fokker = new FokkerLikelihood();
    private double[] points = new double[size];

    public FokkerTests() {
        for(int i=0; i < size; ++i){
            points[i] = 1.0;
        }
    }

    public void testGbm() {
        GeometricBrownianMotionProcess process = new GeometricBrownianMotionProcess(1.0, 0.0, 1.0);

        double v = fokker.value(process, points, 1.0/24/60);

        System.out.println(v);
    }
}
