package com.satellite.messenger.domain.dto;

import com.satellite.messenger.domain.exceptions.location.CircleWithinException;
import com.satellite.messenger.domain.exceptions.location.EqualCircleException;
import com.satellite.messenger.domain.exceptions.location.NotIntersectionException;

import static java.lang.Math.*;

public class CircleDTO implements Locable {

    private final PointDTO point;

    private final Double radio;

    private final Double radioPow;

    public CircleDTO(Double x, Double y, Double radio) {
        this.point = new PointDTO(x, y);
        this.radio = radio;
        this.radioPow = radio * radio;
    }

    public Double getRadio() {
        return radio;
    }

    public Double getX() {
        return point.getX();
    }

    public void setX(Double x) {
        point.setX(x);
    }

    public void moveX(Double x) {
        point.moveX(x);
    }

    public Double getY() {
        return point.getY();
    }

    public void setY(Double y) {
        point.setY(y);
    }

    public void moveY(Double y) {
        point.moveY(y);
    }

    public Double getXDistance(final Locable locable) {
        return point.getXDistance(locable);
    }

    public Double getYDistance(final Locable locable) {
        return point.getYDistance(locable);
    }

    public Double getDistance(final Locable locable) {
        return point.getDistance(locable);
    }

    @Override
    public void round(int decimals) {
        point.round(decimals);
    }

    public void checkNoIntersection(
            final CircleDTO circleDTO
    ) {
        final Double distance = getDistance(circleDTO);
        if (distance > (radio + circleDTO.getRadio())) {
            throw new NotIntersectionException();
        }
    }

    public void checkWithinCircle(
            final CircleDTO circleDTO
    ) {
        final Double distance = getDistance(circleDTO);
        if (distance < (abs(circleDTO.getRadio() - radio))) {
            throw new CircleWithinException();
        }
    }

    public void checkCoincidentCircle(
            final CircleDTO circleDTO
    ) {
        final Double distance = getDistance(circleDTO);
        if (distance.equals(0.) && radio.equals(circleDTO.getRadio())) {
            throw new EqualCircleException();
        }
    }

    public double chordDistance(final CircleDTO circleDTO) {
        final Double distance = getDistance(circleDTO);
        return (radioPow - circleDTO.radioPow + pow(distance, 2)) / (abs(distance) * 2);
    }

    public double halfChordLength(final CircleDTO circleDTO) {
        final double chordDistance = chordDistance(circleDTO);
        return sqrt(radioPow - pow(chordDistance, 2));
    }

    public double chordMidPointX(final CircleDTO circleDTO) {
        final double distance = getDistance(circleDTO);
        final double xDistance = getXDistance(circleDTO);
        final double chordDistance = chordDistance(circleDTO);
        return point.getX() + (chordDistance * xDistance) / abs(distance);
    }

    public double chordMidPointY(final CircleDTO circleDTO) {
        final double distance = getDistance(circleDTO);
        final double yDistance = getYDistance(circleDTO);
        final double chordDistance = chordDistance(circleDTO);
        return point.getY() + (chordDistance * yDistance) / abs(distance);
    }
    
    public double xVector(final CircleDTO circleDTO) {
        final double distance = getDistance(circleDTO);
        final double yDistance = getYDistance(circleDTO);
        final double halfChordLength = halfChordLength(circleDTO);
        return (halfChordLength * yDistance) / abs(distance);
    }

    public double yVector(final CircleDTO circleDTO) {
        final double distance = getDistance(circleDTO);
        final double xDistance = getXDistance(circleDTO);
        final double halfChordLength = halfChordLength(circleDTO);
        return (halfChordLength * xDistance) / abs(distance);
    }

    public boolean intersects(final Locable locable) {
        final double distance = getDistance(locable);
        return Math.round(distance) == Math.round(radio);
    }
}
