package com.loginsecurity.login_security.repositories;

import java.sql.Timestamp;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loginsecurity.login_security.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

	// Buscar usuario por id
		Optional<User> findById(Long id);

	// Buscar usuario por nombre
		User findByUsername(String username);

	//Buscar usuario por ultimo login
		User findByLastLogin(Timestamp lastLogin);
    

}
