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

/**
 * This class represent <a
 * href="http://en.wikipedia.org/wiki/Geometric_Brownian_motion">Geometric
 * Brownian Motion</a> with constant drift and diffusion.
 * 
 */
public final class GeometricBrownianMotionProcess extends StochasticProcess {

    private final double mu;
    private final double sigma;

    /**
     * Constructs geometric brownian motion.
     * 
     * @param x0 Starting point of the process
     * @param mu Process drift
     * @param sigma Process diffusion
     */
    public GeometricBrownianMotionProcess(double x0, double mu, double sigma) {
        super(x0, new EulerDiscretization());
        this.mu = mu;
        this.sigma = sigma;
    }

    /** {@inheritDoc} */
    @Override
    public double drift(double t, double x) {
        return mu * x;
    }

    /** {@inheritDoc} */
    @Override
    public double diffusion(double t, double x) {
        return sigma * x;
    }

}
