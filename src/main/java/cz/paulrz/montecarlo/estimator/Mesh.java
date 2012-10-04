package cz.paulrz.montecarlo.estimator;

public class Mesh {

    public double dx;
    public double xStart;
    public double xEnd;
    public int sizeX;

    public double dt;
    public double tStart;
    public double tEnd;
    public int sizeT;

    public Mesh(double xStart, int sizeX, double xEnd, double tStart, int sizeT, double tEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.sizeX = sizeX;
        dx = (xEnd - xStart) / sizeX;

        this.tStart = tStart;
        this.tEnd = tEnd;
        this.sizeT = sizeT;
        dt = (tEnd - tStart) / sizeT;
    }

    public double getX(int i) {
        return xStart + dx*i;
    }

    public double getT(int i) {
        return tStart + dt*i;
    }
}
