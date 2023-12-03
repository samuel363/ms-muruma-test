package com.muruma.adapter.controller;

import com.muruma.application.port.in.UserCommand;
import com.muruma.mocks.UserMock;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserControllerAdapterTest")
@WebMvcTest(UserControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
class UserEntityControllerAdapterTest {
    private static final String GET_USER = "/api/v1/user/%s";
    private static final String GET_INTERNAL_USER = "/api/v1/user/internal/%s";

    @Autowired
    private MockMvc restRequest;

    @MockBean
    private UserCommand createUser;

    @Test
    @DisplayName("When user is create success")
    void createUserSuccessInternal() throws Exception {
        //when(createUser.createUser(UserMock.getUserDomain())).thenReturn(0);
        restRequest.perform(MockMvcRequestBuilders.post(String.format(UserMock.CREATE_USER))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UtilsByTest.JsonToString(UserMock.getUserRest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(UserMock.getRestResponseCreateUser())));
/*        restRequest.perform(MockMvcRequestBuilders.post(CREATE_USER))
                .andExpect(status().isOk());*/
    }


}