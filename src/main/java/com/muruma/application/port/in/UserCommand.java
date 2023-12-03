package com.muruma.application.port.in;

import com.muruma.domain.User;

import java.util.UUID;

public interface UserCommand {
    User createUser(User user);

    User updateUser(UUID id, User domain);

    User validateUser(User user);

    void deleteUser(UUID id);
}
