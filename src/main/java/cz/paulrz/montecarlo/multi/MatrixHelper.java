package cz.paulrz.montecarlo.multi;

import cern.colt.matrix.linalg.Blas;
import cern.colt.matrix.linalg.SeqBlas;
import cern.colt.matrix.linalg.SmpBlas;

/**
 * User: paul
 * Date: 2/5/11
 * Time: 10:31 AM
 */
public final class MatrixHelper {

    private static int numOfCpu = 1;
    public static Blas blas;

    static {
        numOfCpu = Runtime.getRuntime().availableProcessors();

        if (numOfCpu > 1)
        {
            SmpBlas.allocateBlas(numOfCpu, SeqBlas.seqBlas);
            blas = SmpBlas.smpBlas;
        }
        else
            blas = SeqBlas.seqBlas;
    }
}
