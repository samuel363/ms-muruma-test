package com.muruma.faker;

import com.muruma.adapter.controller.model.UserRequestModel;

import java.util.List;

public class UserRequestModelMock {
    public static final String STRING_VALUE = "Pedro Perez";

    public static UserRequestModel getUserRest() {
        return UserRequestModel.builder()
                .email("test@test.com")
                .name(STRING_VALUE)
                .password(STRING_VALUE)
                .phones(List.of(
                        UserRequestModel.PhoneRequestModel.builder()
                                .number(1234567)
                                .cityCode("1")
                                .countryCode("1")
                                .build()
                ))
                .build();
    }
}
