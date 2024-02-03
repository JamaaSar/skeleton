package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurrentUserDTO;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;
    @Autowired
    private UserService userService;
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUserDTO currentUserDTO = userService.getCurrentUser(authentication);
        model.addAttribute("isAdmin", currentUserDTO.getIsAdmin());
        model.addAttribute("remoteUser", currentUserDTO.getUsername());
        model.addAttribute("curvePoints", curvePointService.getAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {


        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {


        if (!result.hasErrors()) {
            curvePointService.save(curvePoint)  ;
            model.addAttribute("curvePoints", curvePointService.getAll());
            return "redirect:/curvePoint/list";
        }

        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoint", curvePointService.get(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("curvePoint", curvePointService.get(id));
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.save(curvePoint);
        model.addAttribute("curvePoints", curvePointService.getAll());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.delete(id);
        model.addAttribute("curvePoints", curvePointService.getAll());
        return "redirect:/curvePoint/list";
    }
}
