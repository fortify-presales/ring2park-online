package com.dps.ring2park.security;

public class LoginStatus {

	private final boolean loggedIn;
	private final String username;
	private final String message;

	public LoginStatus(boolean loggedIn, String username, String message) {
		this.loggedIn = loggedIn;
		this.username = username;
		this.message = message;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}
	
}
