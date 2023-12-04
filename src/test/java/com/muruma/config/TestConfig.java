package com.muruma.config;

import com.muruma.config.property.ApplicationProperty;
import com.muruma.config.property.EncryptProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public ApplicationProperty getApplicationProperty(){
        return new ApplicationProperty();
    }

    @Bean
    public EncryptProperty getEncryptProperty(){
        return new EncryptProperty();
    }

}
