package com.loginsecurity.login_security.services.inter;

import com.loginsecurity.login_security.model.UserApp;

import java.util.List;
import java.util.Optional;

public interface UserService {

	List<UserApp> getAllUsers();

	Optional<UserApp> findByUsername(String username);

	Optional<UserApp> findById(long id);

	void deleteUser(Long id);

	/**
	 * Agregar usuario
	 * 
	 * @param userApp
	 * @return la creacion de un nuevo usuario
	 */
	UserApp addUser(UserApp userApp);

	/**
	 * Poner en blanco la contraseña de un usuario
	 * 
	 * @param userId
	 * 
	 */
	void resetUserPassword(Long userId);


	/**
	 * Encripta una contraseña utilizando el algoritmo PBKDF2
	 * 
	 * @param password Contraseña a encriptar
	 * 
	 * @return contraseña encriptada
	 */
	String hashPassword(String password);
	
	/**
	 * 
	 * @param userId
	 * @param newPassword
	 * 
	 */


	void changePassword(Long userId, UserApp userApp);


	Optional<UserApp> login(String user, String password);


	






}
