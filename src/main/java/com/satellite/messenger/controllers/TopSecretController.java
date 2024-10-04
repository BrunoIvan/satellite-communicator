package com.satellite.messenger.controllers;

import com.satellite.messenger.pojo.TopSecretReqTO;
import com.satellite.messenger.pojo.TopSecretResTO;
import com.satellite.messenger.services.TopSecretService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/topsecret")
public class TopSecretController {

    private static Logger log = LoggerFactory.getLogger(TopSecretController.class);

    @PostMapping
    public TopSecretResTO decode(@RequestBody @Valid final TopSecretReqTO request) {
        log.info("Incoming request for decode a message with request '{}'", request);
        final TopSecretResTO response = TopSecretService.decodeResponse(request);
        log.info("Incoming request for decode a message with request was OK");
        return response;
    }

}
