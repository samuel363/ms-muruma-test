package com.muruma.application.services;

import com.muruma.config.property.EncryptProperty;
import com.muruma.faker.UserMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Encryption256GCMServiceTest")
@ExtendWith(MockitoExtension.class)
public class Encryption256GCMServiceTest {

    @InjectMocks
    Encryption256GCMService service;

    @Mock
    private EncryptProperty encryptProperty;

    @Test
    @DisplayName("createUser Success")
    void createUserSuccess() {
        String data = "test";
        when(encryptProperty.getAesKey256()).thenReturn("1234567890");
        var encrypt = service.aesEncrypt(data);
        var decrypt = service.aesDecrypt(encrypt);
        assertThat(decrypt).isEqualTo(data);
    }

}
