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
public class RuleNameControllerIT {
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
    public void testRuleNameList() throws Exception {

        mockMvc.perform(get("/ruleName/list").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("remoteUser","user"));
    };

    @Test
    public void testAddRuleName() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }
    @Test
    public void testValidateRuleName() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "test")
                        .param("description", "test")
                        .param("json", "test")
                        .param("template", "test")
                        .param("sqlStr", "test")
                        .param("sqlPart", "test")
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));


    }

    @Test
    public void testShowRuleNameForm() throws Exception {

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }


    @Test
    public void testUpdateRuleName() throws Exception {

        mockMvc.perform(post("/ruleName/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "test")
                        .param("description", "test")
                        .param("json", "test")
                        .param("template", "test")
                        .param("sqlStr", "test")
                        .param("sqlPart", "test")
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    public void testDeleteRuleName() throws Exception {

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

}


