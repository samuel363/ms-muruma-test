package com.muruma.application.usecase;

import com.muruma.application.port.in.PhoneCommand;
import com.muruma.application.port.out.PhoneJPARepository;
import com.muruma.application.services.TokenGeneratorService;
import com.muruma.config.ErrorCode;
import com.muruma.config.exception.UserLoginException;
import com.muruma.domain.Phone;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Component
@Transactional
@AllArgsConstructor
@Slf4j
public class PhoneCommandUseCase implements PhoneCommand {
    private final PhoneJPARepository phoneJPARepository;
    private final TokenGeneratorService tokenGeneratorService;

    @Override
    public Phone createPhone(UUID userId, Phone phone) {
        return phoneJPARepository.createPhone(userId, phone);
    }

    @Override
    public Phone updatePhone(UUID userId, UUID id, Phone phone, String token) {
        if (!tokenGeneratorService.validateToken(token.replace("Bearer ", ""), userId.toString()))
            throw new UserLoginException(ErrorCode.UNAUTHORIZED_USER);
        return phoneJPARepository.updatePhone(userId, id, phone);
    }

    @Override
    public void deletePhone(UUID userId, UUID id) {
        phoneJPARepository.deletePhone(userId, id);
    }

}
