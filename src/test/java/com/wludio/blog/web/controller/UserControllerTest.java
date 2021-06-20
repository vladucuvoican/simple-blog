package com.wludio.blog.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wludio.blog.config.SwaggerConfig;
import com.wludio.blog.service.UserService;
import com.wludio.blog.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(SwaggerConfig.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class UserControllerTest {

    private final static String BASE_URL = "/api/v1/users/";

    @Autowired
    private UserController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = Mockito.spy(new GlobalExceptionHandler());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionHandler).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetUserDtoForBadIdExpectBadRequest() throws Exception {
        // Given
        final long id = -1;

        // When
        MvcResult mvcResult = this.mockMvc.perform(
                put(BASE_URL + id)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String contentResponse = mvcResult.getResponse().getContentAsString();

        // Then
        assertThat(contentResponse).isEqualTo("");
    }

    @Test
    public void testGetUserDtoForInvalidIdExpectBadRequest() throws Exception {
        // Given
        final long id = 0;

        // When
        MvcResult mvcResult = this.mockMvc.perform(
                put(BASE_URL + id)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String contentResponse = mvcResult.getResponse().getContentAsString();

        // Then
        assertThat(contentResponse).isEqualTo("");
    }

}