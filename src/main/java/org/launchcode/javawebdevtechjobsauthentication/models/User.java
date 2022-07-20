package org.launchcode.javawebdevtechjobsauthentication.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User extends AbstractEntity {

	@NotNull
	private String username;

	@NotNull
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
