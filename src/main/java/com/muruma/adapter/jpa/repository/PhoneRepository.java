package com.muruma.adapter.jpa.repository;

import com.muruma.adapter.jpa.model.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<PhoneEntity, UUID> {
}
