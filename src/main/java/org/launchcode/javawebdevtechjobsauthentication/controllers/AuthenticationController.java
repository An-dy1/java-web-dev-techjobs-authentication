package org.launchcode.javawebdevtechjobsauthentication.controllers;

import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.launchcode.javawebdevtechjobsauthentication.models.data.LoginFormDTO;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {

	// a way to retrieve and set users in the database
	@Autowired
	UserRepository userRepository;

	// the 'key' in the session object that we will set the session id on, the same always
	private static final String userSessionKey = "user";

	// method to set user's id value on the session's "user" key
	public static void setUserInSession(HttpSession session, User user) {
		session.setAttribute(userSessionKey, user.getId());

	}

	// a method to retrieve user info from the session
	public User getUserFromSession(HttpSession session) {

		Integer userId = (Integer) session.getAttribute("user");

		if(userId == null) {
			return null;
		}

		Optional<User> user = userRepository.findById(userId);

		if(user.isEmpty()) {
			return null;
		}

		return user.get();

	}

	@GetMapping("/login")
	public String getLoginPage(Model model) {

		model.addAttribute("title", "Login to the tech jobs portal");
		model.addAttribute("loginDTO", new LoginFormDTO());

		return "/login";

	}

	@PostMapping("/login")
	public String processLoginRequest(@Valid @ModelAttribute LoginFormDTO loginDTO, Errors errors, Model model, HttpServletRequest request) {

		// if the passed in values are not valid, return them to the login form
		if(errors.hasErrors()) {
			model.addAttribute("title", "login");
			return "login";
		}

		// if no errors, then get user from database
		User user = userRepository.findByUsername(loginDTO.getUsername());

		// user could still be null, somehow
		if(user == null) {
			errors.rejectValue("username", "username.doesnotexist", "Sorry, that user is not in our system");
			model.addAttribute("title", "Login");
			return "login";
		}

		// or password might not match the one stored in the database
		if(!user.isMatchingPassword(loginDTO.getPassword())) {
			errors.rejectValue("password", "password.isnotcorrect", "Sorry, that password is not correct");
			model.addAttribute("title", "Login");
			return "login";
		}

		// finally, if there are no errors, the user exists, and the stored password matches the one entered
		// then set the user in the session

		setUserInSession(request.getSession(), user);

		return "redirect:";
	}

	@GetMapping("/register")
	public String getRegistrationForm(Model model) {

	}
}
