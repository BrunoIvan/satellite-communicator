package com.satellite.messenger.services;

import com.satellite.messenger.persistence.stores.SatelliteStore;
import com.satellite.messenger.pojo.api.TopSecretReqTO;
import com.satellite.messenger.pojo.api.TopSecretResPosTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.pojo.entities.SatelliteTO;
import com.satellite.messenger.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.satellite.messenger.utils.LocationUtils.intersectThreeCircles;

@Service
public class TopSecretService {

    @Autowired
    private SatelliteStore satelliteStore;

    public TopSecretResTO decodeResponse(final TopSecretReqTO request) {
        final Point position = getPosition(request);
        final String message = getMessage(request);
        return new TopSecretResTO(new TopSecretResPosTO(position.getX(), position.getY()), message);
    }

    private String getMessage(final TopSecretReqTO request) {
        final List<String> message0 = request.getSatellites().get(0).getMessage();
        final List<String> message1 = request.getSatellites().get(1).getMessage();
        final List<String> message2 = request.getSatellites().get(2).getMessage();
        return MessageUtils.getMessage(message0, message1, message2);
    }

    private Point getPosition(final TopSecretReqTO request) {
        final List<SatelliteTO> satellites = satelliteStore.getAll();
        final List<Circle> circles = request.getSatellites().stream()
                .map(requestSatellite -> {
                    final SatelliteTO sat = satellites.stream().filter(it -> requestSatellite.getName().equals(it.getName())).findFirst()
                            .orElseThrow(() -> new IllegalArgumentException(String.format("Could not found satellite '%s'", requestSatellite.getName())));

                    return new Circle(new Point(sat.getXPosition(), sat.getYPosition()), new Distance(requestSatellite.getDistance()));
                }).collect(Collectors.toList());

        return intersectThreeCircles(circles.get(0), circles.get(1), circles.get(2));
    }

}
