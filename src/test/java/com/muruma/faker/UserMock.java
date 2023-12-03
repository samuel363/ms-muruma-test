package com.muruma.faker;

import com.muruma.domain.Phone;
import com.muruma.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class UserMock {
    public static final String STRING_VALUE = "Pedro Perez";

    public static User build(UUID id) {
        return User.builder()
                .id(
                        Objects.isNull(id) ? UUID.randomUUID() : id
                )
                .name(STRING_VALUE)
                .email(STRING_VALUE)
                .password(STRING_VALUE)
                .isActive(true)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token(STRING_VALUE)
                .phones(List.of(
                        Phone.builder()
                                .number(1234567)
                                .cityCode("1")
                                .countryCode("1")
                                .build()
                ))
                .build();
    }

}
