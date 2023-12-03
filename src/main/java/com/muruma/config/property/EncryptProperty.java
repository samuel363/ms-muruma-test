package com.muruma.config.property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
@ConfigurationProperties(prefix = "encrypt")
public class EncryptProperty {
    @NotNull
    private String aesKey256;
    @NotNull
    private String jwtKey;
    @NotNull
    private Integer jwtTimeout;
}
