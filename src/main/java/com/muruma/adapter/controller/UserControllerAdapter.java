package com.muruma.adapter.controller;

import com.muruma.adapter.controller.model.UpdateUserRequestModel;
import com.muruma.adapter.controller.model.UserLoginRequestModel;
import com.muruma.adapter.controller.model.UserRequestModel;
import com.muruma.adapter.controller.model.UserResponseModel;
import com.muruma.application.port.in.UserCommand;
import com.muruma.application.port.in.UserQuery;
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
@RequestMapping("/api/v1/user")
public final class UserControllerAdapter {
    private static final String LOGIN = "/login";
    private static final String USER = "/{id}";
    private static final String USERS = "/all";
    private final UserCommand userCommand;
    private final UserQuery userQuery;

    public UserControllerAdapter(UserCommand userCommand, UserQuery userQuery) {
        this.userCommand = userCommand;
        this.userQuery = userQuery;
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponseModel createInternalUser(
            @Valid @RequestBody UserRequestModel request
    ) {
        log.info("Llamada al servicio");
        var response = userCommand.createUser(request.toDomain());
        log.info("Respuesta del servicio: [{}]", response);
        return UserResponseModel.of(response);
    }

    // --------------------

    @PostMapping(LOGIN)
    public UserResponseModel loginUser(
            @Valid @RequestBody UserLoginRequestModel request
    ) {
        log.info("Llamada al servicio");
        var response = userCommand.validateUser(request.toDomain());
        log.info("Respuesta del servicio: [{}]", response);
        return UserResponseModel.of(response);
    }

    @GetMapping(USER)
    public UserResponseModel readUser(
            @PathVariable("id") UUID id
    ) {
        log.info("Llamada al servicio");
        var response = userQuery.getUser(id);
        log.info("Respuesta del servicio: [{}]", response);
        return UserResponseModel.of(response);
    }

    @GetMapping(USERS)
    public List<UserResponseModel> getAllUsers(
    ) {
        log.info("Llamada al servicio");
        var response = userQuery.getUsers();
        log.info("Respuesta del servicio: [{}]", response);
        return response.stream().map(UserResponseModel::of).collect(Collectors.toList());
    }

    @PutMapping(USER)
    public UserResponseModel updateUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateUserRequestModel request
    ) {
        log.info("Llamada al servicio");
        var response = userCommand.updateUser(id, request.toDomain(), token);
        log.info("Respuesta del servicio: [{}]", response);
        return UserResponseModel.of(response);
    }

    @DeleteMapping(USER)
    public void deleteUser(
            @PathVariable("id") UUID id
    ) {
        log.info("Llamada al servicio");
        userCommand.deleteUser(id);
        log.info("Respuesta del servicio:");
    }

}
