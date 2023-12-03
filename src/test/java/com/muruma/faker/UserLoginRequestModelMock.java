package com.muruma.faker;

import com.muruma.adapter.controller.model.UserLoginRequestModel;

public final class UserLoginRequestModelMock {
    public static final String STRING_VALUE = "Pedro Perez";

    public static UserLoginRequestModel build() {
        return UserLoginRequestModel.builder()
                .email("a@a.com")
                .password(STRING_VALUE)
                .build();
    }

}
