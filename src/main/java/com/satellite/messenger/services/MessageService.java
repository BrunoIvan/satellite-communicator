package com.satellite.messenger.services;

import com.satellite.messenger.dto.DecodeMessageReqDTO;
import com.satellite.messenger.dto.DecodeMessageResDTO;
import com.satellite.messenger.dto.DecodeMessagePosDTO;
import com.satellite.messenger.utils.exceptions.location.LocationException;
import com.satellite.messenger.utils.exceptions.message.MessageException;

import java.util.List;

import static com.satellite.messenger.utils.LocationUtils.intersectThreeCircles;
import static com.satellite.messenger.utils.MessageUtils.getMessage;

public class MessageService {

    private MessageService() {
    }

    public static DecodeMessageResDTO decodeResponse(
            final DecodeMessageReqDTO request
    ) {
        return new DecodeMessageResDTO(intersect(request), decode(request));
    }

    public static DecodeMessagePosDTO intersect(
            final DecodeMessageReqDTO request
    ) {
        double[] result = intersectThreeCircles(
                request.getSatellites().get(0).getDistance(),
                request.getSatellites().get(1).getDistance(),
                request.getSatellites().get(2).getDistance()
        );
        return new DecodeMessagePosDTO(result);
    }

    public static String decode(final DecodeMessageReqDTO request) {
        final List<String> message0 = request.getSatellites().get(0).getMessage();
        final List<String> message1 = request.getSatellites().get(1).getMessage();
        final List<String> message2 = request.getSatellites().get(2).getMessage();
        return getMessage(message0, message1, message2);
    }

}
