package com.muruma.application.usecase;

import com.muruma.application.port.in.UserQuery;
import com.muruma.application.port.out.UserJPARepository;
import com.muruma.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
@AllArgsConstructor
@Slf4j
public class UserQueryUseCase implements UserQuery {
    private final UserJPARepository userJPARepository;

    @Override
    public User getUser(UUID id) {
        return userJPARepository.getUser(id);
    }

    @Override
    public List<User> getUsers() {
        return userJPARepository.getUsers();
    }
}
