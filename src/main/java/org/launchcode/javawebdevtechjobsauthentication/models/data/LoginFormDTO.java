package org.launchcode.javawebdevtechjobsauthentication.models.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {

	@NotNull
	@NotBlank
	@Size(min = 3, max = 20, message="username must be between 3 and 20 characters")
	private String username;

	@NotNull
	@NotBlank
	@Size(min = 5, max = 35, message="password must be between 5 and 35 characters")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//	private String repeatPassword;

//	public LoginFormDTO(String username, String password, String repeatPassword) {
//		this.username = username;
//		this.password = passwordsMatch(password, repeatPassword);
//	}
//
//	public String passwordsMatch(String password, String repeatPassword) {
//		if(password.equals(repeatPassword)) {
//			return password;
//		}
//		return null;
//	}
}
