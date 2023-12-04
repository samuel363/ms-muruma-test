package com.muruma.config;

import com.muruma.config.property.EncryptProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    EncryptProperty getEncryptProperty(EncryptProperty encryptProperty){
        return encryptProperty;
    }

}
