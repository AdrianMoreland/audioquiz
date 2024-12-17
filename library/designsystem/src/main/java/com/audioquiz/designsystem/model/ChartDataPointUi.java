package com.audioquiz.designsystem.model;

public class ChartDataPointUi {
    private int id;
    private final double x;
    private final double y;

    public ChartDataPointUi(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
