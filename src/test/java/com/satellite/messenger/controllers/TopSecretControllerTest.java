package com.satellite.messenger.controllers;

import com.satellite.messenger.pojo.TopSecretReqTO;
import com.satellite.messenger.pojo.TopSecretResTO;
import com.satellite.messenger.services.TopSecretService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(TopSecretController.class)
public class TopSecretControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TopSecretController controller;

    @Mock
    private TopSecretService topSecretService;

    @Test
    public void testDecode() throws Exception {
        TopSecretReqTO request = new TopSecretReqTO();
        // Agrega aquí los valores de prueba del request

        TopSecretResTO response = new TopSecretResTO();
        // Agrega aquí los valores de prueba del response

        when(topSecretService.decodeResponse(any(TopSecretReqTO.class))).thenReturn(response);

        mockMvc.perform(post("/topsecret")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.campo").value("valor"));  // Ajusta los paths y valores según la estructura de tu response.

        verify(topSecretService, times(1)).decodeResponse(any(TopSecretReqTO.class));
    }

    // Método para convertir un objeto en una cadena JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
