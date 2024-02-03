package com.nnk.springboot.service;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;
    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName ruleName = new RuleName();
    private RuleName ruleName1 = new RuleName();
    private List<RuleName> ruleNames = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        ruleName.setId(1);
        ruleName.setName("name");
        ruleName.setDescription("description");
        ruleName.setJson("json");
        ruleName.setTemplate("template");
        ruleName.setSqlPart("sqlpart");
        ruleName.setSqlStr("sqlpart");


        ruleName1.setId(2);
        ruleName1.setName("name");
        ruleName1.setDescription("description");
        ruleName1.setJson("json");
        ruleName1.setTemplate("template");
        ruleName1.setSqlPart("sqlpart");
        ruleName1.setSqlStr("sqlpart");

        ruleNames.add(ruleName);
        ruleNames.add(ruleName1);

    }

    @Test
    @DisplayName("get All Trade")
    public void testGetAllTrade() {

        // Given.

        // When.
        when(ruleNameRepository.findAll()).thenReturn(ruleNames);
        List<RuleName> lists = ruleNameService.getAll();

        // Then.
        assertEquals(ruleNames.size(), lists.size());
        verify(ruleNameRepository, times(1)).findAll();


    }
    @Test
    @DisplayName("get Trade by id")
    public void testGetTradeById() {

        // Given.

        // When.
        when(ruleNameRepository.findById(1)).thenReturn(Optional.ofNullable(ruleName1));
        RuleName res = ruleNameService.get(1);

        // Then.
        assertEquals(res.getId(), ruleName1.getId());
        assertEquals(res.getDescription(), ruleName1.getDescription());
        assertEquals(res.getJson(), ruleName1.getJson());
        verify(ruleNameRepository, times(1)).findById(1);


    }

    @Test
    @DisplayName("save Trade")
    public void testSaveTrade() {

        // Given.
        RuleName ruleName2 = new RuleName();
        ruleName2.setId(3);
        ruleName2.setName("name 3");
        ruleName2.setDescription("description");

        // When.
        ruleNameService.save(ruleName2);

        // Then.
        verify(ruleNameRepository, times(1)).save(ruleName2);


    }
    @Test
    @DisplayName("delete Trade")
    public void testDeleteTrade() {


        // When.
        ruleNameService.delete(3);

        // Then.
        verify(ruleNameRepository, times(1)).deleteById(3);


    }


}
