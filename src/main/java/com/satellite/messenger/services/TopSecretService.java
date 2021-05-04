package com.satellite.messenger.services;

import com.satellite.messenger.persistence.stores.DistanceStore;
import com.satellite.messenger.persistence.stores.MessageStore;
import com.satellite.messenger.persistence.stores.SatelliteStore;
import com.satellite.messenger.pojo.api.TopSecretReqItemTO;
import com.satellite.messenger.pojo.api.TopSecretReqTO;
import com.satellite.messenger.pojo.api.TopSecretResPosTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.pojo.entities.DistanceTO;
import com.satellite.messenger.pojo.entities.MessageTO;
import com.satellite.messenger.pojo.entities.SatelliteTO;
import com.satellite.messenger.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.satellite.messenger.pojo.enums.MessageStatusType.*;
import static com.satellite.messenger.utils.LocationUtils.intersectThreeCircles;

@Service
@Slf4j
public class TopSecretService {

    @Autowired
    private SatelliteStore satelliteStore;

    @Autowired
    private MessageStore messageStore;

    @Autowired
    private DistanceStore distanceStore;

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

    public void addItem(final TopSecretReqItemTO request) {
        log.info("Getting satellite with name '{}'", request.getName());
        final SatelliteTO satellite = strictGetSatellite(request);
        log.info("Satellite with name '{}' has id '{}'", request.getName(), satellite.getId());

        log.info("Getting uncompleted messaged");
        final MessageTO message = getOrCreateMessage();
        log.info("Uncompleted messaged has id '{}'", satellite.getId());

        checkAlreadyUploaded(request, message);
        checkAlreadyThree(message);

        log.info("Saving new distance");
        final DistanceTO distance = distanceStore.save(new DistanceTO(request.getDistance(), request.getMessage(), satellite, message));
        log.info("New distance has id '{}'", distance.getId());

        setUnsolved(message);
    }

    private MessageTO getOrCreateMessage() {
        return messageStore.findUncompleted().stream().findFirst()
                .orElseGet(() -> messageStore.save(MessageTO.builder().status(UNCOMPLETED).build()));
    }

    private SatelliteTO strictGetSatellite(TopSecretReqItemTO request) {
        return satelliteStore.getByName(request.getName())
                .orElseThrow(() -> new IllegalArgumentException("Satellite was not found"));
    }

    private void setUnsolved(MessageTO message) {
        if(message.getDistances().size() == 2) {
            message.setStatus(UNSOLVED);
            messageStore.save(message);
        }
    }

    private void checkAlreadyThree(final MessageTO message) {
        if (message.getDistances().size() == 3) {
            throw new IllegalArgumentException("Distances are already three");
        }
    }

    private void checkLessThanThree(final MessageTO message) {
        if (message.getDistances().size() < 3) {
            throw new IllegalArgumentException("No enough distances to decode a message");
        }
    }

    private void checkAlreadyUploaded(final TopSecretReqItemTO request, final MessageTO message) {
        if (message.getDistances().stream().anyMatch(it -> it.getSatellite().getName().equals(request.getName()))) {
            throw new IllegalArgumentException("Distance is already uploaded");
        }
    }

    public TopSecretResTO decodeFromStore() {
        log.info("Finding message with unsolved status type");
        final MessageTO unsolved = messageStore.findUnsolved()
                .stream().findFirst().orElseThrow(() -> new IllegalArgumentException("No distances to decode a message"));
        log.info("Unsolved message has id '{}'", unsolved.getId());

        checkLessThanThree(unsolved);

        log.info("Getting all satellites");
        final List<SatelliteTO> satellites = satelliteStore.getAll();
        log.info("All satellites gathered");

        return doDecodeFromStore(unsolved, satellites);
    }

    private TopSecretResTO doDecodeFromStore(final MessageTO unsolved, final List<SatelliteTO> satellites) {
        try {
            final Point position = getPosition(unsolved, satellites);
            final String message = getMessage(unsolved);

            unsolved.setStatus(SOLVED_OK);
            unsolved.setValue(message);
            return new TopSecretResTO(new TopSecretResPosTO(position.getX(), position.getY()), message);
        } catch (final IllegalArgumentException e) {
            unsolved.setStatus(SOLVED_FAILED);
            unsolved.setFailedMessage(e.getMessage());
            throw e;
        } finally {
            log.info("Saving message with it '{}'. Status '{}'", unsolved.getId(), unsolved.getStatus());
            messageStore.save(unsolved);
        }
    }

    private String getMessage(final MessageTO unsolved) {
        final List<String> message0 = unsolved.getDistances().get(0).getWords();
        final List<String> message1 = unsolved.getDistances().get(1).getWords();
        final List<String> message2 = unsolved.getDistances().get(2).getWords();
        return MessageUtils.getMessage(message0, message1, message2);
    }

    private Point getPosition(final MessageTO unsolved, final List<SatelliteTO> satellites) {
        final List<Circle> circles = unsolved.getDistances().stream()
                .map(distance -> {
                    final SatelliteTO sat = satellites.stream().filter(it -> it.getName().equals(distance.getSatellite().getName())).findFirst()
                            .orElseThrow(() -> new IllegalStateException(String.format("Could not found satellite '%s'", distance.getSatellite().getName())));

                    return new Circle(new Point(sat.getXPosition(), sat.getYPosition()), new Distance(distance.getValue()));
                }).collect(Collectors.toList());

        return intersectThreeCircles(circles.get(0), circles.get(1), circles.get(2));
    }
}
