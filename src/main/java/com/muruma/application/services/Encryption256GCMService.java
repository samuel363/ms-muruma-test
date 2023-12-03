package com.muruma.application.services;


import com.muruma.application.services.exception.EncryptionException;
import com.muruma.config.ErrorCode;
import com.muruma.config.property.EncryptProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
public class Encryption256GCMService {

    private static final String AES = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_LENGTH = 128;
    private static final String KEY_ALGORITHM = "AES";
    private static final String MSG_BEGIN_ENCRYPT = "Comienza el cifrado del siguiente dato {}";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

    private final EncryptProperty encryptProperty;

    public Encryption256GCMService(EncryptProperty encryptProperty) {
        this.encryptProperty = encryptProperty;
    }


    public String aesEncrypt(String data) {
        log.debug(MSG_BEGIN_ENCRYPT, data);
        try {
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance(AES);

            byte[] iv = new byte[GCM_IV_LENGTH];

            final String key = Optional.ofNullable(encryptProperty.getAesKey256())
                    .orElseThrow(() -> new EncryptionException(ErrorCode.ENCRYPT_PROCESS_FAILED));

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_LENGTH, iv);

            // Initialize Cipher for ENCRYPT_MODE

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key), gcmParameterSpec);

            // Perform Encryption
            byte[] cipherText = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptionException(ErrorCode.CLIENT_BAD_REQUEST, ErrorCode.ENCRYPT_PROCESS_FAILED.getReason(), e);
        }
    }

    public String aesDecrypt(String data) {

        log.debug("Comienza el descifrado del siguiente dato {}", data);
        try {
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance(AES);

            byte[] iv = new byte[GCM_IV_LENGTH];

            final String key = Optional.ofNullable(encryptProperty.getAesKey256())
                    .orElseThrow(() -> new EncryptionException(ErrorCode.DECRYPT_PROCESS_FAILED));

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_LENGTH, iv);

            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key), gcmParameterSpec);

            // Perform Decryption
            byte[] decryptedText = cipher.doFinal(Base64.getDecoder().decode(data));

            return new String(decryptedText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | IllegalArgumentException |
                 BadPaddingException e) {
            throw new EncryptionException(ErrorCode.CLIENT_BAD_REQUEST, ErrorCode.DECRYPT_PROCESS_FAILED.getReason(), e);
        }
    }

    /**
     * Generate encryption key
     *
     * @return SecretKeySpec
     */
    @NotNull
    private static SecretKeySpec getSecretKey(@NotNull String encryptPass) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // Initialize the key generator, AES requires the key length to be 128 bits, 192 bits, 256 bits
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
        secureRandom.setSeed(encryptPass.getBytes());
        kg.init(AES_KEY_SIZE, secureRandom);
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// Convert to AES private key
    }
}
