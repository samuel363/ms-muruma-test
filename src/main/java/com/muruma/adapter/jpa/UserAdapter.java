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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty())
            throw new UserNotFoundException(ErrorCode.CLIENT_NOT_FOUND);
        return result.get().toDomain();
    }

    @Override
    public User updateUser(UUID id, User user) {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty())
            throw new UserNotFoundException(ErrorCode.CLIENT_NOT_FOUND);

        UserEntity userEntity = result.get();
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
        UserEntity result = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        result.setLastLogin(LocalDateTime.now());
        result = userRepository.save(result);
        return result.toDomain();
    }

    @Override
    public User updateUserToken(User user, String token) {
        Optional<UserEntity> optional = userRepository.findById(user.getId());
        if (optional.isEmpty())
            throw new UserNotFoundException(ErrorCode.CLIENT_NOT_FOUND);
        UserEntity result = optional.get();
        result.setToken(token);
        result = userRepository.save(result);
        return result.toDomain();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
