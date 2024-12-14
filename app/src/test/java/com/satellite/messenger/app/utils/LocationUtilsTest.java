package com.satellite.messenger.app.utils;

import com.satellite.messenger.app.dto.CircleDTO;
import com.satellite.messenger.app.dto.Locable;
import com.satellite.messenger.app.exceptions.location.CircleWithinException;
import com.satellite.messenger.app.exceptions.location.EqualCircleException;
import com.satellite.messenger.app.exceptions.location.NotIntersectionException;

public class LocationUtilsTest {

    public static void main(String[] args) {
        when_intersectThreeCircles_then_ResultIsOk();
        when_intersectThreeCircles_then_NotIntersectionException();
        when_intersectThreeCircles_then_CircleWithinException();
        when_intersectThreeCircles_then_EqualCircleException();
        when_intersectThreeCircles_and_TwoCirclesDoNotIntersects_then_NotIntersectionException();
    }

    public static void when_intersectThreeCircles_then_ResultIsOk() {
        final CircleDTO circleA = new CircleDTO((double) -500, (double) -200, (double) 600);
        final CircleDTO circleB = new CircleDTO((double) 100, (double) -100, 115.5);
        final CircleDTO circleC = new CircleDTO((double) 170, (double) -300, 109.856054);
        final Locable result = LocationUtils.intersectThreeCircles(circleA, circleB, circleC);

        final double expectedX = 99.80;
        if (Double.compare(result.getX(), expectedX) != 0) {
            throw new AssertionError("Expected X: " + expectedX + ", but got: " + result.getX());
        }

        final double expectedY = -215.50;
        if (Double.compare(result.getY(), expectedY) != 0) {
            throw new AssertionError("Expected Y: " + expectedY + ", but got: " + result.getY());
        }

        System.out.println("Result was calculated as expected.");
    }

    public static void when_intersectThreeCircles_then_NotIntersectionException() {
        final CircleDTO circleA = new CircleDTO((double) -500, (double) -200, (double) 600);
        final CircleDTO circleB = new CircleDTO((double) 100, (double) -100, 115.5);
        final CircleDTO circleC = new CircleDTO((double) 170, (double) -300, (double) 250);
        try {
            LocationUtils.intersectThreeCircles(circleA, circleB, circleC);
            throw new AssertionError("Expected NotIntersectionException to be thrown, but it was not.");
        } catch (NotIntersectionException ignored) {
            System.out.println("NotIntersectionException was thrown as expected.");
        }
    }

    public static void when_intersectThreeCircles_then_CircleWithinException() {
        final CircleDTO circleA = new CircleDTO((double) -500, (double) -200, (double) 600);
        final CircleDTO circleB = new CircleDTO((double) -240, (double) -450, 115.5);
        final CircleDTO circleC = new CircleDTO((double) 170, (double) -300, (double) 250);
        try {
            LocationUtils.intersectThreeCircles(circleA, circleB, circleC);
            throw new AssertionError("Expected CircleWithinException to be thrown, but it was not.");
        } catch (CircleWithinException ignored) {
            System.out.println("CircleWithinException was thrown as expected.");
        }
    }

    public static void when_intersectThreeCircles_then_EqualCircleException() {
        final CircleDTO circleA = new CircleDTO((double) -500, (double) -200, (double) 600);
        final CircleDTO circleB = new CircleDTO((double) -500, (double) -200, (double) 600);
        final CircleDTO circleC = new CircleDTO((double) 170, (double) -300, (double) 250);
        try {
            LocationUtils.intersectThreeCircles(circleA, circleB, circleC);
            throw new AssertionError("Expected EqualCircleException to be thrown, but it was not.");
        } catch (EqualCircleException ignored) {
            System.out.println("EqualCircleException was thrown as expected.");
        }
    }

    public static void when_intersectThreeCircles_and_TwoCirclesDoNotIntersects_then_NotIntersectionException() {
        final CircleDTO circleA = new CircleDTO((double) -500, (double) -200, (double) 200);
        final CircleDTO circleB = new CircleDTO((double) -240, (double) -450, 115.5);
        final CircleDTO circleC = new CircleDTO((double) 170, (double) -300, (double) 250);
        try {
            LocationUtils.intersectThreeCircles(circleA, circleB, circleC);
            throw new AssertionError("Expected NotIntersectionException to be thrown, but it was not.");
        } catch (NotIntersectionException ignored) {
            System.out.println("NotIntersectionException was thrown as expected.");
        }
    }

}
