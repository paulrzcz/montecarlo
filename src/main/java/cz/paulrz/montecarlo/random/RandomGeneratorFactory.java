package cz.paulrz.montecarlo.random;

import org.apache.commons.math3.random.NormalizedRandomGenerator;

/**
 * User: paul
 * Date: 29/9/11
 * Time: 08:25 AM
 */
public interface RandomGeneratorFactory {

    NormalizedRandomGenerator newGenerator();
}
