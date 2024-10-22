package com.sorbonne.safetyline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used to fetch data from client side for the creation of an account
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private String username;
	private String password;
	private boolean admin;
	private String lastName;
	private String firstName;
	
	// Used for password modification
	private String newPassword;
	private String oldPassword;
	
	public UserDTO(String username, String firstName, String lastName, Boolean admin) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.admin = admin;
	}

}
