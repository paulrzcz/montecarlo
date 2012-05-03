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

import org.apache.commons.math3.exception.OutOfRangeException;

/**
 * Path is a sample of stochastic process's path discretized with uniform time
 * step. It is simply a set of points {x,t}
 * 
 */
public final class Path {
    private int currentIndex = 0;
    private final int length;
    private final double[] values;
    private final double dt;

    /**
     * Constructor of path
     * 
     * @param length Number of points
     * @param dt Time step
     */
    public Path(int length, double dt) {
        this.dt = dt;
        this.length = length;
        values = new double[length];
    }

    /**
     * Gets time step (dt)
     * 
     * @return Value of the step
     */
    public double getTimeStep() {
        return dt;
    }

    /**
     * Gets all coordinate values of the path
     * 
     * @return X values
     */
    public double[] getValues() {
        return values;
    }

    /**
     * Gets length of the path
     * 
     * @return Number of points in the path
     */
    int getLength() {
        return length;
    }

    /**
     * Adds a new value to the end of path
     * 
     * @param value Value to add
     * @throws org.apache.commons.math3.exception.OutOfRangeException The exception is thrown if path is complete,
     *             i.e. number of points added equals to path's length
     */
    public void addValue(double value) throws OutOfRangeException {
        if (currentIndex >= length)
            throw new OutOfRangeException(currentIndex, 0, length);

        values[currentIndex] = value;
        currentIndex++;
    }
}
