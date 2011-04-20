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
import org.apache.commons.math.util.FastMath;

/**
 * Euler equally spaced discretization
 * 
 */
public final class EulerDiscretization implements Discretization {

    /** {@inheritDoc} */
    public double drift(final StochasticProcess process, final double t, final double x,
                        final double dt) throws FunctionEvaluationException {
        return process.drift(t, x) * dt;
    }

    /** {@inheritDoc} */
    public double diffusion(final StochasticProcess process, final double t, final double x,
            final double dt) throws FunctionEvaluationException {
        return process.diffusion(t, x) * FastMath.sqrt(dt);
    }

}
