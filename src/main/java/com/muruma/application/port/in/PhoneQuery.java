package com.muruma.application.port.in;

import com.muruma.domain.Phone;

import java.util.List;
import java.util.UUID;

public interface PhoneQuery {
    Phone getPhone(UUID userId, UUID id);

    List<Phone> getPhones(UUID userId);
}
