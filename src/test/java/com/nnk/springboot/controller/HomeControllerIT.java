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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest()
@ActiveProfiles("test")
public class HomeControllerIT {
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


    }
    @Test
    public void testHome() throws Exception {

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(
                "user");

        mockMvc.perform(get("/"))
                .andExpect(status().isFound());
    }
 //   @Test
    public void testHomeUserNull() throws Exception {

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(
                null);
        mockMvc.perform(get("/"))
                .andExpect(status().isFound());
    }
    @Test
    public void testLogOut() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().isFound());
    }
    @Test
    public void testAdminHome() throws Exception {
        mockMvc.perform(get("/admin/home"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
    }
    @Test
    public void testError() throws Exception {

        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"));
    }

}


