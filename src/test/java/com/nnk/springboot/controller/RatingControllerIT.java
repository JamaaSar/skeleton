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
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public class RatingControllerIT {
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
    public void testRating() throws Exception {


        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("remoteUser","user"));
    }
    @Test
    public void testAddRating() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }
    @Test
    public void testValidateRating() throws Exception {

        mockMvc.perform(post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("moodys_rating", "test")
                        .param("sandprating", "test")
                        .param("fitch_rating", "test")
                        .param("order_number", String.valueOf(1)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    public void testShowRatingForm() throws Exception {

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }
    @Test
    public void testUpdateRating() throws Exception {

        mockMvc.perform(post("/rating/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("moodys_rating", "test")
                        .param("sandprating", "test")
                        .param("fitch_rating", "test")
                        .param("order_number", String.valueOf(1)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    public void testDeleteRating() throws Exception {

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
    }

}


