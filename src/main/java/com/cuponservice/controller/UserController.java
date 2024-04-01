package com.cuponservice.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cuponservice.mode.Role;
import com.cuponservice.mode.User;
import com.cuponservice.repository.UserRepo;
import com.cuponservice.service.SecurityServiceImp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	private SecurityServiceImp securityServiceImp;
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/showReg")
	public String showRegisterationPage() {
		return "registerUser";// html page name
	}

	@PostMapping("/registerUser") // this match with html page action
	public String register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		HashSet<Role> roles = new HashSet<>();//set roles
		Role role = new Role();
		role.setId(2L);
		user.setRoles(roles);
		userRepo.save(user);
		return "login";
	}

	@GetMapping("/")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(String email, String password, HttpServletRequest request, HttpServletResponse Response) {
		boolean loginResponse = securityServiceImp.login(email, password, request, Response);

		if (loginResponse) {
			return "index";
		}
		return "login";

	}
}
