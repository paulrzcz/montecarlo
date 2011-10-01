package cz.paulrz.montecarlo.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: paul
 * Date: 1/10/11
 * Time: 18:13 PM
 */
public final class CpuPool {
    public static final int numOfCpu = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService executorService = Executors.newFixedThreadPool(numOfCpu);


}
