package com.satellite.messenger.controllers;

import com.satellite.messenger.controllers.exceptions.BadRequestException;
import com.satellite.messenger.dto.DecodeMessageReqDTO;
import com.satellite.messenger.dto.DecodeMessageResDTO;
import com.satellite.messenger.services.MessageService;
import com.satellite.messenger.utils.exceptions.location.LocationException;
import com.satellite.messenger.utils.exceptions.message.MessageException;
import jakarta.enterprise.context.ApplicationScoped;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "messages")
@ApplicationScoped
public class MessageController {

    @PostMapping(path = "decode", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public DecodeMessageResDTO decode(@RequestBody final DecodeMessageReqDTO request) {
        try {
            return MessageService.decodeResponse(request);
        } catch (LocationException | MessageException e) {
            throw new BadRequestException(e);
        }
    }

}
