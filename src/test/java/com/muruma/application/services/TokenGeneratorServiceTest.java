package com.muruma.application.services;

import com.muruma.config.property.EncryptProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("TokenGeneratorServiceTest")
@ExtendWith(MockitoExtension.class)
public class TokenGeneratorServiceTest {

    @InjectMocks
    TokenGeneratorService service;

    @Mock
    private EncryptProperty encryptProperty;

    @Test
    @DisplayName("createUser Success")
    void createUserSuccess() {
        String data = "test";
        when(encryptProperty.getJwtKey()).thenReturn("123456789012345678901234567890123456789001234567890");
        when(encryptProperty.getJwtTimeout()).thenReturn(1);

        Map<String, Object> claims = new HashMap<>();
        var token = service.generateToken(claims,data);
        var subject = service.getSubjectFromToken(token);
        var expired = service.isTokenExpired(token);
        assertThat(subject).isEqualTo(data);
        assertThat(expired).isFalse();
    }

}
