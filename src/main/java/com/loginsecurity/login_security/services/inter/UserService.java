package com.loginsecurity.login_security.services.inter;

import java.util.List;
import java.util.Optional;

import com.loginsecurity.login_security.model.User;

public interface UserService {

	List<User> getAllUsers();

	User findByUsername(String username);

	Optional<User> findById(Long id);

	void deleteUser(Long id);

	/**
	 * Agregar usuario
	 * 
	 * @param user
	 * @return la creacion de un nuevo usuario
	 */
	User addUser(User user);

	/**
	 * Poner en blanco la contraseña de un usuario
	 * 
	 * @param userId
	 * 
	 */
	void resetUserPassword(Long userId);

	/**
	 * 
	 * @param userId
	 * @param newPassword
	 * 
	 */
	void changePassword(Long userId, String newPassword);

	/**
	 * Encripta una contraseña utilizando el algoritmo PBKDF2
	 * 
	 * @param password Contraseña a encriptar
	 * 
	 * @return contraseña encriptada
	 */
	String hashPassword(String password);

}
