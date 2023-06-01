package com.loginsecurity.login_security.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

	/**
	 * Devuelve una lista de todos los usuarios.
	 *
	 * @return Lista de usuarios
	 */
	@Override
	public List<UserApp> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Busca un usuario por su nombre de usuario.
	 *
	 * @param username Nombre de usuario
	 * @return Usuario encontrado (si existe)
	 */
	@Override
	public Optional<UserApp> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * Busca un usuario por su ID.
	 *
	 * @param id ID del usuario
	 * @return Usuario encontrado (si existe)
	 */
	@Override
	public Optional<UserApp> findById(long id) {
		return userRepository.findById(id);
	}

	/**
	 * Elimina un usuario por su ID.
	 *
	 * @param id ID del usuario a eliminar
	 */
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	/**
	 * Agrega un nuevo usuario.
	 *
	 * @param userApp Usuario a agregar
	 * @return Usuario agregado exitosamente
	 * @throws IllegalArgumentException si el nombre de usuario ya está en uso
	 */
	@Override
	public UserApp addUser(UserApp userApp) {
		byte[] salt = generateSalt(17);
		String saltHex = bytesToHex(salt);

		Optional<UserApp> existingUseruserName = userRepository.findByUsername(userApp.getUsername().trim());
		if (existingUseruserName.isPresent()) {
			throw new IllegalArgumentException("El nombre de usuario ya está en uso");
		}
		String hashedPassword = hashPassword(userApp.getPassword(), salt);

		userApp.setPassword(hashedPassword);
		userApp.setUserKey(saltHex);
		UserApp newUserApp = userRepository.save(userApp);
		return newUserApp;
	}

	/**
	 * Restablece la contraseña de un usuario a un valor en blanco.
	 *
	 * @param userId ID del usuario
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
	 * Encripta una contraseña utilizando el algoritmo PBKDF2.
	 *
	 * @param password Contraseña a encriptar
	 * @param salt     Sal utilizada en la encriptación
	 * @return Contraseña encriptada
	 */
	@Override
	public String hashPassword(String password, byte[] salt) {
		Hash hash = Password.hash(password).addSalt(salt).withPBKDF2();
		return hash.getResult();
	}

	/**
	 * Cambia la contraseña de un usuario.
	 *
	 * @param userId  ID del usuario
	 * @param userApp Usuario con la nueva contraseña
	 */
	@Override
	public void changePassword(Long userId, UserApp userApp) {
		UserApp optionalUserApp = userRepository.findById(userId).orElseThrow(
				() -> new IllegalArgumentException("No se encontró ningún usuario con el ID proporcionado."));

		String newPassword = userApp.getPassword();
		byte[] salt = hexToBytes(optionalUserApp.getUserKey());
		String hashedPassword = hashPassword(newPassword, salt);
		optionalUserApp.setPassword(hashedPassword);

		userRepository.save(optionalUserApp);
	}

	/**
	 * Verifica si una contraseña coincide con un hash.
	 *
	 * @param password Contraseña a verificar
	 * @param hash     Hash de referencia
	 * @return `true` si la contraseña coincide, `false` en caso contrario
	 */
	public boolean verifyPassword(String password, String hash) {
		boolean verified = Password.check(password, hash).withArgon2();
		return false;
	}

	/**
	 * Realiza el proceso de inicio de sesión para un usuario.
	 *
	 * @param username Nombre de usuario
	 * @param password Contraseña
	 * @return Usuario si el inicio de sesión fue exitoso, o un `Optional` vacío
	 */
	public Optional<UserApp> login(String username, String password) {
		Optional<UserApp> user = userRepository.findByUsername(username);
		Optional<UserApp> userR = null;

		System.out.println("entre" + password);
		if (user.isPresent()) {
			byte[] saltByte = hexToBytes(user.get().getUserKey());

			String pass = password;
			if (password != "") {
				pass = hashPassword(password, saltByte);
			}

			UserApp u = user.get();
			if (user.get().getPassword().equals(pass)) {
				userR = user;
				u.setLastLogin(LocalDateTime.now());
				userRepository.save(u);
			}

		}
		return userR;
	}

	/**
	 * Genera una sal aleatoria como una matriz de bytes.
	 *
	 * @param length Longitud de la sal
	 * @return Sal generada
	 */
	private static byte[] generateSalt(int length) {
		byte[] salt = new byte[length];
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.nextBytes(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return salt;
	}

	/**
	 * Convierte una cadena hexadecimal en una matriz de bytes.
	 *
	 * @param hex Cadena hexadecimal
	 * @return Matriz de bytes
	 */
	private static byte[] hexToBytes(String hex) {
		int length = hex.length();
		byte[] bytes = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return bytes;
	}

	/**
	 * Convierte una matriz de bytes en una cadena hexadecimal.
	 *
	 * @param bytes Matriz de bytes
	 * @return Cadena hexadecimal
	 */
	private static String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}
}
