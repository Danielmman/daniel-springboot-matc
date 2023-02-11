package com.ems.model.sub;

import javax.persistence.*;

import com.ems.customdt.AuthRole;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private AuthRole name;

	public Role() {

	}

	public Role(AuthRole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AuthRole getName() {
		return name;
	}

	public void setName(AuthRole name) {
		this.name = name;
	}
}