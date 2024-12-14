package com.satellite.messenger.app.dto;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class PointDTO implements Locable {

    private Double x;

    private Double y;

    public PointDTO(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void moveX(Double x) {
        this.x += x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void moveY(Double y) {
        this.y += y;
    }

    public Double getXDistance(final Locable locable) {
        return locable.getX() - this.x;
    }

    public Double getYDistance(final Locable locable) {
        return locable.getY() - this.y;
    }

    public Double getDistance(final Locable locable) {
        final double xDistance = getXDistance(locable);
        final double yDistance = getYDistance(locable);
        return sqrt(pow(xDistance, 2) + pow(yDistance, 2));
    }

    public void round(int decimals) {
        double scale = pow(10, decimals);
        x = Math.round(x * scale) / scale;
        y = Math.round(y * scale) / scale;
    }
}
