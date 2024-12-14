package com.satellite.messenger.app.utils;

import com.satellite.messenger.app.dto.CircleDTO;
import com.satellite.messenger.app.dto.Locable;
import com.satellite.messenger.app.dto.PointDTO;
import com.satellite.messenger.app.exceptions.location.NotIntersectionException;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.pow;

public class LocationUtils {

    public static final int DEFAULT_ROUND = 2;

    private LocationUtils() {
    }

    private static class Constants {
        public static final double AX = -500;
        public static final double AY = -200;
        public static final double BX = 100;
        public static final double BY = -100;
        public static final double CX = 170;
        public static final double CY = -300;
    }

    private static Set<Locable> getCircleIntersections(
            final CircleDTO aCircle,
            final CircleDTO bCircle
    ) {
        aCircle.checkCoincidentCircle(bCircle);
        aCircle.checkNoIntersection(bCircle);
        aCircle.checkWithinCircle(bCircle);

        final double chordMidPointX = aCircle.chordMidPointX(bCircle);
        final double chordMidPointY = aCircle.chordMidPointY(bCircle);
        final double xVector = aCircle.xVector(bCircle);
        final double yVector = aCircle.yVector(bCircle);
        final double iX = chordMidPointX + xVector;
        final double iY = chordMidPointY - yVector;
        final double jX = chordMidPointX - xVector;
        final double jY = chordMidPointY + yVector;

        final Set<Locable> results = new HashSet<>();
        results.add(new PointDTO(iX, iY));
        results.add(new PointDTO(jX, jY));
        return results;
    }

    /**
     * Returns one point where three circles are intersected.
     * The solution is taken from the following <a href="http://paulbourke.net/geometry/circlesphere/">documentation</a>.
     */
    public static Locable intersectThreeCircles(
            final CircleDTO aCircle,
            final CircleDTO bCircle,
            final CircleDTO cCircle
    ) {
        return intersectThreeCircles(aCircle, bCircle, cCircle, DEFAULT_ROUND);
    }

    public static Locable intersectThreeCircles(
            final CircleDTO aCircle,
            final CircleDTO bCircle,
            final CircleDTO cCircle,
            final Integer round
    ) {
        final Set<Locable> possibleResults = getCircleIntersections(aCircle, bCircle);
        Locable result = possibleResults.stream()
                .filter(cCircle::intersects)
                .findAny()
                .orElseThrow(NotIntersectionException::new);
        result.round(round);
        return result;
    }

    public static Locable intersectThreeCircles (
            final double distanceA,
            final double distanceB,
            final double distanceC
    ) {
        return intersectThreeCircles(
                new CircleDTO(Constants.AX, Constants.AY, distanceA),
                new CircleDTO(Constants.BX, Constants.BY, distanceB),
                new CircleDTO(Constants.CX, Constants.CY, distanceC)
        );
    }
}
