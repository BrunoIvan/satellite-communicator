package com.satellite.messenger.controllers;

import com.satellite.messenger.pojo.api.TopSecretReqItemTO;
import com.satellite.messenger.pojo.api.TopSecretReqTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.services.TopSecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/topsecret_split")
@Slf4j
public class TopSecretSplitController {

    @Autowired
    private TopSecretService topSecretService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody @Valid final TopSecretReqItemTO request) {
        log.info("Incoming request for add an an item to decode a request '{}'", request);
        topSecretService.addItem(request);
        log.info("Incoming request for add an an item was Ok");
    }

}
