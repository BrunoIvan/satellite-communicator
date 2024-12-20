package com.satellite.messenger.app.dto;

import com.satellite.messenger.app.exceptions.location.CircleWithinException;
import com.satellite.messenger.app.exceptions.location.EqualCircleException;
import com.satellite.messenger.app.exceptions.location.NotIntersectionException;

import static java.lang.Math.*;

public class Circle implements Locable {

    private final Point point;

    private final Double radio;

    private final Double radioPow;

    public Circle(Double x, Double y, Double radio) {
        this.point = new Point(x, y);
        this.radio = radio;
        this.radioPow = radio * radio;
    }

    public Double getRadio() {
        return radio;
    }

    @Override
    public Double getX() {
        return point.getX();
    }

    @Override
    public Double getY() {
        return point.getY();
    }

    @Override
    public Double getXDistance(final Locable locable) {
        return point.getXDistance(locable);
    }

    @Override
    public Double getYDistance(final Locable locable) {
        return point.getYDistance(locable);
    }

    @Override
    public Double getDistance(final Locable locable) {
        return point.getDistance(locable);
    }

    public void checkNoIntersection(
            final Circle circle
    ) {
        final Double distance = getDistance(circle);
        if (distance > (radio + circle.getRadio())) {
            throw new NotIntersectionException();
        }
    }

    public void checkWithinCircle(
            final Circle circle
    ) {
        final Double distance = getDistance(circle);
        if (distance < (abs(circle.getRadio() - radio))) {
            throw new CircleWithinException();
        }
    }

    public void checkCoincidentCircle(
            final Circle circle
    ) {
        final Double distance = getDistance(circle);
        final Double radioDistance = circle.getRadio() - radio;
        if (distance + radioDistance == 0) {
            throw new EqualCircleException();
        }
    }

    public double chordDistance(final Circle circle) {
        final Double distance = getDistance(circle);
        return (radioPow - circle.radioPow + pow(distance, 2)) / (abs(distance) * 2);
    }

    public double halfChordLength(final Circle circle) {
        final double chordDistance = chordDistance(circle);
        return sqrt(radioPow - pow(chordDistance, 2));
    }

    public double chordMidPointX(final Circle circle) {
        final double distance = getDistance(circle);
        final double xDistance = getXDistance(circle);
        final double chordDistance = chordDistance(circle);
        return point.getX() + (chordDistance * xDistance) / abs(distance);
    }

    public double chordMidPointY(final Circle circle) {
        final double distance = getDistance(circle);
        final double yDistance = getYDistance(circle);
        final double chordDistance = chordDistance(circle);
        return point.getY() + (chordDistance * yDistance) / abs(distance);
    }
    
    public double xVector(final Circle circle) {
        final double distance = getDistance(circle);
        final double yDistance = getYDistance(circle);
        final double halfChordLength = halfChordLength(circle);
        return (halfChordLength * yDistance) / abs(distance);
    }

    public double yVector(final Circle circle) {
        final double distance = getDistance(circle);
        final double xDistance = getXDistance(circle);
        final double halfChordLength = halfChordLength(circle);
        return (halfChordLength * xDistance) / abs(distance);
    }

    public boolean intersects(final Locable locable) {
        final double distance = getDistance(locable);
        return Math.round(distance) == Math.round(radio);
    }
}
