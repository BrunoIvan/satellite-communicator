package com.satellite.messenger.utils;

import com.satellite.messenger.utils.exceptions.location.CircleWithinException;
import com.satellite.messenger.utils.exceptions.location.EqualCircleException;
import com.satellite.messenger.utils.exceptions.location.LocationException;
import com.satellite.messenger.utils.exceptions.location.NotIntersectionException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

public class LocationUtils {

    protected LocationUtils() {
    }

    private static double getDistance(final double[] pointA, final double[] pointB) {
        final double xB = pointB[0];
        final double yB = pointB[1];
        final double xA = pointA[0];
        final double yA = pointA[1];
        return sqrt(pow((xB - xA), 2) + pow((yB - yA), 2));
    }

    private static double round(
            double value
    ) {
        BigDecimal casted = BigDecimal.valueOf(value);
        return casted.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private static void checkNoIntersection(
            final Double distance,
            final Double radiusA,
            final Double radiusB
    ) {
        if (distance > (radiusA + radiusB)) {
            throw new NotIntersectionException();
        }
    }

    private static void checkWithinCircle(
            final Double distance,
            final Double radiusA,
            final Double radiusB
    ) {
        if (distance < (abs(radiusB - radiusA))) {
            throw new CircleWithinException();
        }
    }

    private static void checkCoincidentCircle(
            final Double distance,
            final Double radiusA,
            final Double radiusB
    ) {
        if (distance.equals(0.) && radiusA.equals(radiusB)) {
            throw new EqualCircleException();
        }
    }

    /**
     * Returns two points where two circles are intersected.
     * The solution is taken from the documentation: <a href="http://paulbourke.net/geometry/circlesphere/">...</a>
     */
    private static double[] intersectTwoCircles(
            final double[] aCircle,
            final double[] bCircle
    ) {
        final double aRadius = aCircle[2];
        final double bRadius = bCircle[2];
        final double xA = aCircle[0];
        final double yA = aCircle[1];
        final double xB = bCircle[0];
        final double yB = bCircle[1];
        final double xDistance = xB - xA;
        final double yDistance = yB - yA;
        final double distance = getDistance(new double[]{xA, yA}, new double[]{xB, yB});

        checkCoincidentCircle(distance, aRadius, bRadius);
        checkNoIntersection(distance, aRadius, bRadius);
        checkWithinCircle(distance, aRadius, bRadius);

        final double chorddistance = (pow(aRadius, 2) - pow(bRadius, 2) + pow(distance, 2)) / (abs(distance) * 2);

        // distance from 1st circle's centre to the chord between intersects
        final double halfchordlength = sqrt(pow(aRadius, 2) - pow(chorddistance, 2));

        final double chordmidpointx = xA + (chorddistance * xDistance) / abs(distance);
        final double chordmidpointy = yA + (chorddistance * yDistance) / abs(distance);
        final double vX = (halfchordlength * yDistance) / abs(distance);
        final double vY = (halfchordlength * xDistance) / abs(distance);

        final double xIPoint = chordmidpointx + vX;
        final double yIPoint = chordmidpointy - vY;
        final double xJPoint = chordmidpointx - vX;
        final double yJPoint = chordmidpointy + vY;

        return new double[]{xIPoint, yIPoint, xJPoint, yJPoint};
    }

    private static boolean provePointIntersect(
            final double[] point,
            final double[] circle
    ) {
        final double distance = getDistance(point, circle);
        return round(distance) == round(circle[2]);
    }

    /**
     * Returns one point where three circles are intersected.
     */
    public static double[] intersectThreeCircles(
            final double[] aCircle,
            final double[] bCircle,
            final double[] cCircle
    ) {
        final double[] possibleResults = intersectTwoCircles(aCircle, bCircle);
        final double firstX = round(possibleResults[0]);
        final double firstY = round(possibleResults[1]);
        final double secondX = round(possibleResults[2]);
        final double secondY = round(possibleResults[3]);
        double[] first = {firstX, firstY};
        double[] second = {secondX, secondY};
        if (provePointIntersect(first, cCircle)) {
            return first;
        } else if (provePointIntersect(second, cCircle)) {
            return second;
        } else {
            throw new NotIntersectionException();
        }
    }

    public static double[] intersectThreeCircles (
            final double distanceA,
            final double distanceB,
            final double distanceC
    ) {
        return intersectThreeCircles(
                new double[]{Constants.AX, Constants.AY, distanceA},
                new double[]{Constants.BX, Constants.BY, distanceB},
                new double[]{Constants.CX, Constants.CY, distanceC}
        );
    }
}
