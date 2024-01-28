package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.CurrentUserDTO;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {
    @Autowired
    private BidListService bidListService;
    @Autowired
    private UserService userService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<BidList> bidlists = bidListService.getAll();

        CurrentUserDTO currentUserDTO = userService.getCurrentUser(authentication);
        model.addAttribute("isAdmin", currentUserDTO.getIsAdmin());
        model.addAttribute("remoteUser", currentUserDTO.getUsername());

        model.addAttribute("bidLists", bidlists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            bidListService.save(bid);
            model.addAttribute("bidLists", bidListService.getAll());
            return "redirect:/bidList/list";
        }

        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", bidListService.get(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bidList", bidListService.get(id));
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListService.save(bidList);
        model.addAttribute("bidLists", bidListService.getAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id,
                                             Model model) {
        bidListService.delete(id);
        model.addAttribute("bidLists", bidListService.getAll());
        return "redirect:/bidList/list";
    }
}
