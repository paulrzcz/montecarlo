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

/**
 * Base class of stochastic processes
 * 
 */
public abstract class StochasticProcess implements GenericProcess {
    protected Discretization discretization;
    protected final double x0;

    /**
     * Stochastic process constructor
     * 
     * @param x0 initial position value
     * @param d Discretization
     */
    protected StochasticProcess(double x0, Discretization d) {
        this.x0 = x0;
        discretization = d;
    }

    /**
     * Gets initial position value
     * 
     * @return
     */
    public double getInitialX() {
        return x0;
    }

    /**
     * Drift component of stochastic process
     * 
     * @param t Time
     * @param x Position
     * @return drift
     */
    public abstract double drift(double t, double x) throws FunctionEvaluationException;

    /**
     * Diffusion component of stochastic process
     * 
     * @param t Time
     * @param x Position
     * @return diffusion
     */
    public abstract double diffusion(double t, double x) throws FunctionEvaluationException;

    /**
     * Returns position after time interval dt
     * 
     * @param t Time
     * @param x Position
     * @param dt Time step
     * @param dw Standard Brownian Step
     * @return Position after time step
     */
    public double evolve(double t, double x, double dt, double dw) throws FunctionEvaluationException {
        return x + discretization.drift(this, t, x, dt)
                + discretization.diffusion(this, t, x, dt) * dw;
    }
}
