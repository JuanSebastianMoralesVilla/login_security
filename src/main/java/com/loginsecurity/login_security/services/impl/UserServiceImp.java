package com.loginsecurity.login_security.services.impl;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
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
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}

	/**
	 * Eliminar usuario
	 */
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
	 * Encripta una contraseña utilizando el algoritmo PBKDF2
	 * 
	 * @param password Contraseña a encriptar
	 * 
	 * @return contraseña encriptada
	 */
	@Override
	public String hashPassword(String password) {

		Hash hash = Password.hash(password).addRandomSalt(3).withPBKDF2();

		return hash.getResult();
	}
	
	
	

	@Override
	public void changePassword(Long userId, User user) {
	    User optionalUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No se encontró ningún usuario con el ID proporcionado."));

	    String newPassword = user.getPassword();
	    String hashedPassword = hashPassword(newPassword);
	    optionalUser.setPassword(hashedPassword);

	    userRepository.save(optionalUser);
	}
	
	//Revisar
	
	public boolean verifyPassword(String password, String hash) {
		boolean verified= Password.check(password, hash).withArgon2();
		return false;
		
	}

	@Override
	public String getLastLogin(Long userId){
		if(!userRepository.findByLastLogin(userId).isEmpty()){
			if(userRepository.findByLastLogin(userId).get().getLastLogin() != null) {
				return userRepository.findByLastLogin(userId).get().getLastLogin().toString();
			}else{
				return null;
			}
		}else{
			return "";
		}
	}


}
