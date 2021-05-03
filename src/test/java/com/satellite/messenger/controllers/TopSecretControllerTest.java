package com.satellite.messenger.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.satellite.messenger.pojo.api.TopSecretReqItemTO;
import com.satellite.messenger.pojo.api.TopSecretReqTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.services.TopSecretService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;
import static org.testng.collections.Lists.newArrayList;

@Test
public class TopSecretControllerTest extends AbstractControllerTest {

    @InjectMocks
    private TopSecretController controller;

    @Mock
    private TopSecretService service;

    @Override
    protected Object getTarget() {
        return controller;
    }

    public void topSecret() {
        final TopSecretReqTO request = EASY_RANDOM.nextObject(TopSecretReqTO.class);
        final TopSecretReqItemTO item = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        request.setSatellites(newArrayList(item, item, item));
        final TopSecretResTO expectedResponse = EASY_RANDOM.nextObject(TopSecretResTO.class);

        when(service.decodeResponse(eq(request))).thenReturn(expectedResponse);
        final TopSecretResTO response = perform(post("/topsecret"), request, new TypeReference<TopSecretResTO>() {
        }, status().isOk());

        verify(service).decodeResponse(eq(request));
        assertEquals(response, expectedResponse);
    }

    @DataProvider
    private Object[][] badItemSizesProvider() {
        final TopSecretReqItemTO item = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        return new Object[][] {
            { newArrayList(item, item) },
            { newArrayList(item, item, item, item) }
        };
    }

    @Test(dataProvider = "badItemSizesProvider")
    public void topSecretBadItemSizes(final List<TopSecretReqItemTO> items) {
        final TopSecretReqTO request = EASY_RANDOM.nextObject(TopSecretReqTO.class);
        request.setSatellites(items);

        perform(post("/topsecret"), request, status().is4xxClientError());
    }

}
