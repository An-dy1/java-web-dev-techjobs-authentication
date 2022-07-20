package org.launchcode.javawebdevtechjobsauthentication.controllers;

import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.launchcode.javawebdevtechjobsauthentication.models.data.LoginFormDTO;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
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

		// perhaps unnecessary
//		if(session.getAttribute("user") != null) {
//			return;
//		}

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
}
