package com.muruma.adapter.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.muruma.domain.Phone;
import com.muruma.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequestModel {

    private static final String ERROR_BLANK = "El campo '%s' no puede estar vacio";

    @Email(message = "El correo es invalido")
    @NotBlank(message = ERROR_BLANK)
    private String email;
    @NotBlank(message = ERROR_BLANK)
    private String name;
    @NotBlank(message = ERROR_BLANK)
    private String password;
    private List<PhoneRequestModel> phones;

    @Data
    @Builder
    public static class PhoneRequestModel {
        @NotNull
        @Min(0)
        @Max(99999999)
        private Integer number;
        @JsonProperty(value = "citycode")
        private String cityCode;
        @JsonProperty(value = "contrycode")
        private String countryCode;

        public Phone toDomain() {
            return Phone.builder()
                            .number(number)
                            .cityCode(cityCode)
                            .countryCode(countryCode)
                            .build();
        }
    }

    public User toDomain() {
        List<Phone> phones = null;
        if (!Objects.isNull(this.phones)) {
            phones = this.phones.stream()
                            .map(PhoneRequestModel::toDomain)
                            .collect(Collectors.toList());
        }

        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .phones(phones)
                .build();
    }
}
