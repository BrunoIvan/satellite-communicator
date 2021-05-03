package com.satellite.messenger.controllers;

import com.satellite.messenger.pojo.api.TopSecretReqTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.services.TopSecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/topsecret")
@Slf4j
public class TopSecretController {

    @Autowired
    private TopSecretService topSecretService;

    @PostMapping
    public TopSecretResTO decode(@RequestBody @Valid final TopSecretReqTO request) {
        log.info("Incoming request for decode a message with request '{}'", request);
        final TopSecretResTO response = topSecretService.decodeResponse(request);
        log.info("Incoming request for decode a message with request was OK");
        return response;
    }

}
