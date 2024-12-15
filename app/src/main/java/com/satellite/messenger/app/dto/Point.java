package com.satellite.messenger.app.dto;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Point implements Roundable, Locable {

    private Double x;

    private Double y;

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Double getXDistance(final Locable locable) {
        return locable.getX() - this.x;
    }

    @Override
    public Double getYDistance(final Locable locable) {
        return locable.getY() - this.y;
    }

    @Override
    public Double getDistance(final Locable locable) {
        final double xDistance = getXDistance(locable);
        final double yDistance = getYDistance(locable);
        return sqrt(pow(xDistance, 2) + pow(yDistance, 2));
    }

    @Override
    public void round(int decimals) {
        double scale = pow(10, decimals);
        x = Math.round(x * scale) / scale;
        y = Math.round(y * scale) / scale;
    }
}
