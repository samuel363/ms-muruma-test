package com.muruma.application.port.in;

import com.muruma.domain.User;

import java.util.UUID;

public interface UserQuery {
    User getUser(UUID id);
}
