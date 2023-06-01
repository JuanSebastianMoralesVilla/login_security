package com.loginsecurity.login_security.repository;

import java.util.Optional;

import com.loginsecurity.login_security.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {

	// Buscar usuario por id
	Optional<UserApp> findById(Long id);

	// Buscar usuario por nombre
	Optional<UserApp>  findByUsername(String username);

	// Buscar usuario por ultimo login
	@Query("SELECT u.lastLogin FROM UserApp u WHERE u.id = ?1")
	Optional<UserApp> findByLastLogin(long id);

}
