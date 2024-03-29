package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.CurrentUserDTO;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class RuleNameController {

    @Autowired
    RuleNameService ruleNameService;
    @Autowired
    private UserService userService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUserDTO currentUserDTO = userService.getCurrentUser(authentication);
        model.addAttribute("isAdmin", currentUserDTO.getIsAdmin());
        model.addAttribute("remoteUser", currentUserDTO.getUsername());
        model.addAttribute("ruleNames", ruleNameService.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.save(ruleName)  ;
            model.addAttribute("ruleNames", ruleNameService.getAll());
            return "redirect:/ruleName/list";
        }


        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ruleName", ruleNameService.get(id));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleNameService.get(id));
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.save(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);
        model.addAttribute("ruleNames", ruleNameService.getAll());
        return "redirect:/ruleName/list";
    }
}
