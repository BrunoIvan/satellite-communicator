package com.satellite.messenger.app.dto;

import java.text.DecimalFormat;

public interface Locable {

    public Double getX();

    public void setX(Double x);

    public Double getY();

    public void setY(Double y);

    public void moveX(Double x);

    public void moveY(Double y);

    public Double getXDistance(final Locable x);

    public Double getYDistance(final Locable y);

    public Double getDistance(final Locable locable);

    public void round(int decimals);

}
