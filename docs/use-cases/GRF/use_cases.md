# Módulo GRF - Comunicación

## Índice

- [CU-01 Enviar Mensaje a Residentes](#cu-01-enviar-mensaje-a-residentes)
- [CU-02 Enviar Comunicado a Trabajadores](#cu-02-enviar-comunicado-a-trabajadores)
- [CU-03 Enviar Mensaje Global](#cu-03-enviar-mensaje-global)
- [CU-04 Crear Anuncio General](#cu-04-crear-anuncio-general)
- [CU-05 Modificar Anuncio General](#cu-05-modificar-anuncio-general)
- [CU-06 Eliminar Anuncio General](#cu-06-eliminar-anuncio-general)
- [CU-07 Crear Reporte de Comunicación](#cu-07-crear-reporte-de-comunicación)
- [CU-08 Consultar Historial de Mensajes](#cu-08-consultar-historial-de-mensajes)
- [CU-09 Registrar Historial de Comunicaciones](#cu-09-registrar-historial-de-comunicaciones)
- [CU-10 Notificar Mensaje Recibido](#cu-10-notificar-mensaje-recibido)

---

# CU-01 Enviar Mensaje a Residentes

## Descripción
Permite al Administrador enviar mensajes dirigidos únicamente a los residentes del condominio, comunicar información relevante relacionada con la administración del condominio.

## Actor Principal
Administrador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir residentes registrados dentro del sistema de gestión del condominio.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra las opciones disponibles de mensajería.
3. El Administrador selecciona la opción de enviar mensaje a residentes.
4. El Administrador redacta el asunto y el contenido del mensaje.
5. El sistema valida que la información ingresada sea correcta.
6. El sistema envía el mensaje a los residentes correspondientes.
7. El sistema registra el envío dentro del historial de comunicaciones.

## Postcondiciones
- El mensaje queda enviado a los residentes correspondientes.
- El mensaje queda almacenado dentro del historial de comunicaciones del sistema.
- El sistema conserva la fecha y hora del envío realizado.

---

# CU-02 Enviar Comunicado a Trabajadores

## Descripción
Permite al Administrador enviar comunicados dirigidos a los trabajadores del condominio.

## Actor Principal
Administrador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir trabajadores registrados dentro del sistema de gestión del condominio.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra las opciones disponibles de comunicación.
3. El Administrador selecciona la opción de enviar comunicado a trabajadores.
4. El Administrador redacta el asunto y el contenido del comunicado.
5. El sistema valida la información ingresada.
6. El sistema envía el comunicado a los trabajadores correspondientes.
7. El sistema registra el comunicado dentro del historial de comunicaciones.

## Postcondiciones
- El comunicado queda enviado a los trabajadores correspondientes.
- El comunicado queda almacenado dentro del historial de comunicaciones del sistema.
- El sistema conserva la fecha y hora del envío realizado.

---

# CU-03 Enviar Mensaje Global

## Descripción
Permite al Administrador enviar mensajes generales a residentes y trabajadores del condominio.

## Actor Principal
Administrador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir usuarios registrados dentro del sistema de gestión del condominio.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra las opciones disponibles de mensajería.
3. El Administrador selecciona la opción de enviar mensaje global.
4. El Administrador redacta el asunto y el contenido del mensaje.
5. El sistema valida que la información ingresada sea correcta.
6. El sistema envía el mensaje a todos los usuarios correspondientes.
7. El sistema registra el mensaje global dentro del historial de comunicaciones.

## Postcondiciones
- El mensaje global queda enviado a residentes y trabajadores.
- El mensaje queda almacenado dentro del historial de comunicaciones del sistema.
- El sistema conserva la fecha y hora del envío realizado.

---

# CU-04 Crear Anuncio General

## Descripción
Permite al Administrador publicar anuncios generales para los usuarios del condominio.

## Actor Principal
Administrador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la opción de gestión de anuncios generales.
3. El Administrador selecciona la opción de crear anuncio general.
4. El Administrador ingresa el título y contenido del anuncio.
5. El sistema valida que la información ingresada sea correcta.
6. El sistema publica el anuncio general.
7. El sistema registra la creación del anuncio dentro del historial de comunicaciones.

## Postcondiciones
- El anuncio general queda publicado dentro del sistema.
- El anuncio queda disponible para los usuarios correspondientes.
- El sistema conserva un registro de la fecha y hora de publicación.

---

# CU-05 Modificar Anuncio General

## Descripción
Permite al Administrador modificar anuncios generales publicados previamente, con el fin de corregir, actualizar o complementar información comunicada a los usuarios del condominio.

## Actor Principal
Administrador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Debe existir al menos un anuncio general registrado dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la lista de anuncios generales registrados.
3. El Administrador selecciona el anuncio que desea modificar.
4. El Administrador edita el título o contenido del anuncio.
5. El sistema valida los cambios realizados.
6. El sistema actualiza el anuncio general.
7. El sistema registra la modificación realizada.

## Postcondiciones
- El anuncio general queda actualizado correctamente.
- El sistema guarda los cambios realizados.
- El sistema conserva un registro de la fecha y hora de modificación.

---

# CU-06 Eliminar Anuncio General

## Descripción
Permite al Administrador eliminar anuncios generales publicados previamente cuando la información ya no sea necesaria, esté desactualizada o no deba permanecer visible para los usuarios.

## Actor Principal
Administrador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Debe existir al menos un anuncio general registrado dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la lista de anuncios generales registrados.
3. El Administrador selecciona el anuncio que desea eliminar.
4. El sistema solicita confirmación antes de eliminar el anuncio.
5. El Administrador confirma la eliminación.
6. El sistema elimina el anuncio general seleccionado.
7. El sistema registra la eliminación realizada.

## Postcondiciones
- El anuncio general deja de estar disponible para los usuarios.
- El sistema actualiza la lista de anuncios generales.
- El sistema conserva un registro de la eliminación realizada.

---

# CU-07 Crear Reporte de Comunicación

## Descripción
Permite al Administrador generar reportes sobre mensajes, comunicados y anuncios enviados dentro del sistema.
## Actor Principal
Administrador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir registros de comunicación dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la opción de reportes de comunicación.
3. El Administrador selecciona los filtros del reporte.
4. El sistema consulta los registros de comunicación disponibles.
5. El sistema genera el reporte solicitado.
6. El Administrador visualiza el reporte generado.

## Postcondiciones
- El reporte de comunicación queda generado correctamente.
- El Administrador puede revisar la información de mensajes, comunicados y anuncios.
- El sistema mantiene disponibles los registros consultados.

---

# CU-08 Consultar Historial de Mensajes

## Descripción
Permite consultar el historial de mensajes, comunicados y anuncios registrados en el sistema. El Administrador puede consultar el historial general de comunicaciones, mientras que los residentes y trabajadores pueden consultar únicamente los mensajes o comunicados dirigidos a ellos.

## Actor Principal
Administrador, Residente, Trabajador

## Precondiciones
- El usuario debe haber iniciado sesión en el sistema.
- Deben existir mensajes, comunicados o anuncios registrados dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El usuario ingresa al módulo de comunicación.
2. El sistema muestra la opción de consultar historial de mensajes.
3. El usuario selecciona los filtros de consulta disponibles.
4. El sistema valida los permisos del usuario.
5. El sistema muestra los mensajes, comunicados o anuncios correspondientes según el rol del usuario.

## Postcondiciones
- El historial de mensajes queda visualizado correctamente.
- El usuario accede únicamente a la información permitida según su rol.
- El sistema mantiene protegida la información que no corresponde al usuario.

---

# CU-09 Registrar Historial de Comunicaciones

## Descripción
Permite al sistema almacenar automáticamente el historial de mensajes, comunicados y anuncios realizados dentro del módulo de comunicación.

## Actor Principal
Sistema

## Precondiciones
- Debe existir una acción de comunicación realizada dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El sistema detecta una acción de comunicación realizada.
2. El sistema identifica el tipo de comunicación registrada.
3. El sistema almacena la información correspondiente.
4. El sistema actualiza el historial de comunicaciones.

## Postcondiciones
- La comunicación queda almacenada dentro del historial del sistema.
- El historial de comunicaciones queda actualizado.
- El sistema conserva la información necesaria para futuras consultas o reportes.

---

# CU-10 Notificar Mensaje Recibido

## Descripción
Permite al sistema notificar automáticamente a los usuarios cuando reciben un mensaje, comunicado o anuncio.

## Actor Principal
Sistema

## Precondiciones
- Debe existir un mensaje, comunicado o anuncio enviado dentro del sistema.
- Debe existir al menos un destinatario registrado dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Flujo Principal
1. El sistema detecta un nuevo mensaje, comunicado o anuncio enviado.
2. El sistema identifica a los destinatarios correspondientes.
3. El sistema genera la notificación respectiva.
4. El sistema envía la notificación a los usuarios destinatarios.
5. El sistema registra la notificación realizada.

## Postcondiciones
- Los usuarios destinatarios reciben la notificación correspondiente.
- El sistema registra la notificación generada.
- La comunicación queda disponible para consulta dentro del sistema.