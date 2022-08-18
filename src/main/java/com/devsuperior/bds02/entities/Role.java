package com.devsuperior.bds02.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_role")
public class Role {

	@Id
	private Long id;
	private String authority;
	
	public Role() {
		
	}
	public Role(Long id, String authority) {
		super();
		this.id = id;
		this.authority = authority;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}