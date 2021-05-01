package com.satellite.messenger.utils;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.util.Pair;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

@Test
public class LocationUtilsTest {

    public void getDistanceOk() {
        final Point pointA = new Point(-500, -200);
        final Point pointB = new Point(100, -100);
        final Double expectedResult = 608.276253029822;
        final Double result = LocationUtils.getDistance(pointA, pointB);
        assertEquals(result, expectedResult);
    }

    public void roundOk() {
        assertEquals(LocationUtils.round(23.4567, 2), 23.46);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid quantity of places")
    public void roundInvalidPlaces() {
        LocationUtils.round(23.4567, -2);
    }

    public void intersectTwoCirclesOk() {
        final Circle circleA = new Circle(new Point(-500, -200), new Distance(600));
        final Circle circleB = new Circle(new Point(100, -100), new Distance(115.5));
        final Pair<Point, Point> result = LocationUtils.intersectTwoCircles(circleA, circleB);
        assertEquals(LocationUtils.round(result.getFirst().getX(), 6), 62.351183);
        assertEquals(LocationUtils.round(result.getFirst().getY(), 6), 9.191651);
        assertEquals(LocationUtils.round(result.getSecond().getX(), 6), 99.799763);
        assertEquals(LocationUtils.round(result.getSecond().getY(), 6), -215.499826);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "One circle is within the other")
    public void intersectOneCircleWithin() {
        final Circle circleA = new Circle(new Point(-500, -200), new Distance(600));
        final Circle circleB = new Circle(new Point(-240, -450), new Distance(115.5));
        LocationUtils.intersectTwoCircles(circleA, circleB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "The circles do not intersect")
    public void intersectSeparatedCircumferences() {
        final Circle circleA = new Circle(new Point(-500, -200), new Distance(200));
        final Circle circleB = new Circle(new Point(-240, -450), new Distance(115.5));
        LocationUtils.intersectTwoCircles(circleA, circleB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "The circles are equal")
    public void intersectIdenticalCircles() {
        final Circle circleA = new Circle(new Point(-500, -200), new Distance(200));
        final Circle circleB = new Circle(new Point(-500, -200), new Distance(200));
        assertThrows(IllegalArgumentException.class, () -> LocationUtils.intersectTwoCircles(circleA, circleB));
        LocationUtils.intersectTwoCircles(circleA, circleB);
    }

    public void intersectThreeCirclesOk() {
        final Circle circleA = new Circle(new Point(-500, -200), new Distance(600));
        final Circle circleB = new Circle(new Point(100, -100), new Distance(115.5));
        final Circle circleC = new Circle(new Point(170, -300), new Distance(109.856054));
        final Point result = LocationUtils.intersectThreeCircles(circleA, circleB, circleC);
        assertEquals(LocationUtils.round(result.getX(), 6), 99.799763);
        assertEquals(LocationUtils.round(result.getY(), 6), -215.499826);
    }

    public void intersectThreeCirclesNoIntersected() {
        final Circle circleA = new Circle(new Point(-500, -200), new Distance(600));
        final Circle circleB = new Circle(new Point(100, -100), new Distance(115.5));
        final Circle circleC = new Circle(new Point(170, -300), new Distance(250));
        assertThrows(IllegalArgumentException.class, () -> LocationUtils.intersectThreeCircles(circleA, circleB, circleC));
    }

}
