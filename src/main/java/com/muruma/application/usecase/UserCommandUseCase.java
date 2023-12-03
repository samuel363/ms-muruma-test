package com.muruma.application.usecase;

import com.muruma.application.port.in.UserCommand;
import com.muruma.application.port.out.UserJPARepository;
import com.muruma.application.services.Encryption256GCMService;
import com.muruma.application.services.TokenGeneratorService;
import com.muruma.config.ErrorCode;
import com.muruma.config.exception.PasswordException;
import com.muruma.config.exception.UserLoginException;
import com.muruma.config.property.ApplicationProperty;
import com.muruma.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional
@AllArgsConstructor
@Slf4j
public class UserCommandUseCase implements UserCommand {
    private final UserJPARepository userJPARepository;
    private final ApplicationProperty property;
    private final Encryption256GCMService encryptionService;
    private final TokenGeneratorService tokenGeneratorService;

    @Override
    public User createUser(User user) {
        validatePassword(user.getPassword());
        user = user.withPassword(
                encryptionService.aesEncrypt(user.getPassword())
        );
        user = userJPARepository.createUser(user);
        user = userJPARepository.updateUserToken(
                user,
                generateToken(user)
        );
        return user;
    }

    @Override
    public User validateUser(User user) {
        user = user.withPassword(
                encryptionService.aesEncrypt(user.getPassword())
        );
        if (!userJPARepository.validateUser(user))
            throw new UserLoginException(ErrorCode.INVALID_CREDENTIALS);

        user = userJPARepository.updateLoginUser(user);
        user = userJPARepository.updateUserToken(
                user,
                generateToken(user)
        );

        return User.builder()
                .token(user.getToken())
                .build();
    }

    @Override
    public User updateUser(UUID id, User user, String token) {
        if (!validateToken(token.replace("Bearer ", ""), id.toString()))
            throw new UserLoginException(ErrorCode.UNAUTHORIZED_USER);
        if (!Objects.isNull(user.getPassword())) {
            user = user.withPassword(
                    encryptionService.aesEncrypt(user.getPassword())
            );
        }
        return userJPARepository.updateUser(id, user);
    }

    @Override
    public void deleteUser(UUID id) {
        userJPARepository.deleteUser(id);
    }

    private void validatePassword(String password) {
        Pattern pattern = Pattern.compile(property.getPasswordRegex());
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches())
            throw new PasswordException(ErrorCode.INVALID_PASSWORD);
    }

    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("created", user.getCreated().toString());
        claims.put("modified", user.getModified().toString());
        claims.put("last_login", user.getLastLogin().toString());
        claims.put("isactive", user.getIsActive().toString());
        return tokenGeneratorService.generateToken(claims, user.getId().toString());
    }

    private Boolean validateToken(String token, String id) {
        final String subject = tokenGeneratorService.getSubjectFromToken(token);
        return (subject.equals(id) && !tokenGeneratorService.isTokenExpired(token));
    }

}
