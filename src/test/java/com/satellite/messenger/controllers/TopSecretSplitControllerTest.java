package com.satellite.messenger.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.satellite.messenger.pojo.api.TopSecretReqItemTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.services.TopSecretService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

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


    public void decode() {
        final TopSecretResTO expectedResponse = EASY_RANDOM.nextObject(TopSecretResTO.class);

        when(service.decodeFromStore()).thenReturn(expectedResponse);
        final TopSecretResTO response = perform(get("/topsecret_split"), null, new TypeReference<TopSecretResTO>() {
        }, status().isOk());

        verify(service).decodeFromStore();
        assertEquals(response, expectedResponse);
    }

}