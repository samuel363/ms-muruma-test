package com.muruma.faker;

import com.muruma.adapter.controller.model.UpdateUserRequestModel;
import com.muruma.adapter.controller.model.UserRequestModel;

import java.util.List;

public class UpdateUserRequestModelMock {
    public static final String STRING_VALUE = "Pedro Perez";

    public static UpdateUserRequestModel getUserRest() {
        return UpdateUserRequestModel.builder()
                .email("test@test.com")
                .name(STRING_VALUE)
                .password(STRING_VALUE)
                .build();
    }
}
