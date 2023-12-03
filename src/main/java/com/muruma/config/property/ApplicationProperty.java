package com.muruma.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
@ConfigurationProperties(prefix = "muruma")
public class ApplicationProperty {
    @NotNull
    private String passwordRegex;
}
