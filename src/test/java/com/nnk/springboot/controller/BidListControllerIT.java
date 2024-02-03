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
import org.springframework.validation.BindingResult;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public class BidListControllerIT {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    BindingResult result;

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
         result = mock(BindingResult.class);

    }
    @Test
    public void testBidList() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }
    @Test
    public void testAddBidList() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }
    @Test
    public void testValidateBidList() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "account")
                        .param("type", "type")
                        .param("bid_quantity", String.valueOf(1d))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));


    }

    @Test
    public void testShowBidListForm() throws Exception {

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }
    @Test
    public void testUpdateBidList() throws Exception {

        mockMvc.perform(post("/bidList/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "account")
                        .param("type", "type")
                        .param("bid_quantity", String.valueOf(1d))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    public void testDeleteBidList() throws Exception {

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
    }
}


