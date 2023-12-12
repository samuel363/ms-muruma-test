package com.muruma.adapter.jpa.repository;

import com.muruma.adapter.jpa.model.PhoneEntity;
import com.muruma.adapter.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<PhoneEntity, UUID> {
    boolean existsByNumber(Integer number);
    List<PhoneEntity> findByUser(UserEntity user);
    Optional<PhoneEntity> findByIdAndUser(UUID id, UserEntity user);

}
