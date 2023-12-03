package com.muruma.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muruma.config.property.ApplicationProperty;
import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;

@DisplayName("UserRestClientAdapterTest")
@Import(value = {ApplicationProperty.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(value = {UserRestClientAdapter.class})
class UserRequestModelClientAdapterTest {

    @Autowired
    private UserRestClientAdapter adapter;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockRestServiceServer server;

    @Test
    @Name("When user rest is success call")
    void getExternalRestUserOk() {
        // this.server.expect()
    }

}