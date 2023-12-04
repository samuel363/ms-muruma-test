package com.muruma.adapter.jpa.repository;

import com.muruma.adapter.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

}
