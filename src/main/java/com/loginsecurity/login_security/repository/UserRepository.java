package com.loginsecurity.login_security.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loginsecurity.login_security.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// Buscar usuario por id
	Optional<User> findById(Long id);

	// Buscar usuario por nombre
	User findByUsername(String username);

	// Buscar usuario por ultimo login
	User findByLastLogin(LocalDateTime lastLogin);

}
