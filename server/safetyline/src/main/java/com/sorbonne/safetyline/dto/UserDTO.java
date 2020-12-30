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

	private String userId;
	private String password;
	private boolean isAdmin;
	private String lastName;
	private String firstName;

}
