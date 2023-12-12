package com.muruma.application.port.in;

import com.muruma.domain.User;

import java.util.UUID;

public interface UserCommand {
    User createUser(User user);

    User updateUser(UUID id, User domain, String token);

    User validateUser(User user);

    void logicDeleteUser(UUID id, String token);

    void logicRestoreUser(UUID id, String token);

    void deleteUser(UUID id);
}
