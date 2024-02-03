package com.nnk.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public class UserControllerIT {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("user");

    }
    @Test
    public void testUserList() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }
    @Test
    public void testAddUser() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }
    @Test
    public void testValidateUser() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "test")
                        .param("password", "test")
                        .param("fullname", "test")
                        .param("role", "USER"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    public void testShowUserForm() throws Exception {

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }
    @Test
    public void testUpdateUser() throws Exception {

        mockMvc.perform(post("/user/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "test")
                        .param("password", "test")
                        .param("fullname", "test")
                        .param("role", "USER"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }
    @Test
    public void testDeleteUser() throws Exception {

        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }
}


