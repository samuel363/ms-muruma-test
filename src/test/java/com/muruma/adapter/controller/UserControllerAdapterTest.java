package com.muruma.adapter.controller;

import com.muruma.adapter.controller.model.UpdateUserRequestModel;
import com.muruma.application.port.in.UserCommand;
import com.muruma.application.port.in.UserQuery;
import com.muruma.faker.UpdateUserRequestModelMock;
import com.muruma.faker.UserLoginRequestModelMock;
import com.muruma.faker.UserMock;
import com.muruma.faker.UserRequestModelMock;
import com.muruma.faker.UserResponseModelMock;
import com.muruma.utils.UtilsByTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserControllerAdapterTest")
@WebMvcTest(UserControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
class UserControllerAdapterTest {
    public static final String CREATE_USER_URL = "/api/v1/user/create";
    public static final String LOGIN_USER_URL = "/api/v1/user/login";
    private static final String USER_URL = "/api/v1/user/%s";

    @Autowired
    private MockMvc restRequest;

    @MockBean
    private UserCommand userCommand;

    @MockBean
    private UserQuery userQuery;

    @Test
    @DisplayName("When user is create success")
    void createUserSuccessInternal() throws Exception {
        when(userCommand.createUser(UserRequestModelMock.getUserRest().toDomain())).thenReturn(UserMock.build(null));

        restRequest.perform(
                        MockMvcRequestBuilders.post(CREATE_USER_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(UtilsByTest.JsonToString(UserRequestModelMock.getUserRest())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(UtilsByTest.JsonToString(UserResponseModelMock.build())));
    }

    @Test
    @DisplayName("When user is update success")
    void updateUserSuccessInternal() throws Exception {
        var id = UUID.randomUUID();
        var user = UpdateUserRequestModelMock.getUserRest().toDomain().withPhones(null);
        when(userCommand.updateUser(id, user, "token")).thenReturn(UserMock.build(id));

        restRequest.perform(
                        MockMvcRequestBuilders.put(String.format(USER_URL, id))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(UtilsByTest.JsonToString(UpdateUserRequestModelMock.getUserRest()))
                                .header("Authorization","token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(UserResponseModelMock.build())));
    }

    @Test
    @DisplayName("When user is validate success")
    void validateUserSuccessInternal() throws Exception {
        var id = UUID.randomUUID();
        when(userCommand.validateUser(UserLoginRequestModelMock.build().toDomain())).thenReturn(UserMock.build(id));

        restRequest.perform(
                        MockMvcRequestBuilders.post(String.format(LOGIN_USER_URL))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(UtilsByTest.JsonToString(UserLoginRequestModelMock.build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(UserResponseModelMock.build())));
    }

    @Test
    @DisplayName("When user is delete success")
    void deleteUserSuccessInternal() throws Exception {
        var id = UUID.randomUUID();

        restRequest.perform(
                        MockMvcRequestBuilders.delete(String.format(USER_URL, id))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(UtilsByTest.JsonToString(UserRequestModelMock.getUserRest())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When user is get success")
    void getUserSuccessInternal() throws Exception {
        var id = UUID.randomUUID();
        when(userQuery.getUser(id)).thenReturn(UserMock.build(id));

        restRequest.perform(
                        MockMvcRequestBuilders.get(String.format(USER_URL, id))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(UtilsByTest.JsonToString(UserRequestModelMock.getUserRest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(UserResponseModelMock.build())));
    }

}