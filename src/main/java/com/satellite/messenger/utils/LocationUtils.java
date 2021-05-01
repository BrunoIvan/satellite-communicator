package com.satellite.messenger.utils;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

public class LocationUtils {

    public static double getDistance(final Point pointA, final Point pointB) {
        final double xB = pointB.getX();
        final double yB = pointB.getY();
        final double xA = pointA.getX();
        final double yA = pointA.getY();
        return sqrt(pow((xB - xA), 2) + pow((yB - yA), 2));
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException("Invalid quantity of places");
        }

        BigDecimal casted = BigDecimal.valueOf(value);
        return casted.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    private static void checkNoIntersection(final Double distance, final Double radiusA, final Double radiusB) {
        if (distance > (radiusA + radiusB)) {
            throw new IllegalArgumentException("The circles do not intersect");
        }
    }

    private static void checkWithinCircle(final Double distance, final Double radiusA, final Double radiusB) {
        if (distance < (abs(radiusB - radiusA))) {
            throw new IllegalArgumentException("One circle is within the other");
        }
    }

    private static void checkCoincidentCircle(final Double distance, final Double radiusA, final Double radiusB) {
        if (distance.equals(0.) && radiusA.equals(radiusB)) {
            throw new IllegalArgumentException("The circles are equal");
        }
    }

    private static Pair<Point, Point> orderByDegrees(final Point iPoint, final Point jPoint, final Circle aCircle) {
        final double xA = aCircle.getCenter().getX();
        final double yA = aCircle.getCenter().getY();
        final double yIPoint = iPoint.getY();
        final double yJPoint = jPoint.getY();
        final double xIPoint = iPoint.getX();
        final double xJPoint = jPoint.getX();
        final double iTheta = toDegrees(atan2(yIPoint - yA, xIPoint - xA));
        final double jTheta = toDegrees(atan2(yJPoint - yA, xJPoint - xA));

        if (jTheta > iTheta) {
            return Pair.of(jPoint, iPoint);
        } else {
            return Pair.of(iPoint, jPoint);
        }
    }

    /**
     * Returns two points where two circles are intersected.
     * The solution is taken from the documentation: http://paulbourke.net/geometry/circlesphere/
     */
    public static Pair<Point, Point> intersectTwoCircles(final Circle aCircle, final Circle bCircle) {
        final double aRadius = aCircle.getRadius().getValue();
        final double bRadius = bCircle.getRadius().getValue();
        final double xA = aCircle.getCenter().getX();
        final double yA = aCircle.getCenter().getY();
        final double xB = bCircle.getCenter().getX();
        final double yB = bCircle.getCenter().getY();
        final double xDistance = xB - xA;
        final double yDistance = yB - yA;
        final double distance = getDistance(aCircle.getCenter(), bCircle.getCenter());

        checkCoincidentCircle(distance, aRadius, bCircle.getRadius().getValue());
        checkNoIntersection(distance, aRadius, bCircle.getRadius().getValue());
        checkWithinCircle(distance, aRadius, bCircle.getRadius().getValue());

        final double chorddistance = (pow(aRadius, 2) - pow(bRadius, 2) + pow(distance, 2)) / (distance * 2);

        // distance from 1st circle's centre to the chord between intersects
        final double halfchordlength = sqrt(pow(aRadius, 2) - pow(chorddistance, 2));
        final double chordmidpointx = xA + (chorddistance * xDistance) / distance;
        final double chordmidpointy = yA + (chorddistance * yDistance) / distance;

        final double xIPoint = chordmidpointx + (halfchordlength * yDistance) / distance;
        final double yIPoint = chordmidpointy - (halfchordlength * xDistance) / distance;
        final Point iPoint = new Point(xIPoint, yIPoint);

        final double xJPoint = chordmidpointx - (halfchordlength * yDistance) / distance;
        final double yJPoint = chordmidpointy + (halfchordlength * xDistance) / distance;
        final Point jPoint = new Point(xJPoint, yJPoint);

        return orderByDegrees(iPoint, jPoint, aCircle);
    }

    private static boolean provePointIntersect(final Point point, final Circle circle) {
        final double distance = getDistance(point, circle.getCenter());
        return round(distance, 6) == round(circle.getRadius().getValue(), 6);
    }

    /**
     * Returns one point where three circles are intersected.
     */
    public static Point intersectThreeCircles(final Circle aCircle, final Circle bCircle, final Circle cCircle) {
        final Pair<Point, Point> possibleResults = intersectTwoCircles(aCircle, bCircle);

        if (provePointIntersect(possibleResults.getFirst(), cCircle)) {
            return possibleResults.getFirst();
        } else if (provePointIntersect(possibleResults.getSecond(), cCircle)) {
            return possibleResults.getSecond();
        } else {
            throw new IllegalArgumentException("Could not be able to find any intersection");
        }
    }
}
