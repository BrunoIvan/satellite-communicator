package com.satellite.messenger.app.utils;

import com.satellite.messenger.app.dto.Circle;
import com.satellite.messenger.app.dto.Locable;
import com.satellite.messenger.app.dto.Point;
import com.satellite.messenger.app.dto.Roundable;
import com.satellite.messenger.app.exceptions.location.NotIntersectionException;

import java.util.HashSet;
import java.util.Set;

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
            final Circle aCircle,
            final Circle bCircle
    ) {
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
        results.add(new Point(iX, iY));
        results.add(new Point(jX, jY));
        return results;
    }

    public static Locable intersectThreeCircles(
            final Circle aCircle,
            final Circle bCircle,
            final Circle cCircle,
            final Integer round
    ) {
        aCircle.checkCoincidentCircle(bCircle);
        aCircle.checkCoincidentCircle(cCircle);

        final Set<Locable> possibleResults = getCircleIntersections(aCircle, bCircle);
        Roundable result = possibleResults.stream()
                .filter(cCircle::intersects)
                .findAny()
                .map(Roundable. class::cast)
                .orElseThrow(NotIntersectionException::new);
        result.round(round);
        return result;
    }

    /**
     * Returns one point where three circles are intersected.
     * The solution is taken from the following <a href="http://paulbourke.net/geometry/circlesphere/">documentation</a>.
     */
    public static Locable intersectThreeCircles(
            final Circle aCircle,
            final Circle bCircle,
            final Circle cCircle
    ) {
        return intersectThreeCircles(aCircle, bCircle, cCircle, DEFAULT_ROUND);
    }

    public static Locable intersectThreeCircles (
            final double distanceA,
            final double distanceB,
            final double distanceC
    ) {
        return intersectThreeCircles(
                new Circle(Constants.AX, Constants.AY, distanceA),
                new Circle(Constants.BX, Constants.BY, distanceB),
                new Circle(Constants.CX, Constants.CY, distanceC)
        );
    }
}
