package com.satellite.messenger.app.dto;

public interface Locable {

    public Double getX();

    public Double getY();

    public Double getXDistance(final Locable x);

    public Double getYDistance(final Locable y);

    public Double getDistance(final Locable locable);

}
