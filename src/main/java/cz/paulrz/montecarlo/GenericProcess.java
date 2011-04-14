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
 * Generic stochastic process X(t)
 * 
 */
public interface GenericProcess {

    /**
     * Access initial X_0
     * 
     * @return
     */
    double getInitialX();

    /**
     * Single step of the process from x in time interval dt with given
     * stochastic part
     * 
     * @param t Time
     * @param x Position
     * @param dt Time interval
     * @param dw Stochastic part
     * @return Position at x+dt, i.e. X(t+dt)
     */
    double evolve(double t, double x, double dt, double dw) throws FunctionEvaluationException;
}
