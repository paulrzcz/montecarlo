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
 * Discretization of a stochastic process over given time interval
 * 
 */
public interface Discretization {
    /**
     * Drift component discretization
     * 
     * @param process Stochastic process
     * @param t Time
     * @param x Position
     * @param dt Time step
     * @return Drift value step
     */
    double drift(StochasticProcess process, double t, double x, double dt) throws FunctionEvaluationException;

    /**
     * Diffusion component discretization
     * 
     * @param process Stochastic process
     * @param t Time
     * @param x Position
     * @param dt Time step
     * @return Diffusion value step
     */
    double diffusion(StochasticProcess process, double t, double x, double dt) throws FunctionEvaluationException;
}
