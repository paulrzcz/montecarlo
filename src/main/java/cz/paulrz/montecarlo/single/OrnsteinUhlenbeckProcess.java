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

/**
 * This class represents <a
 * href="http://en.wikipedia.org/wiki/Ornstein�Uhlenbeck_process"
 * >Ornstein-Uhlenbeck process</a>, also known as the mean-reverting process.
 * 
 */
public class OrnsteinUhlenbeckProcess extends StochasticProcess1D {

    private final double theta;
    private final double mu;
    private final double sigma;

    /**
     * Constructor of the process
     * 
     * @param x0 Starting point
     * @param theta Theta parameter
     * @param mu Mu parameter
     * @param sigma Sigma parameter
     */
    public OrnsteinUhlenbeckProcess(double x0, double theta, double mu,
            double sigma) {
        super(x0, new EulerDiscretization());
        this.theta = theta;
        this.mu = mu;
        this.sigma = sigma;
    }

    public double getTheta() {
        return theta;
    }

    public double getMu() {
        return mu;
    }

    public double getSigma() {
        return sigma;
    }

    /** {@inheritDoc} */
    @Override
    public double drift(double t, double x) {
        return theta * (mu - x);
    }

    /** {@inheritDoc} */
    @Override
    public double diffusion(double t, double x) {
        return sigma;
    }

    @Override
    public String toString() {
        return "OUP : x0 = "+x0+"; mu = "+mu+"; sigma = "+sigma+"; theta = "+theta;
    }
}
