package com.muruma.faker;

import com.muruma.adapter.jpa.model.UserEntity;
import com.muruma.domain.Phone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserEntityMock {
    private static String STRING_VALUE = "Pedro Perrez";
    public static UserEntity build(UUID id) {
        var result = new UserEntity();
        result.setId(
            Objects.isNull(id) ? UUID.randomUUID() : id
        );
        result.setName(STRING_VALUE);
        result.setEmail(STRING_VALUE);
        result.setPassword(STRING_VALUE);
        result.setIsActive(true);
        result.setCreated(LocalDateTime.now());
        result.setModified(LocalDateTime.now());
        result.setLastLogin(LocalDateTime.now());
        result.setToken(STRING_VALUE);

        return result;
    }
}
