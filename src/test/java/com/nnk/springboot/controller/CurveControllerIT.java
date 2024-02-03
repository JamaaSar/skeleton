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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest()
@ActiveProfiles("test")
public class CurveControllerIT {
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
    public void testCurvePoint() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("remoteUser","user"));
    }
    @Test
    public void testAddCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }
    @Test
    public void testValidateCurvePoint() throws Exception {

        mockMvc.perform(post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curve_id", String.valueOf(1))
                        .param("term", String.valueOf(1))
                        .param("value", String.valueOf(1)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    public void testShowCurvePointForm() throws Exception {

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }
    @Test
    public void testUpdateCurvePoint() throws Exception {

        mockMvc.perform(post("/curvePoint/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curve_id", String.valueOf(1))
                        .param("term", String.valueOf(1))
                        .param("value", String.valueOf(1)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }
    @Test
    public void testDeleteCurvePoint() throws Exception {

              mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }
}


