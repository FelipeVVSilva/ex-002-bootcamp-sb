package com.devsuperior.bds02.dto;

import java.util.HashSet;
import java.util.Set;

import com.devsuperior.bds02.entities.Role;
import com.devsuperior.bds02.entities.User;
import com.devsuperior.bds02.services.validation.EventInsertValid;

public class UserDTO {

	private Long id;
	private String email;
	private String password;
	private Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO(Long id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}
	public UserDTO(User entity) {
		super();
		this.id = entity.getId();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
	}
	public UserDTO(User entity, Set<Role> roles ) {
		super();
		this.id = entity.getId();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
		roles.forEach(role -> this.roles.add(new RoleDTO(role)));
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<RoleDTO> getRoles() {
		return roles;
	}
	
}
