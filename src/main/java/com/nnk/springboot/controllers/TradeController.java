package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.CurrentUserDTO;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {

    @Autowired
    TradeService tradeService;
    @Autowired
    private UserService userService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUserDTO currentUserDTO = userService.getCurrentUser(authentication);
        model.addAttribute("isAdmin", currentUserDTO.getIsAdmin());
        model.addAttribute("remoteUser", currentUserDTO.getUsername());
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            tradeService.save(trade)  ;
            model.addAttribute("trades", tradeService.getAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("trade", tradeService.get(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trade", tradeService.get(id));
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.save(trade);
        model.addAttribute("trades", tradeService.getAll());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.delete(id);
        model.addAttribute("trades", tradeService.getAll());
        return "redirect:/trade/list";
    }
}
