package com.loginsecurity.login_security.model;

import java.io.Serializable;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOGIN_USER")
//@NamedQuery(name = "LOGIN_USER.findAll", query = "SELECT LOGIN_USER FROM  s")
public class User {

	/**
	public enum typeUser{
	
		ADMIN, 
		OPER
	}
	**/
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	

	
}
