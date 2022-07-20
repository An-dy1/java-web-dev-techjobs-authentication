package org.launchcode.javawebdevtechjobsauthentication.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User extends AbstractEntity {

	@NotNull
	@NotBlank
	@Size(min = 3, max = 20, message="username must be between 3 and 20 characters")
	private String username;

	@NotNull
	@NotBlank
	@Size(min = 5, max = 35, message="password must be between 5 and 35 characters")
	private String passwordHash;

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public User(String username, String password) {
		this.username = username;
		this.passwordHash = encoder.encode(password);
	}

	public User() {}

	public String getUsername() {
		return username;
	}

	public boolean isMatchingPassword(String password) {

		return encoder.matches(password, passwordHash);

	}

}
