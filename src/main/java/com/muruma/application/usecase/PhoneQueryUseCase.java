package com.muruma.application.usecase;

import com.muruma.application.port.in.PhoneQuery;
import com.muruma.application.port.out.PhoneJPARepository;
import com.muruma.domain.Phone;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
@AllArgsConstructor
@Slf4j
public class PhoneQueryUseCase implements PhoneQuery {
    private final PhoneJPARepository phoneJPARepository;

    @Override
    public Phone getPhone(UUID userId, UUID id) {
        return phoneJPARepository.getPhone(userId, id);
    }

    @Override
    public List<Phone> getPhones(UUID userId) {
        return phoneJPARepository.getPhones(userId);
    }
}
