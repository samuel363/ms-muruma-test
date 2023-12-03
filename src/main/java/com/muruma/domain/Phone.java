package com.muruma.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Phone {
    UUID id;
    Integer number;
    String cityCode;
    String countryCode;
}
