package com.satellite.messenger.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.BeforeClass;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public abstract class AbstractControllerTest {

    protected static final EasyRandom EASY_RANDOM = new EasyRandom();

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        openMocks(this);
        this.mockMvc = standaloneSetup(this.getTarget()).build();
        this.objectMapper = new ObjectMapper();
    }

    protected abstract Object getTarget();

    @SneakyThrows
    protected <I> void perform(final MockHttpServletRequestBuilder requestBuilder, final I request, final ResultMatcher status) {
        performRequest(requestBuilder, request, status);
    }

    @SneakyThrows
    protected <I, O> O perform(final MockHttpServletRequestBuilder requestBuilder, final I request, final TypeReference<O> typeReference, final ResultMatcher status) {
        final MvcResult result = performRequest(requestBuilder, request, status);
        return (typeReference != null) ? objectMapper.readValue(result.getResponse().getContentAsString(), typeReference) : null;
    }

    @SneakyThrows
    protected <I> ModelAndView performModelAndView(final MockHttpServletRequestBuilder requestBuilder, final I request, final ResultMatcher status) {
        final MvcResult result = performRequest(requestBuilder, request, status);
        return result.getModelAndView();
    }

    @SneakyThrows
    private <I> MvcResult performRequest(final MockHttpServletRequestBuilder requestBuilder, final I request, final ResultMatcher status) {
        final String json = objectMapper.writeValueAsString(request);

        return this.mockMvc.perform(
                requestBuilder
                        .header("device-brand", "android")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status)
                .andReturn();
    }
}