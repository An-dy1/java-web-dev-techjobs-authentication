package org.launchcode.javawebdevtechjobsauthentication;

import org.launchcode.javawebdevtechjobsauthentication.controllers.AuthenticationController;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationController authController;

	private static final List<String> whiteList = Arrays.asList("/login", "/register", "/logout", "/css");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

		HttpSession session = request.getSession();

		if(session.getAttribute("user") != null) {
			return true;
		}

		response.sendRedirect("/login");
		return false;

	}

	private boolean isWhitelisted(HttpServletRequest request) {
		if(whiteList.contains(request.getRequestURI())){
			return true;
		}
		return false;

	}
}
