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
package cz.paulrz.montecarlo.single;

import cz.paulrz.montecarlo.random.BrownianBridge;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.random.NormalizedRandomGenerator;

/**
 * SimplePathGenerator1D constructs random paths that satisfy stochastic process.
 * 
 */
public final class SimplePathGenerator1D implements PathGenerator1D {
    private final GenericProcess1D process;
    private final int timeSteps;
    private final double dt;
    private final NormalizedRandomGenerator generator;
    private final BrownianBridge bridge;

    /**
     * Constructor of SimplePathGenerator1D
     * 
     * @param process Stochastic Process
     * @param timeSteps Number of time steps
     * @param duration Total duration of the process
     * @param generator Random generator of normalized real values
     */
    public SimplePathGenerator1D(GenericProcess1D process, int timeSteps,
                                 double duration, NormalizedRandomGenerator generator, boolean useBridge) {
        this.process = process;
        this.timeSteps = timeSteps;
        this.generator = generator;
        this.dt = duration / timeSteps;
        bridge = useBridge ? new BrownianBridge(timeSteps, dt) : null;
    }

    public Path next() throws FunctionEvaluationException {
        final Path path = new Path(timeSteps, dt);
        path.addValue(process.getInitialX());

        double[] dw = new double[timeSteps];
        dw[0] = 0.0;
        for(int i = 1; i<timeSteps; ++i) {
            dw[i] = generator.nextNormalizedDouble();
        }

        if (bridge!=null)
            dw = bridge.transform(dw);

        double t = 0.0;
        for (int i = 1; i < timeSteps; ++i) {
            path.addValue(process.evolve(t, path.getValues()[i - 1], dt, dw[i]));
            t += dt;
        }

        return path;
    }
}
