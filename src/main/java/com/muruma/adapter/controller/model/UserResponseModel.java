package com.muruma.adapter.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.muruma.domain.Phone;
import com.muruma.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserResponseModel {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
    private List<PhoneResponseModel> phones;

    @Data
    @Builder
    public static class PhoneResponseModel {
        private UUID id;
        private Integer number;
        private String cityCode;
        private String countryCode;

        public static PhoneResponseModel of(Phone phone) {
            return PhoneResponseModel.builder()
                    .id(phone.getId())
                    .number(phone.getNumber())
                    .cityCode(phone.getCityCode())
                    .countryCode(phone.getCountryCode())
                    .build();
        }
    }

    public static UserResponseModel of(User user) {
        List<PhoneResponseModel> phones = null;
        if (!Objects.isNull(user.getPhones())) {
            phones = user.getPhones().stream()
                    .map(PhoneResponseModel::of)
                    .collect(Collectors.toList());
        }

        return UserResponseModel.builder()
                .id(user.getId())
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .token(user.getToken())
                .phones(phones)
                .build();
    }
}
