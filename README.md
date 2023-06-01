# Proyecto final de Seguridad: Funcionalidad de login para una plataforma
- Autores: Sebastian Morales, Alejandro Arce, Gonzalo De Varona
- Universidad: Universidad ICESI
- Facultad: Facultad de Ingeniería, Diseño y Ciencias Aplicadas
- Carrera: Ingeniería de Sistemas
- Ubicación: Cali
- Año: 2023

## Frontend: https://github.com/gonzalodevarona/FrontendSecurity.git

![08d1163f-bef8-42a5-ad37-edcda6a04379](https://github.com/JuanSebastianMoralesVilla/login_security/assets/54720084/a77677dc-7cf0-4c6d-8439-d59dfece79ec)


# ¿Cómo hicimos el programa?

Para poder construir esta pequeña aplicación empleamos una arquitectura API Rest, construimos un backend usando el framework de Spring, un frontend usando JavaScript y librerías como React y Material UI. Como base de datos empleamos H2, una base de datos relacional hecha en Java que se acopla muy bien a aplicaciones hechas con Spring.

En el desarrollo del back,  usamos una arquitectura MVC basado en microservicios que conllevan la lógica del programa , estos servicios exponen funcionalidades como la gestión de usuario , así mismo la configuración de protección y manejo de contraseñas. Posteriormente “Password4j” que es una biblioteca criptográfica con fluidez en Java especializada en el cifrado de contraseñas con diferentes funciones de derivación de claves (KDF) y funciones de hash criptográfico (CHF). Dicha herramienta nos facilito la configuración y control de las contraseñas de nuestro sistema.

Por parte del frontend, construimos todo basado en componentes y JavaScript sin necesidad de incorporar HTML o CSS directamente, para construir el proyecto usamos Vite puesto que su competidor directo, create-react-app cada vez es menos usado por distintas razones técnicas. Para el formulario de login y de registro usamos una plantilla proporcionada por Material UI usando sus componentes hechos (algo así como bootstrap). Empleamos también la librería de sweetalert2 para hacer un pop-up y así cambiar la contraseña de un usuario. Finalmente la conexión con el backend se hizo empleando la librería de axios y así poder hacer los llamados a los endpoints correspondientes.

# Dificultades


En el frontend ya teníamos algo de experiencia haciendo aplicaciones en JavaScript y React, por ende fue un reto buscar implementar y hacer evidentes las buenas prácticas de programación para hacer código limpio, mantenible y con bajo acoplamiento, lo cual no suele ser tan sencillo de hacer. También nos puso a pensar cómo manejar la restricción de los roles en el frontend, por ejemplo que un usuario no pudiera acceder a la vista del administrador. 

Por otro lado, la parte de la lógica de la aplicación fue sencilla porque ya hemos realizado varios proyectos con Spring. Sin embargo, ocurrieron dificultades al poder encriptar las contraseñas, debido a que para dar más seguridad se emplea el concepto de salt para poder hacer hashes más fuertes. No obstante, en principio utilizamos un salt fijo para hashing, pero nos dimos cuenta de que es muy inseguro ya que dos contraseñas pueden tener el mismo hash. Para resolver este problema usamos un salt aleatorio para que el texto plano de dos contraseñas iguales sus hashes sean distintos. De modo que la salt se guarda en la base de datos como hexadecimal para luego ser usada en bytes en el hash de la contraseña.

# Palabras claves

## Salt:
en el contexto de la encriptación de contraseñas, un salt (salto en español) es un valor aleatorio único que se agrega a la contraseña antes de realizar el proceso de hash. El objetivo principal de un salt es aumentar la seguridad de las contraseñas almacenadas y dificultar los ataques de fuerza bruta y la generación de tablas arco iris (rainbow tables).

Cuando se crea un hash de contraseña, el salt se concatena con la contraseña y luego se aplica una función de hash, como Argon2, PBKDF2 o bcrypt. El salt garantiza que aunque dos usuarios tengan la misma contraseña, sus hashes de contraseña sean diferentes debido a los valores de salt únicos. Esto significa que incluso si un atacante obtiene acceso a los hashes de contraseña almacenados en una base de datos, no podrá deducir las contraseñas originales fácilmente.

En resumen, el salt es un valor aleatorio único utilizado en la encriptación de contraseñas para aumentar la seguridad y dificultar los ataques. Al agregar un salt a las contraseñas antes de realizar el hash, se asegura que los hashes sean únicos y no se puedan utilizar tablas arco iris para revertir la contraseña original.

## PBKDF2: 
PBKDF2 (Password-Based Key Derivation Function 2) es una función de derivación de claves basada en contraseñas. Su objetivo principal es aumentar la seguridad de las contraseñas almacenadas al aplicar una función criptográfica iterativa y lenta sobre la contraseña original y una "sal" aleatoria.

PBKDF2 se utiliza comúnmente para almacenar contraseñas de forma segura mediante la generación de una "hash" o resumen criptográfico único y resistente a colisiones. Esta función se utiliza para proteger las contraseñas almacenadas en bases de datos, evitando que un atacante obtenga las contraseñas originales en caso de una brecha de seguridad.

La fortaleza de PBKDF2 radica en su capacidad para aumentar el tiempo de cómputo necesario para generar una hash a partir de una contraseña. Esto dificulta los ataques de fuerza bruta y de diccionario, ya que incluso contraseñas débiles requieren un tiempo considerable para ser procesadas. PBKDF2 utiliza un número configurable de iteraciones para aumentar la resistencia a estos ataques.


# Documentación Backend

A continuación,se explica cada uno de los métodos presentes en esta implementación:

## Métodos de la implementación

1. Método `getAllUsers()`
   - **Descripción**: Devuelve una lista de todos los usuarios.
   - **Implementación**: Llama al método `findAll()` del repositorio `UserRepository` para obtener todos los usuarios.

2. Método `findByUsername(String username)`
   - **Descripción**: Busca un usuario por su nombre de usuario.
   - **Implementación**: Llama al método `findByUsername(username)` del repositorio `UserRepository` para buscar el usuario por su nombre de usuario y devuelve un `Optional` que puede contener el usuario encontrado o estar vacío.

3. Método `findById(long id)`
   - **Descripción**: Busca un usuario por su ID.
   - **Implementación**: Llama al método `findById(id)` del repositorio `UserRepository` para buscar el usuario por su ID y devuelve un `Optional` que puede contener el usuario encontrado o estar vacío.

4. Método `deleteUser(Long id)`
   - **Descripción**: Elimina un usuario por su ID.
   - **Implementación**: Llama al método `deleteById(id)` del repositorio `UserRepository` para eliminar el usuario por su ID.

5. Método `addUser(UserApp userApp)`
   - **Descripción**: Agrega un nuevo usuario.
   - **Implementación**: Realiza las siguientes operaciones:
     - Genera una sal aleatoria llamando al método `generateSalt()`.
     - Verifica si ya existe un usuario con el mismo nombre de usuario llamando al método `findByUsername()` del repositorio `UserRepository`.
     - Si ya existe un usuario con el mismo nombre de usuario, lanza una excepción.
     - Calcula el hash de la contraseña del usuario llamando al método `hashPassword()` y actualiza la contraseña y la clave del usuario.
     - Guarda el nuevo usuario en el repositorio llamando al método `save()` del repositorio `UserRepository` y devuelve el nuevo usuario guardado.

6. Método `resetUserPassword(Long userId)`
   - **Descripción**: Restablece la contraseña de un usuario a un valor en blanco.
   - **Implementación**: Busca el usuario por su ID llamando al método `findById()` del repositorio `UserRepository`. Si se encuentra el usuario, se establece su contraseña en blanco y se guarda nuevamente en el repositorio.

7. Método `hashPassword(String password, byte[] salt)`
   - **Descripción**: Encripta una contraseña utilizando el algoritmo PBKDF2.
   - **Implementación**: Utiliza la clase `Password` para realizar el hash de la contraseña proporcionada utilizando el algoritmo PBKDF2 y la sal proporcionada. Devuelve el resultado del hash.

8. Método `changePassword(Long userId, UserApp userApp)`
   - **Descripción**: Cambia la contraseña de un usuario.
   - **Implementación**: Realiza las siguientes operaciones:
     - Busca el usuario por su ID llamando al método `findById()` del repositorio `UserRepository`. Si no se encuentra el usuario, se lanza una excepción.
     - Obtiene la nueva contraseña del objeto `userApp` proporcionado.
     - Obtiene la clave del usuario desde la base de datos y la convierte en una matriz de bytes.
     - Calcula el nuevo hash de la contraseña llamando al método `hashPassword()` y actualiza la contraseña del usuario.
     - Guarda el usuario actualizado en el repositorio llamando al método `save()` del repositorio `UserRepository`.

9. Método `verifyPassword(String password

, String hash)`
   - **Descripción**: Verifica si una contraseña coincide con un hash.
   - **Implementación**: Utiliza la clase `Password` para verificar si la contraseña proporcionada coincide con el hash proporcionado utilizando el algoritmo Argon2. Devuelve `true` si la contraseña coincide y `false` en caso contrario.

10. Método `login(String username, String password)`
    - **Descripción**: Realiza el proceso de inicio de sesión para un usuario.
    - **Implementación**: Realiza las siguientes operaciones:
      - Busca un usuario por su nombre de usuario llamando al método `findByUsername()` del repositorio `UserRepository`.
      - Si se encuentra el usuario, calcula el hash de la contraseña proporcionada utilizando la clave del usuario almacenada en la base de datos.
      - Si la contraseña coincide, actualiza la última fecha de inicio de sesión del usuario y guarda el usuario en el repositorio.
      - Devuelve un `Optional` que puede contener el usuario si el inicio de sesión fue exitoso o estar vacío en caso contrario.

## Métodos de utilidad

- `generateSalt(int length)`: Genera una sal aleatoria como una matriz de bytes.
- `hexToBytes(String hex)`: Convierte una cadena hexadecimal en una matriz de bytes.
- `bytesToHex(byte[] bytes)`: Convierte una matriz de bytes en una cadena hexadecimal.

# Conclusiones

A pesar que no se pide una aplicación muy robusta, requiere de pensar en hacer un desarrollo seguro de forma completa, que sea seguro desde el frontend hasta el backend y que se implementen buenas prácticas en ambas capas para hacer evidente código mantenible y de alta calidad. 

Una de las conclusiones en este proyecto es todo lo que implica cifrar y verificar una contraseña para el almacenamiento en una base de datos. Un factor importante en las aplicaciones que usamos y que nos hace tomar precaución donde almacenamos nuestros datos para evitar posibles vulnerabilidades o robo de información.

El uso de técnicas como el empleo de un salt aleatorio y la función PBKDF2 para la encriptación de contraseñas muestra un enfoque responsable y seguro en la protección de la información sensible de los usuarios. Además, la implementación de una capa de seguridad en el frontend para restringir el acceso a determinadas vistas o funcionalidades demuestra un cuidado adicional en la protección de los datos.

En resumen, este proyecto resalta la importancia de considerar la seguridad en todas las etapas del desarrollo de una aplicación, desde el diseño hasta la implementación. El enfoque en la seguridad y la implementación de buenas prácticas garantizan la protección de la información y brindan confianza a los usuarios.

