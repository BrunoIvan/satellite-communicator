package com.satellite.messenger.dto;

import java.io.Serializable;

public class DecodeMessagePosDTO implements Serializable {

    private double x;

    private double y;

    public DecodeMessagePosDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DecodeMessagePosDTO(double[] result) {
        this(result[0], result[1]);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
