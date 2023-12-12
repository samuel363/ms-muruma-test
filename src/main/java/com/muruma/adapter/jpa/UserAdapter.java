package com.muruma.adapter.jpa;

import com.muruma.adapter.jpa.exception.UserEmailException;
import com.muruma.adapter.jpa.exception.UserNotFoundException;
import com.muruma.adapter.jpa.model.UserEntity;
import com.muruma.adapter.jpa.repository.UserRepository;
import com.muruma.application.port.out.UserJPARepository;
import com.muruma.config.ErrorCode;
import com.muruma.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserAdapter implements UserJPARepository {

    private final UserRepository userRepository;

    public UserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserEmailException(ErrorCode.EMAIL_ALREADY_EXISTS);

        UserEntity result = userRepository.save(UserEntity.of(user));
        return result.toDomain();
    }

    @Override
    public boolean validateUser(User user) {
        return userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @Override
    public User getUser(UUID id) {
        UserEntity result = getUserEntity(userRepository.findById(id));
        return result.toDomain();
    }

    private UserEntity getUserEntity(Optional<UserEntity> userRepository) {
        Optional<UserEntity> result = userRepository;
        if (result.isEmpty())
            throw new UserNotFoundException(ErrorCode.CLIENT_NOT_FOUND);
        return result.get();
    }

    @Override
    public User updateUser(UUID id, User user) {
        UserEntity userEntity = getUserEntity(userRepository.findById(id));

        if (!Objects.isNull(user.getEmail())) userEntity.setEmail(user.getEmail());
        if (!Objects.isNull(user.getPassword())) userEntity.setPassword(user.getPassword());
        if (!Objects.isNull(user.getName())) userEntity.setName(user.getName());
        if (!Objects.isNull(user.getIsActive())) userEntity.setIsActive(user.getIsActive());
        userEntity.setModified(LocalDateTime.now());
        userEntity = userRepository.save(userEntity);
        return userEntity.toDomain();
    }

    @Override
    public User updateLoginUser(User user) {
        UserEntity result = getUserEntity(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()));
        result.setLastLogin(LocalDateTime.now());
        result = userRepository.save(result);
        return result.toDomain();
    }

    @Override
    public User updateUserToken(User user, String token) {
        UserEntity result = getUserEntity(userRepository.findById(user.getId()));
        result.setToken(token);
        result = userRepository.save(result);
        return result.toDomain();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserActive(UUID id, Boolean status) {
        UserEntity result = getUserEntity(userRepository.findById(id));
        result.setIsActive(status);
        userRepository.save(result);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll().stream()
                .map(UserEntity::toDomain)
                .collect(Collectors.toList());
    }

}
