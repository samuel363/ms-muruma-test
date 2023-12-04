package com.muruma.application.usecase;

import com.muruma.application.port.out.UserJPARepository;
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

@DisplayName("UserQueryUseCaseTest")
@ExtendWith(MockitoExtension.class)
public class UserQueryUseCaseTest {
    @InjectMocks
    private UserQueryUseCase useCase;
    @Mock
    private UserJPARepository userJPARepository;

    @Test
    @DisplayName("getUser Success")
    void getUserSuccess() {
        UUID id = UUID.randomUUID();
        var user = UserMock.build(id);
        when(userJPARepository.getUser(id)).thenReturn(user);

        var response = useCase.getUser(id);
        verify(userJPARepository).getUser(id);
        assertThat(response).isNotNull();
    }

}
