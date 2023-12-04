package com.muruma.adapter.jpa;

import com.muruma.adapter.jpa.repository.UserRepository;
import com.muruma.config.property.ApplicationProperty;
import com.muruma.faker.UserEntityMock;
import com.muruma.faker.UserMock;
import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("UserAdapterTest")
@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @InjectMocks
    private UserAdapter userAdapter;
    @Mock
    private UserRepository userRepository;

    @Test
    @Name("createUser Success")
    void createUserSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        var userEntity = UserEntityMock.build(id);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userEntity);

        var result = userAdapter.createUser(user);

        assertThat(result).isNotNull();
    }

    @Test
    @Name("validateUser Success")
    void validateUserSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        when(userRepository.existsByEmailAndPassword(any(),any())).thenReturn(false);

        var result = userAdapter.validateUser(user);

        assertThat(result).isFalse();
    }

    @Test
    @Name("getUser Success")
    void getUserSuccess() {
        UUID id = UUID.randomUUID();
        var userEntity = Optional.of(UserEntityMock.build(id));
        when(userRepository.findById(any())).thenReturn(userEntity);

        var result = userAdapter.getUser(id);

        assertThat(result).isNotNull();
    }

    @Test
    @Name("updateUser Success")
    void updateUserSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        var userEntity = Optional.of(UserEntityMock.build(id));
        when(userRepository.findById(any())).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity.get());

        var result = userAdapter.updateUser(id,user);

        assertThat(result).isNotNull();
    }

    @Test
    @Name("updateLoginUser Success")
    void updateLoginUserSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        var userEntity = Optional.of(UserEntityMock.build(id));
        when(userRepository.findByEmailAndPassword(any(),any())).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity.get());

        var result = userAdapter.updateLoginUser(user);

        assertThat(result).isNotNull();
    }

    @Test
    @Name("updateUserToken Success")
    void updateUserTokenSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        var userEntity = Optional.of(UserEntityMock.build(id));
        when(userRepository.findById(any())).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity.get());

        var result = userAdapter.updateUserToken(user,"token");

        assertThat(result).isNotNull();
    }

    @Test
    @Name("deleteUser Success")
    void deleteUserSuccess() {
        UUID id = UUID.randomUUID();
        userAdapter.deleteUser(id);
        verify(userRepository).deleteById(id);
    }

}