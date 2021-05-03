package com.satellite.messenger.controllers;

import com.satellite.messenger.pojo.api.TopSecretReqItemTO;
import com.satellite.messenger.services.TopSecretService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
public class TopSecretSplitControllerTest extends AbstractControllerTest {

    @InjectMocks
    private TopSecretSplitController controller;

    @Mock
    private TopSecretService service;

    @Override
    protected Object getTarget() {
        return controller;
    }

    public void addItem() {
        final TopSecretReqItemTO request = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        perform(post("/topsecret_split"), request, status().is(201));
        verify(service).addItem(eq(request));
    }

}