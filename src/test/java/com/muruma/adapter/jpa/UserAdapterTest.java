package com.muruma.adapter.jpa;

import com.muruma.adapter.jpa.repository.UserRepository;
import com.muruma.config.property.ApplicationProperty;
import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Import;

@DisplayName("UserAdapterTest")
@Import(value = {ApplicationProperty.class})
@AutoConfigureWebClient(registerRestTemplate = true)
class UserAdapterTest {

    private UserRepository userRepository;

    @Test
    @Name("When user rest is success call")
    void getExternalRestUserOk() {
        // this.server.expect()
    }

}