package com.muruma.mocks;

import com.muruma.adapter.controller.model.RestResponse;
import com.muruma.adapter.controller.model.UserRequestModel;
import com.muruma.domain.User;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public final class UserMock {
    public static final String STRING_VALUE = "Pedro Perez";
    public static final String CREATE_USER = "/api/v1/user/create";

    public static UserRequestModel getUserRest() {
        return UserRequestModel.builder()
                .name(STRING_VALUE)
                .build();
    }

    public static User getUserDomain() {
        return User.builder()
                .name(STRING_VALUE)
                .build();
    }

    public static RestResponse<Integer> getRestResponseCreateUser() {
        return new RestResponse<>(
                "",
                HttpStatus.CREATED.value(),
                CREATE_USER,
                0,
                Collections.emptyMap());
    }

}
