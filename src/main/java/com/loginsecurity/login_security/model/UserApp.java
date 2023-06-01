package com.loginsecurity.login_security.model;

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
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOGIN_USER")
//@NamedQuery(name = "LOGIN_USER.findAll", query = "SELECT LOGIN_USER FROM  s")
public class UserApp {

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

	@Column(name = "type")
	private String type;
	


	
}
