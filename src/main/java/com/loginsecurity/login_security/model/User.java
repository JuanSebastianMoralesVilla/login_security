package com.loginsecurity.login_security.model;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDateTime;



import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "LOGIN_USER")
//@NamedQuery(name = "LOGIN_USER.findAll", query = "SELECT LOGIN_USER FROM  s")
public class User implements Serializable {

	
	public enum typeUser{
	
		ADMIN, 
		OPER
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	

	
}
