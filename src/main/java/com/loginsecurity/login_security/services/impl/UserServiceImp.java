package com.loginsecurity.login_security.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.loginsecurity.login_security.model.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<UserApp> getAllUsers() {
		return userRepository.findAll();
	}

	// Buscar por nombre un usuario
	@Override
	public Optional<UserApp> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	


	// Listar por id
	@Override
	public Optional<UserApp> findById(long id) {
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
	 * @param userApp
	 * @return la creacion de un nuevo usuario
	 */
	@Override
	public UserApp addUser(UserApp userApp) {

		Optional<UserApp> existingUseruserName = userRepository.findByUsername(userApp.getUsername().trim());
		if (existingUseruserName.isPresent()) {
			throw new IllegalArgumentException("El nombre de usuario ya esta en uso");
		}
		String hashedPassword = hashPassword(userApp.getPassword());
		userApp.setPassword(hashedPassword);

		UserApp newUserApp = userRepository.save(userApp);
		return newUserApp;

	}

	/**
	 * Poner en blanco la contraseña de un usuario
	 * 
	 * @param userId
	 * 
	 */
	@Override

	public void resetUserPassword(Long userId) {
		UserApp userApp = userRepository.findById(userId).orElse(null);
		if (userApp != null) {
			userApp.setPassword("");
			userRepository.save(userApp);
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

		Hash hash = Password.hash(password).addSalt("abc123").withPBKDF2();

		return hash.getResult();
	}
	
	
	

	@Override
	public void changePassword(Long userId, UserApp userApp) {
	    UserApp optionalUserApp = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No se encontró ningún usuario con el ID proporcionado."));

	    String newPassword = userApp.getPassword();
	    String hashedPassword = hashPassword(newPassword);
	    optionalUserApp.setPassword(hashedPassword);

	    userRepository.save(optionalUserApp);
	}
	
	//Revisar
	
	public boolean verifyPassword(String password, String hash) {
		boolean verified= Password.check(password, hash).withArgon2();
		return false;
		
	}


	public Optional<UserApp> login(String username, String password){
		Optional<UserApp> user = userRepository.findByUsername(username);
		Optional<UserApp> userR = null;

		String pass = hashPassword(password);
		System.out.println("Aca1");
		if(user.isPresent()){
			UserApp u = user.get();
			System.out.println("Aca2");
			System.out.println("pass "+pass +" /userp "+user.get().getPassword());
			if(user.get().getPassword().equals(pass)){
				System.out.println("Aca3");
				userR = user;
				u.setLastLogin(LocalDateTime.now());
				userRepository.save(u);
			}
		}
		return userR;
	}



}
