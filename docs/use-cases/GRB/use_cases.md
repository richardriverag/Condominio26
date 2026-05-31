# Módulo GRB - Gestión de Usuarios y Administradores

## Índice

- [CU-01 Registrar Cuenta](#cu-01-registrar-cuenta)
- [CU-02 Actualizar Información de Cuenta](#cu-02-actualizar-información-de-cuenta)
- [CU-03 Desactivar Cuenta](#cu-03-desactivar-cuenta)
- [CU-04 Asignar Rol a Usuario](#cu-04-asignar-rol-a-usuario)
- [CU-05 Definir Permisos de Rol](#cu-05-definir-permisos-de-rol)
- [CU-06 Iniciar Sesión](#cu-06-iniciar-sesión)
- [CU-07 Actualizar Perfil](#cu-07-actualizar-perfil)
- [CU-08 Recuperar Contraseña](#cu-08-recuperar-contraseña)
- [CU-09 Enviar Token de Restablecimiento](#cu-09-enviar-token-de-restablecimiento)

---

# CU-01 Registrar Cuenta

## Descripción

Permite al Administrador registrar una nueva cuenta de usuario en el sistema del condominio.

## Actor Principal

Administrador

## Precondiciones

- El Administrador ha iniciado sesión.
- Posee permisos para gestionar usuarios.

## Postcondiciones

- La cuenta queda registrada.

## Escenario Básico

1. El caso de uso comienza cuando el Administrador registra un Usuario.
2. El Administrador proporciona los datos del Usuario nombre y apellido.
3. El Sistema valida el nombre y apellido del Usuario entonces el Administrador proporciona el correo electrónico.
4. El Sistema valida el correo electrónico del Usuario entonces el Administrador proporciona la contraseña.
5. El Sistema registra la cuenta y envía las credenciales al correo del Usuario.
6. El caso de uso termina cuando el Sistema muestra el mensaje "Usuario registrado exitosamente".

## Escenario Alternos

### A1. Correo ya registrado

1. El caso de uso comienza cuando el Administrador registra un Usuario.
2. El Administrador proporciona los datos del Usuario nombre y apellido.
3. El Sistema valida el nombre y apellido del Usuario entonces el Administrador proporciona el correo electrónico.
4. El Sistema detecta que el correo electrónico del Usuario ya está registrado en el sistema.
5. El Sistema muestra el mensaje "El correo electrónico ya está registrado".
6. El caso de uso finaliza sin registrar al usuario.

### A2. Campos incompletos

1. El caso de uso comienza cuando el Administrador registra un Usuario.
2. El Administrador proporciona los datos del Usuario nombre y apellido.
3. El Sistema valida el nombre y apellido del Usuario entonces el Administrador proporciona el correo electrónico.
4. El Sistema valida el correo electrónico del Usuario entonces el Administrador proporciona la contraseña.
5. El Sistema detecta que uno o más campos obligatorios del Usuario están vacíos.
6. El Sistema muestra el mensaje "Todos los campos son obligatorios".
7. El caso de uso finaliza sin registrar al Usuario.

---

# CU-02 Actualizar Información de Cuenta

(Contenido)

---

# CU-03 Desactivar Cuenta

(Contenido)

---

# CU-04 Asignar Rol a Usuario

## Descripción

Permite al Administrador asignar o modificar el rol de seguridad de una cuenta de usuario existente para controlar sus niveles de acceso dentro del sistema.

## Actor Principal

Administrador

## Precondiciones

- El Administrador ha iniciado sesión correctamente.
- El usuario al que se le asignará el rol debe estar previamente registrado en el sistema.

## Postcondiciones

- El rol del usuario es actualizado en la base de datos.
- Los permisos asociados al nuevo rol se aplican de inmediato a la sesión del usuario.

## Escenario Básico

1. El Administrador solicita gestionar los roles de un usuario.
2. El Sistema solicita el identificador (correo o nombre) del usuario.
3. El Administrador ingresa los datos de búsqueda.
4. El Sistema valida la existencia del usuario y muestra su información actual.
5. El Administrador selecciona la opción "Asignar nuevo rol".
6. El Sistema despliega la lista de roles disponibles (ej: Residente, Conserje, Directiva).
7. El Administrador selecciona el rol deseado y confirma la acción.
8. El Sistema guarda los cambios y muestra un mensaje de éxito.

## Escenario Alternos

### A1. Usuario no encontrado

1. En el paso 4, el Sistema no encuentra coincidencias.
2. El Sistema muestra un mensaje de error: "Usuario inexistente".
3. El flujo regresa al punto de búsqueda o finaliza.

### A2. El usuario ya posee ese rol

1. En el paso 7, el Administrador selecciona un rol que el usuario ya tiene asignado.
2. El Sistema informa que no hubo cambios necesarios.
3. El caso de uso finaliza.

---

# CU-05 Definir Permisos de Rol

(Contenido)

---

# CU-06 Iniciar Sesión

## Descripción

Permite al Usuario o Administrador autenticarse en el sistema del condominio.

## Actor Principal

Usuario, Administrador

## Precondiciones

- El Actor tiene una cuenta registrada.
- La cuenta no se encuentra desactivada.

## Postcondiciones

- El Actor inicia sesión en el sistema.

## Escenario Básico

1. El caso de uso comienza cuando el Actor inicia sesión.
2. El Actor proporciona el correo electrónico.
3. El Sistema valida el correo electrónico entonces el Actor proporciona la contraseña.
4. El Sistema valida la contraseña y el estado de la cuenta.
5. El caso de uso termina cuando el Sistema muestra el mensaje "Sesión iniciada exitosamente".

## Escenarios Alternos

### A1. Credenciales incorrectas

1. El caso de uso comienza cuando el Actor inicia sesión.
2. El Actor proporciona el correo electrónico.
3. El Sistema valida el correo electrónico entonces el Actor proporciona la contraseña.
4. El Sistema detecta que el correo electrónico o la contraseña son incorrectos.
5. El Sistema muestra el mensaje "Credenciales incorrectas".
6. El caso de uso finaliza sin iniciar sesión.

### A2. Cuenta desactivada

1. El caso de uso comienza cuando el Actor inicia sesión.
2. El Actor proporciona el correo electrónico.
3. El Sistema valida el correo electrónico entonces el Actor proporciona la contraseña.
4. El Sistema detecta que la cuenta se encuentra desactivada.
5. El Sistema muestra el mensaje "La cuenta se encuentra desactivada".
6. El caso de uso finaliza sin iniciar sesión.

### A3. Campos incompletos

1. El caso de uso comienza cuando el Actor inicia sesión.
2. El Actor proporciona el correo electrónico.
3. El Sistema valida el correo electrónico entonces el Actor proporciona la contraseña.
4. El sistema detecta que uno o más campos obligatorios están vacíos.
5. El Sistema muestra el mensaje "Todos los campos son obligatorios".
6. El caso de uso finaliza sin iniciar sesión.

---

# CU-07 Actualizar Perfil

(Contenido)

---

# CU-08 Recuperar Contraseña

(Contenido)

---

# CU-09 Enviar Token de Restablecimiento

## Descripción

Permite al Sistema generar y enviar un código de seguridad para la recuperación de una cuenta.

## Actor Principal

Sistema

## Precondiciones

- Se ha solicitado la recuperación de contraseña de una cuenta existente.

## Postcondiciones

- El token de restablecimiento es enviado al correo del usuario.

## Escenario Básico

1. El caso de uso comienza cuando el Sistema aprueba la solicitud de recuperación de contraseña.
2. El Sistema genera el token temporal de recuperación.
3. El Sistema asocia el token generado a la cuenta correspondiente.
4. El Sistema envía el mensaje con el token al correo electrónico registrado.
5. El caso de uso termina cuando el Sistema muestra el mensaje "Token enviado exitosamente".

## Escenarios Alternos

### A1. Falla de conexión con el servidor

1. El caso de uso comienza cuando el Sistema aprueba la solicitud de recuperación de contraseña.
2. El Sistema genera el token temporal de recuperación.
3. El Sistema asocia el token generado a la cuenta correspondiente.
4. El Sistema detecta un error de conexión al intentar enviar el correo electrónico.
5. El Sistema muestra el mensaje "Error al enviar el token, intente más tarde".
6. El caso de uso finaliza sin enviar el token.

### A2. Límite de intentos excedido

1. El caso de uso comienza cuando el Sistema aprueba la solicitud de recuperación de contraseña.
2. El Sistema detecta que se ha superado el límite de intentos permitidos para generar tokens.
3. El Sistema muestra el mensaje "Límite de envíos excedido, espere un momento".
4. El caso de uso finaliza sin enviar el token.
