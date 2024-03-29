package com.nnk.springboot.controllers;

import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController
{
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String home(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().
				getAuthentication();
		if(authentication.getPrincipal() != null ){
			userService.creatUserFromOauth2(authentication);
			return "redirect:/bidList/list";
		}
		return "/login";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}
	@PostMapping("/logout")
	public String logout(Authentication authentication, HttpServletRequest request,
						 HttpServletResponse response) {
		this.logoutHandler.logout(request, response, authentication);

		return "redirect:/login";
	}
	@GetMapping("/error")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView();
		String errorMessage= "You are not authorized for the requested data.";
		mav.addObject("errorMsg", errorMessage);
		mav.setViewName("403");
		return mav;
	}


}
