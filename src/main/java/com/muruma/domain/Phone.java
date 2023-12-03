package com.muruma.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Phone {
    private UUID id;
    private Integer number;
    private String cityCode;
    private String countryCode;
}
