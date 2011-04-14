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
import org.apache.commons.math.analysis.MultivariateRealFunction;

/**
 * Generic Ito process X(t) = mu(x,t) dt + sigma(x,t) dW_t
 * 
 */
public class ItoProcess extends StochasticProcess {

    private final MultivariateRealFunction mu;
    private final MultivariateRealFunction sigma;

    /**
     * Constructs Ito process with starting point x0, drift mu and diffusion
     * sigma
     * 
     * @param x0 Starting point
     * @param mu Drift function mu(x,t)
     * @param sigma Diffusion function sigma(x,t)
     */
    public ItoProcess(double x0, MultivariateRealFunction mu,
            MultivariateRealFunction sigma) {
        super(x0, new EulerDiscretization());
        this.mu = mu;
        this.sigma = sigma;
    }

    /** {@inheritDoc} */
    @Override
    public double drift(double t, double x) throws FunctionEvaluationException {
        double[] input = new double[] { t, x };
        return mu.value(input);
    }

    /** {@inheritDoc} */
    @Override
    public double diffusion(double t, double x) throws FunctionEvaluationException {
        double[] input = new double[] { t, x };
        return sigma.value(input);
    }

}
