package com.muruma.adapter.controller.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.muruma.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLoginRequestModel {

    private static final String ERROR_BLANK = "El campo '%s' no puede estar vacio";

    @Email(message = "El correo es invalido")
    @NotBlank(message = ERROR_BLANK)
    private String email;
    @NotBlank(message = ERROR_BLANK)
    private String password;

    public User toDomain() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
