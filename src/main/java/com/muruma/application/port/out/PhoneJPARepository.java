package com.muruma.application.port.out;

import com.muruma.domain.Phone;

import java.util.List;
import java.util.UUID;

public interface PhoneJPARepository {
    Phone createPhone(UUID userId, Phone phone);

    Phone getPhone(UUID userId, UUID id);

    Phone updatePhone(UUID userId, UUID id, Phone phone);

    void deletePhone(UUID userId, UUID id);

    List<Phone> getPhones(UUID userId);
}
