package com.muruma.adapter.controller.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.muruma.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateUserRequestModel {

    private static final String ERROR_BLANK = "El campo '%s' no puede estar vacio";

    @Email(message = "El correo es invalido")
    private String email;
    private String name;
    private String password;
    private Boolean isActive;

    public User toDomain() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .isActive(this.isActive)
                .build();
    }
}
