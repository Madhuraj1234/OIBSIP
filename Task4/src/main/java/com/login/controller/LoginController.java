package com.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.login.entities.User;
import com.login.repository.FormRepository;

@Controller
public class LoginController {
	
	@Autowired
	private FormRepository formRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/form")
	public String auth1()
	{
		return "login";
	}
	
	@GetMapping("/register")
	public String auth2(Model model)
	{
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/doRegister")
	public String signup(@ModelAttribute("user") User user , Model model)
	{
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User user1 = this.formRepository.save(user);
		System.out.println(user1);
		return "register";
	}
	
	@GetMapping("/success")
	public String secured()
	{
		return "success";
	}
	
	@GetMapping("/logout")
	public String logout()
	{
		return "login";
	}
	
}
