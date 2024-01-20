package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {

    private RuleNameRepository ruleNameRepository;

    public void delete(Integer id) {
        ruleNameRepository.deleteById(id);
    }

    public List<RuleName> getAll() {
        return ruleNameRepository.findAll();
    }
    public RuleName get(Integer id) {
        return ruleNameRepository.findById(id).orElseThrow();
    }
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }
}
