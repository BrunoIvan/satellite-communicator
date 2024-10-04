package com.satellite.messenger.services;

import com.satellite.messenger.pojo.TopSecretReqTO;
import com.satellite.messenger.pojo.TopSecretResPosTO;
import com.satellite.messenger.pojo.TopSecretResTO;
import com.satellite.messenger.utils.MessageUtils;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.satellite.messenger.utils.LocationUtils.intersectThreeCircles;

@Service
public class TopSecretService {

    public static TopSecretResTO decodeResponse(final TopSecretReqTO request) {
        final Point position = getPosition(request);
        final String message = getMessage(request);
        return new TopSecretResTO(new TopSecretResPosTO(position.getX(), position.getY()), message);
    }

    private static String getMessage(final TopSecretReqTO request) {
        final List<String> message0 = request.getSatellites().get(0).getMessage();
        final List<String> message1 = request.getSatellites().get(1).getMessage();
        final List<String> message2 = request.getSatellites().get(2).getMessage();
        return MessageUtils.getMessage(message0, message1, message2);
    }

    private static int randomInt() {
        Random random = new Random();
        return random.nextInt(101);
    }

    private static Point randomPoint() {
        return new Point(randomInt(), randomInt());
    }

    private static Circle randomCircle() {
        return new Circle(randomPoint(), randomInt());
    }

    private static Point getPosition(final TopSecretReqTO request) {
        return intersectThreeCircles(randomCircle(), randomCircle(), randomCircle());
    }
}
