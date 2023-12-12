package com.muruma.application.port.in;

import com.muruma.domain.Phone;

import java.util.UUID;

public interface PhoneCommand {
    Phone createPhone(UUID userId, Phone phone);

    Phone updatePhone(UUID userId, UUID id, Phone domain, String token);

    void deletePhone(UUID userId, UUID id);
}
