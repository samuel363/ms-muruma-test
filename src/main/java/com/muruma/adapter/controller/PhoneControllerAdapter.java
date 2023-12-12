package com.muruma.adapter.controller;

import com.muruma.adapter.controller.model.UserRequestModel;
import com.muruma.adapter.controller.model.UserResponseModel;
import com.muruma.application.port.in.PhoneCommand;
import com.muruma.application.port.in.PhoneQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/user/{user_id}/phone")
public final class PhoneControllerAdapter {
    private static final String PHONE = "/{id}";
    private static final String PHONES = "/all";
    private final PhoneCommand phoneCommand;
    private final PhoneQuery phoneQuery;

    public PhoneControllerAdapter(PhoneCommand phoneCommand, PhoneQuery phoneQuery) {
        this.phoneCommand = phoneCommand;
        this.phoneQuery = phoneQuery;
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponseModel.PhoneResponseModel createInternalPhone(
            @PathVariable("user_id") UUID userId,
            @Valid @RequestBody UserRequestModel.PhoneRequestModel request
    ) {
        log.info("Llamada al servicio");
        var response = phoneCommand.createPhone(userId,request.toDomain());
        log.info("Respuesta del servicio: [{}]", response);
        return UserResponseModel.PhoneResponseModel.of(response);
    }

    // --------------------

    @GetMapping(PHONE)
    public UserResponseModel.PhoneResponseModel readPhone(
            @PathVariable("user_id") UUID userId,
            @PathVariable("id") UUID id
    ) {
        log.info("Llamada al servicio");
        var response = phoneQuery.getPhone(userId, id);
        log.info("Respuesta del servicio: [{}]", response);
        return UserResponseModel.PhoneResponseModel.of(response);
    }

    @GetMapping(PHONES)
    public List<UserResponseModel.PhoneResponseModel> getAllUserPhones(
            @PathVariable("user_id") UUID userId
    ) {
        log.info("Llamada al servicio");
        var response = phoneQuery.getPhones(userId);
        log.info("Respuesta del servicio: [{}]", response);
        return response.stream().map(UserResponseModel.PhoneResponseModel::of).collect(Collectors.toList());
    }


    @PutMapping(PHONE)
    public UserResponseModel.PhoneResponseModel updatePhone(
            @RequestHeader("Authorization") String token,
            @PathVariable("user_id") UUID userId,
            @PathVariable("id") UUID id,
            @Valid @RequestBody UserRequestModel.PhoneRequestModel request
    ) {
        log.info("Llamada al servicio");
        var response = phoneCommand.updatePhone(userId, id, request.toDomain(), token);
        log.info("Respuesta del servicio: [{}]", response);
        return UserResponseModel.PhoneResponseModel.of(response);
    }

    @DeleteMapping(PHONE)
    public void deletePhone(
            @PathVariable("user_id") UUID userId,
            @PathVariable("id") UUID id
    ) {
        log.info("Llamada al servicio");
        phoneCommand.deletePhone(userId, id);
        log.info("Respuesta del servicio:");
    }

}
