package com.muruma.adapter.jpa;

import com.muruma.adapter.jpa.exception.PhoneNotFoundException;
import com.muruma.adapter.jpa.exception.PhoneNumberException;
import com.muruma.adapter.jpa.exception.UserNotFoundException;
import com.muruma.adapter.jpa.model.PhoneEntity;
import com.muruma.adapter.jpa.model.UserEntity;
import com.muruma.adapter.jpa.repository.PhoneRepository;
import com.muruma.adapter.jpa.repository.UserRepository;
import com.muruma.application.port.out.PhoneJPARepository;
import com.muruma.config.ErrorCode;
import com.muruma.domain.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class PhoneAdapter implements PhoneJPARepository {

    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;


    public PhoneAdapter(PhoneRepository phoneRepository, UserRepository userRepository) {
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Phone createPhone(UUID userId, Phone phone) {
        var user = getUserEntity(userId);
        if (phoneRepository.existsByNumber(phone.getNumber()))
            throw new PhoneNumberException(ErrorCode.PHONE_ALREADY_EXISTS);

        PhoneEntity result = phoneRepository.save(PhoneEntity.ofWithUser(user, phone));
        return result.toDomain();
    }

    @Override
    public Phone getPhone(UUID userId, UUID id) {
        var user = getUserEntity(userId);
        PhoneEntity result = getPhoneEntity(phoneRepository.findByIdAndUser(id,user));
        return result.toDomain();
    }

    private UserEntity getUserEntity(UUID id) {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty())
            throw new UserNotFoundException(ErrorCode.CLIENT_NOT_FOUND);
        return result.get();
    }

    private PhoneEntity getPhoneEntity(Optional<PhoneEntity> phoneRepository) {
        Optional<PhoneEntity> result = phoneRepository;
        if (result.isEmpty())
            throw new PhoneNotFoundException(ErrorCode.PHONE_NOT_FOUND);
        return result.get();
    }

    @Override
    public Phone updatePhone(UUID userId, UUID id, Phone phone) {
        var user = getUserEntity(userId);
        PhoneEntity phoneEntity = getPhoneEntity(phoneRepository.findByIdAndUser(id,user));

        if (!Objects.isNull(phone.getNumber())) phoneEntity.setNumber(phone.getNumber());
        if (!Objects.isNull(phone.getCountryCode())) phoneEntity.setCountryCode(phone.getCountryCode());
        if (!Objects.isNull(phone.getCityCode())) phoneEntity.setCityCode(phone.getCityCode());
        phoneEntity = phoneRepository.save(phoneEntity);
        return phoneEntity.toDomain();
    }

    @Override
    public void deletePhone(UUID userId, UUID id) {
        var user = getUserEntity(userId);
        PhoneEntity phoneEntity = getPhoneEntity(phoneRepository.findByIdAndUser(id,user));
        phoneRepository.delete(phoneEntity);
    }

    @Override
    public List<Phone> getPhones(UUID userId) {
        var user = getUserEntity(userId);
        List<PhoneEntity> results = phoneRepository.findByUser(user);
        return results.stream().map(PhoneEntity::toDomain).collect(Collectors.toList());
    }

}
