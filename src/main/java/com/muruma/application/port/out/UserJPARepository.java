package com.muruma.application.port.out;

import com.muruma.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserJPARepository {
    User createUser(User user);

    User getUser(UUID id);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);

    boolean validateUser(User user);

    User updateLoginUser(User user);

    User updateUserToken(User user, String s);

    void updateUserActive(UUID id, Boolean status);

    List<User> getUsers();
}
