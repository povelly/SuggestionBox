package com.sorbonne.safetyline.model;

/**
 * Used to fetch data from client side for the creation of an account
 */
public class UserCreation {

	private String userId;
	private boolean isAdmin;
	private String lastName;
	private String firstName;

	public UserCreation(String userId, boolean isAdmin, String lastName, String firstName) {
		super();
		this.userId = userId;
		this.isAdmin = isAdmin;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
