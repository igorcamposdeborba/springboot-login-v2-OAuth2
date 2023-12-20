package com.devsuperior.bds04.dto.login;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.devsuperior.bds04.entities.login.Role;
import com.devsuperior.bds04.entities.login.User;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	private Set<Role> roles = new HashSet<Role>();
	
	public UserDTO() {}

	public UserDTO(Long id, String firstName, String lastName, String email, String password, Set<Role> roles) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	public UserDTO(User userEntity) {
		this.id = userEntity.getId();
		this.firstName = userEntity.getFirstName();
		this.lastName = userEntity.getLastName();
		this.email = userEntity.getEmail();
	}
	
	
}
