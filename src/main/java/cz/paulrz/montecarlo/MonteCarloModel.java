/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.paulrz.montecarlo;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * This class implements Monte Carlo model. It provides constructs paths,
 * evaluate them and collect statistics.
 * 
 */
public final class MonteCarloModel<TValue> {
    private final Accumulator<TValue> summary;
    private final PathGenerator pathGenerator;
    private final PathValuation<TValue> pathValuation;

    /**
     * Constructor of Monte Carlo model
     * 
     * @param random Underlying random number generator
     * @param process Underlying stochastic process
     * @param duration Duration of paths in time units
     * @param timeSteps Path discretization time step
     * @param valuation Path valuation function
     * @param statistics Statistics summary
     */
    public MonteCarloModel(NormalizedRandomGenerator random,
            GenericProcess process, double duration, int timeSteps,
            PathValuation<TValue> valuation, Accumulator<TValue> statistics,
            boolean useAntithetic) {
        this.summary = statistics;
        if (useAntithetic)
            this.pathGenerator = new AntitheticPathGenerator(process, timeSteps, duration, random);
        else
            this.pathGenerator = new SimplePathGenerator(process, timeSteps, duration,
                    random);
        this.pathValuation = valuation;
    }

    /**
     *
     * Constructor of Quasi Monte Carlo Model with Sobol generator
     *
     * @param process Underlying stochastic process
     * @param duration Duration of paths in time units
     * @param timeSteps Path discretization time step
     * @param valuation Path valuation function
     * @param statistics Statistics summary
     */
    public MonteCarloModel(GenericProcess process, double duration, int timeSteps,
                           PathValuation<TValue> valuation, Accumulator<TValue> statistics) throws Exception {
        this.summary = statistics;
        this.pathGenerator = new SobolPathGenerator(process, timeSteps, duration);
        this.pathValuation = valuation;
    }

    /**
     * Adds path samples to statistics
     * 
     * @param samples Number of paths to add
     */
    public int addSamples(int samples) throws MathException {
        for (int i = 0; i < samples; ++i) {
            final Path path = pathGenerator.next();
            final TValue pathValue = pathValuation.value(path);
            summary.addValue(pathValue);
        }
        return samples;
    }

    public int addSamples(int minSamples, double eps, int maxSteps) throws MathException {
        addSamples(minSamples);
        Accumulator<TValue> prev = summary.deepCopy();
        int steps = 1;
        while(steps==1 || summary.norm(prev) > eps) {
            prev = summary.deepCopy();
            addSamples(minSamples);
            steps++;

            if (steps>maxSteps)
                return steps*minSamples;
        }
        return steps*minSamples;
    }

    /**
     * Gets statistics summary
     * 
     * @return Statistics summary
     */
    public Accumulator<TValue> getStats() {
        return summary;
    }
}
