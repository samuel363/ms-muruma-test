package com.muruma.application.usecase;

import com.muruma.application.port.out.UserJPARepository;
import com.muruma.application.services.Encryption256GCMService;
import com.muruma.application.services.TokenGeneratorService;
import com.muruma.config.property.ApplicationProperty;
import com.muruma.faker.UserMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("UserCommandUseCaseTest")
@ExtendWith(MockitoExtension.class)
public class UserCommandUseCaseTest {
    @InjectMocks
    private UserCommandUseCase useCase;
    @Mock
    private UserJPARepository userJPARepository;
    @Mock
    private ApplicationProperty property;
    @Mock
    private Encryption256GCMService encryptionService;
    @Mock
    private TokenGeneratorService tokenGeneratorService;

    @Test
    @DisplayName("createUser Success")
    void createUserSuccess() {
        var user = UserMock.build(null);
        when(property.getPasswordRegex()).thenReturn("^.{5,}$");
        when(userJPARepository.createUser(any())).thenReturn(user);
        when(tokenGeneratorService.generateToken(any(), any())).thenReturn("token");
        when(userJPARepository.updateUserToken(any(), any())).thenReturn(user.withToken("token"));

        var response = useCase.createUser(user);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotBlank();
    }

    @Test
    @DisplayName("updateUser Success")
    void updateUserSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        var userUpdated = user.withModified(LocalDateTime.now().plusHours(60));
        when(encryptionService.aesEncrypt(any())).thenReturn("password");
        user = user.withPassword("password");
        when(userJPARepository.updateUser(id, user)).thenReturn(userUpdated);
        when(tokenGeneratorService.getSubjectFromToken(any())).thenReturn(id.toString());
        when(tokenGeneratorService.isTokenExpired(any())).thenReturn(false);

        var response = useCase.updateUser(id, user, " token");

        assertThat(response.getModified().toString())
                .isNotEqualTo(user.getModified().toString());
    }


    @Test
    @DisplayName("validateUser Success")
    void validateUserSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        var uservalidated = user.withLastLogin(LocalDateTime.now().plusHours(60));
        when(encryptionService.aesEncrypt(any())).thenReturn("password");
        user = user.withPassword("password");
        when(userJPARepository.validateUser(user)).thenReturn(true);
        when(userJPARepository.updateLoginUser(any())).thenReturn(uservalidated);
        when(userJPARepository.updateUserToken(any(), any())).thenReturn(uservalidated.withToken("token"));

        var response = useCase.validateUser(user);

        assertThat(response.getToken()).isNotBlank();
    }

    @Test
    @DisplayName("deleteUser Success")
    void deleteUserSuccess() {
        UUID id = UUID.randomUUID();
        useCase.deleteUser(id);
        verify(userJPARepository).deleteUser(id);
    }

}
