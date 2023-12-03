package com.muruma.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@With
public class User {
    UUID id;
    String name;
    String email;
    String password;
    Boolean isActive;
    LocalDateTime created;
    LocalDateTime modified;
    LocalDateTime lastLogin;
    List<Phone> phones;
    String token;
}
