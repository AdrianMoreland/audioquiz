package com.adrian.model.stats;

/**
 * Represents a data point in a chart.
 * [x] The x value of the data point.
 * [y] The y value of the data point.
 */
public class ChartDataPoint {
    private final double x;
    private final double y;

    public ChartDataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
