package com.loginsecurity.login_security.services.impl;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.loginsecurity.login_security.model.User;
import com.loginsecurity.login_security.repository.UserRepository;
import com.loginsecurity.login_security.services.inter.UserService;
import com.password4j.Hash;
import com.password4j.Password;

@Service

public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	// Listar todos los usuarios
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// Buscar por nombre un usuario
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	// Listar por id
	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	// Eliminar usuario
	@Override
	public void deleteUser(Long id) {

		userRepository.deleteById(id);

	}

	/**
	 * Agregar usuario
	 * 
	 * @param user
	 * @return la creacion de un nuevo usuario
	 */
	@Override
	public User addUser(User user) {

		User existingUseruserName = userRepository.findByUsername(user.getUsername().trim());
		if (existingUseruserName != null) {
			throw new IllegalArgumentException("El nombre de usuario ya esta en uso");
		}
		String hashedPassword = hashPassword(user.getPassword());
		user.setPassword(hashedPassword);

		User newUser = userRepository.save(user);
		return newUser;

	}

	/**
	 * Poner en blanco la contraseña de un usuario
	 * 
	 * @param userId
	 * 
	 */
	@Override

	public void resetUserPassword(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			user.setPassword("");
			userRepository.save(user);
		}
	}

	/**
	 * 
	 * @param userId
	 * @param newPassword
	 * 
	 */
	@Override
	public void changePassword(Long userId, String newPassword) {

		User user = userRepository.findById(userId).get();
		if (user != null) {
			String hashedPassword = hashPassword(newPassword);
			user.setPassword(hashedPassword);

			userRepository.save(user);

		}

	}

	/**
	 * Encripta una contraseña utilizando el algoritmo PBKDF2
	 * 
	 * @param password Contraseña a encriptar
	 * 
	 * @return contraseña encriptada
	 */
	@Override
	public String hashPassword(String password) {
		
		Hash hash = Password.hash(password).addRandomSalt().withPBKDF2();

		return hash.getResult();
	}

}
