package com.muruma.adapter.jpa.model;

import com.muruma.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "`user`")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<PhoneEntity> phones;
    private String token;

    public UserEntity() {
    }

    public static UserEntity of(User user) {
        List<PhoneEntity> phones = new ArrayList<>();

        user.getPhones().forEach(phone -> {
            phones.add(
                PhoneEntity.of(phone)
            );
        });

        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setIsActive(Boolean.TRUE);
        userEntity.setCreated(LocalDateTime.now());
        userEntity.setModified(LocalDateTime.now());
        userEntity.setLastLogin(LocalDateTime.now());
        userEntity.setPhones(phones);
        return userEntity;
    }

    public User toDomain() {
        return User.builder()
                .id(id)
                .isActive(isActive)
                .created(created)
                .modified(modified)
                .lastLogin(lastLogin)
                .token(token)
                .build();
    }
}
