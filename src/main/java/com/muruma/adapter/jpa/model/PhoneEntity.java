package com.muruma.adapter.jpa.model;

import com.muruma.domain.Phone;
import com.muruma.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "phone")
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer number;
    private String cityCode;
    private String countryCode;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static PhoneEntity of(Phone phone) {
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber(phone.getNumber());
        phoneEntity.setCityCode(phone.getCityCode());
        phoneEntity.setCountryCode(phone.getCountryCode());
        return phoneEntity;
    }

    public static PhoneEntity ofWithUser(UserEntity user, Phone phone) {
        var phoneEntity = of(phone);
        phoneEntity.setUser(user);
        return phoneEntity;
    }

    public Phone toDomain() {
        return Phone.builder()
                .id(id)
                .number(number)
                .cityCode(cityCode)
                .countryCode(countryCode)
                .build();
    }
}
