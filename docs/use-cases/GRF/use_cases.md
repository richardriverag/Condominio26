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

## Actores Secundarios
Residente

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir residentes registrados dentro del sistema de gestión del condominio.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El mensaje queda enviado a los residentes correspondientes.
- El mensaje queda almacenado dentro del historial de comunicaciones del sistema.
- El sistema conserva la fecha y hora del envío realizado.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra las opciones disponibles de mensajería.
3. El Administrador selecciona la opción de enviar mensaje a residentes.
4. El Administrador redacta el asunto y el contenido del mensaje.
5. El sistema valida que la información ingresada sea correcta.
6. El sistema envía el mensaje a los residentes correspondientes.
7. El sistema registra el envío dentro del historial de comunicaciones.

## Flujos Alternativos

### A1. Campos obligatorios incompletos

1. El Administrador intenta enviar el mensaje sin completar el asunto o el contenido.
2. El sistema detecta que existen campos obligatorios vacíos.
3. El sistema informa que la información requerida está incompleta.

### A2. Error durante el envío del mensaje

1. El Administrador confirma el envío del mensaje.
2. El sistema detecta un error durante el proceso de envío.
3. El sistema informa que no fue posible completar el envío del mensaje.
4. El caso de uso finaliza manteniendo la información sin enviar.

## Reglas de Negocio

- Solo el Administrador puede enviar mensajes dirigidos a residentes.
- Los mensajes enviados deben quedar registrados dentro del historial de comunicaciones.
- El mensaje debe contener obligatoriamente un contenido.
- Los residentes únicamente pueden visualizar mensajes dirigidos hacia ellos.



---

# CU-02 Enviar Comunicado a Trabajadores

## Descripción
Permite al Administrador enviar comunicados dirigidos a los trabajadores del condominio.

## Actor Principal
Administrador

## Actores Secundarios
Trabajador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir trabajadores registrados dentro del sistema de gestión del condominio.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El comunicado queda enviado a los trabajadores correspondientes.
- El comunicado queda almacenado dentro del historial de comunicaciones del sistema.
- El sistema conserva la fecha y hora del envío realizado.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra las opciones disponibles de comunicación.
3. El Administrador selecciona la opción de enviar comunicado a trabajadores.
4. El Administrador redacta el asunto y el contenido del comunicado.
5. El sistema valida la información ingresada.
6. El sistema envía el comunicado a los trabajadores correspondientes.
7. El sistema registra el comunicado dentro del historial de comunicaciones.

## Flujos Alternativos

### A1. Campos obligatorios incompletos
1. El Administrador intenta enviar el comunicado sin completar el asunto o el contenido.
2. El sistema detecta que existen campos obligatorios vacíos.
3. El sistema informa que la información requerida está incompleta.

### A2. Error durante el envío del comunicado
1. El Administrador confirma el envío del comunicado.
2. El sistema detecta un error durante el proceso de envío.
3. El sistema informa que no fue posible completar el envío del comunicado.
4. El caso de uso finaliza manteniendo la información sin enviar.

## Reglas de Negocio

- Solo el Administrador puede enviar comunicados dirigidos a trabajadores.
- Los comunicados enviados deben quedar registrados dentro del historial de comunicaciones.
- El comunicado debe contener obligatoriamente un asunto y un contenido.
- Los trabajadores únicamente pueden visualizar comunicados dirigidos hacia ellos.

---

# CU-03 Enviar Mensaje Global

## Descripción
Permite al Administrador enviar mensajes generales a residentes y trabajadores del condominio.

## Actor Principal
Administrador

## Actores Secundarios
Residente, Trabajador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir usuarios registrados dentro del sistema de gestión del condominio.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El mensaje global queda enviado a residentes y trabajadores.
- El mensaje queda almacenado dentro del historial de comunicaciones del sistema.
- El sistema conserva la fecha y hora del envío realizado.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra las opciones disponibles de mensajería.
3. El Administrador selecciona la opción de enviar mensaje global.
4. El Administrador redacta el asunto y el contenido del mensaje.
5. El sistema valida que la información ingresada sea correcta.
6. El sistema envía el mensaje a todos los usuarios correspondientes.
7. El sistema registra el mensaje global dentro del historial de comunicaciones.

## Flujos Alternativos

### A1. Campos obligatorios incompletos
1. El Administrador intenta enviar el mensaje sin completar el asunto o el contenido.
2. El sistema detecta que existen campos obligatorios vacíos.
3. El sistema informa que la información requerida está incompleta.

### A2. Error durante el envío del mensaje
1. El Administrador confirma el envío del mensaje.
2. El sistema detecta un error durante el proceso de envío.
3. El sistema informa que no fue posible completar el envío del mensaje.
4. El caso de uso finaliza manteniendo la información sin enviar.

## Reglas de Negocio
- Solo el Administrador puede enviar mensajes globales.
- Los mensajes globales deben enviarse a todos los residentes y trabajadores registrados.
- Los mensajes enviados deben quedar registrados dentro del historial de comunicaciones.
- El mensaje debe contener obligatoriamente un asunto y un contenido.

---

# CU-04 Crear Anuncio General

## Descripción
Permite al Administrador publicar anuncios generales para los usuarios del condominio.

## Actor Principal
Administrador

## Actores Secundarios
Residente, Trabajador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El anuncio general queda publicado dentro del sistema.
- El anuncio queda disponible para los usuarios correspondientes.
- El sistema conserva un registro de la fecha y hora de publicación.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la opción de gestión de anuncios generales.
3. El Administrador selecciona la opción de crear anuncio general.
4. El Administrador ingresa el título y contenido del anuncio.
5. El sistema valida que la información ingresada sea correcta.
6. El sistema publica el anuncio general.
7. El sistema registra la creación del anuncio dentro del historial de comunicaciones.

## Flujos Alternativos

### A1. Campos obligatorios incompletos
1. El Administrador intenta publicar el anuncio sin completar el título o el contenido.
2. El sistema detecta que existen campos obligatorios vacíos.
3. El sistema informa que la información requerida está incompleta.

### A2. Error durante la publicación del anuncio
1. El Administrador confirma la publicación del anuncio.
2. El sistema detecta un error durante el proceso de publicación.
3. El sistema informa que no fue posible completar la publicación del a

## Reglas de Negocio
- Solo el Administrador puede crear anuncios generales.
- Los anuncios generales deben quedar registrados dentro del historial de comunicaciones.
- Todo anuncio general debe contener obligatoriamente un título y un contenido.
- Los anuncios publicados deben ser visibles para residentes y trabajadores registrados.
---

# CU-05 Modificar Anuncio General

## Descripción
Permite al Administrador modificar anuncios generales publicados previamente, con el fin de corregir, actualizar o complementar información comunicada a los usuarios del condominio.

## Actor Principal
Administrador

## Actores Secundarios
Residente, Trabajador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Debe existir al menos un anuncio general registrado dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El anuncio general queda actualizado correctamente.
- El sistema guarda los cambios realizados.
- El sistema conserva un registro de la fecha y hora de modificación.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la lista de anuncios generales registrados.
3. El Administrador selecciona el anuncio que desea modificar.
4. El Administrador edita el título o contenido del anuncio.
5. El sistema valida los cambios realizados.
6. El Administrador confirma la modificación del anuncio.
7. El sistema actualiza el anuncio general.
8. El sistema registra la modificación realizada.

## Flujos Alternativos

### A1. Campos obligatorios incompletos
1. El Administrador intenta modificar el anuncio sin completar el título o el contenido.
2. El sistema detecta que existen campos obligatorios vacíos.
3. El sistema informa que la información requerida está incompleta.

### A2. Anuncio inexistente
1. El Administrador selecciona un anuncio para modificar.
2. El sistema detecta que el anuncio ya no existe o fue eliminado previamente.
3. El sistema informa que el anuncio seleccionado no se encuentra disponible.
4. El caso de uso finaliza sin realizar modificaciones.

### A3. Error durante la modificación del anuncio
1. El Administrador confirma la modificación del anuncio.
2. El sistema detecta un error durante el proceso de actualización.
3. El sistema informa que no fue posible completar la modificación del anuncio.
4. El caso de uso finaliza manteniendo la información anterior del anuncio.

## Reglas de Negocio
- Solo el Administrador puede modificar anuncios generales.
- Todo anuncio general debe contener obligatoriamente un título y un contenido.
- Las modificaciones realizadas deben quedar registradas dentro del historial de comunicaciones.
- Los cambios realizados en un anuncio deben reflejarse para todos los usuarios correspondientes.

---

# CU-06 Eliminar Anuncio General

## Descripción
Permite al Administrador eliminar anuncios generales publicados previamente cuando la información ya no sea necesaria, esté desactualizada o no deba permanecer visible para los usuarios.

## Actor Principal
Administrador

## Actores Secundarios
Residente, Trabajador

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Debe existir al menos un anuncio general registrado dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El anuncio general deja de estar disponible para los usuarios.
- El sistema actualiza la lista de anuncios generales.
- El sistema conserva un registro de la eliminación realizada.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la lista de anuncios generales registrados.
3. El Administrador selecciona el anuncio que desea eliminar.
4. El sistema solicita confirmación antes de eliminar el anuncio.
5. El Administrador confirma la eliminación.
6. El sistema elimina el anuncio general seleccionado.
7. El sistema registra la eliminación realizada.

## Flujos Alternativos

### A1. Cancelación de la eliminación
1. El Administrador selecciona un anuncio para eliminar.
2. El sistema solicita confirmación de la eliminación.
3. El Administrador cancela la operación.
4. El sistema mantiene el anuncio sin modificaciones.
5. El caso de uso finaliza sin eliminar el anuncio.

### A2. Anuncio inexistente
1. El Administrador selecciona un anuncio para eliminar.
2. El sistema detecta que el anuncio ya no existe o fue eliminado previamente.
3. El sistema informa que el anuncio seleccionado no se encuentra disponible.
4. El caso de uso finaliza sin realizar modificaciones.

### A3. Error durante la eliminación del anuncio
1. El Administrador confirma la eliminación del anuncio.
2. El sistema detecta un error durante el proceso de eliminación.
3. El sistema informa que no fue posible completar la eliminación del anuncio.
4. El caso de uso finaliza manteniendo el anuncio disponible dentro del sistema.

## Reglas de Negocio

- Solo el Administrador puede eliminar anuncios generales.
- Toda eliminación de anuncios debe quedar registrada dentro del historial de comunicaciones.
- Los anuncios eliminados dejan de estar visibles para residentes y trabajadores.
- El sistema debe conservar un registro histórico de las eliminaciones realizadas.
---

# CU-07 Crear Reporte de Comunicación

## Descripción
Permite al Administrador generar reportes sobre mensajes, comunicados y anuncios enviados dentro del sistema.

## Actor Principal
Administrador

## Actores Secundarios
Ninguno

## Precondiciones
- El Administrador debe haber iniciado sesión en el sistema.
- Deben existir registros de comunicación dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El reporte de comunicación queda generado correctamente.
- El Administrador puede revisar la información de mensajes, comunicados y anuncios.
- El sistema mantiene disponibles los registros consultados.

## Flujo Principal
1. El Administrador ingresa al módulo de comunicación.
2. El sistema muestra la opción de reportes de comunicación.
3. El Administrador selecciona los filtros del reporte.
4. El sistema consulta los registros de comunicación disponibles.
5. El sistema genera el reporte solicitado.
6. El Administrador visualiza el reporte generado.

## Flujos Alternativos

### A1. No existen registros de comunicación
1. El Administrador solicita generar un reporte de comunicación.
2. El sistema detecta que no existen registros almacenados dentro del sistema.
3. El sistema informa que no existen datos disponibles para generar el reporte.

### A2. Filtros de búsqueda inválidos
1. El Administrador ingresa filtros de búsqueda incorrectos o incompletos.
2. El sistema detecta inconsistencias en la información ingresada.
3. El sistema informa que los filtros proporcionados no son válidos.
4. El flujo regresa al paso 5 del Flujo Principal.

### A3. Error durante la generación del reporte
1. El sistema inicia el proceso de generación del reporte.
2. El sistema detecta un error durante la consulta o generación de la información.
3. El sistema informa que no fue posible generar el reporte solicitado.
4. El caso de uso finaliza sin generar el reporte.

## Reglas de Negocio
- Solo el Administrador puede generar reportes de comunicación.
- Los reportes deben generarse utilizando información almacenada dentro del historial de comunicaciones.
- El sistema debe conservar la integridad de los registros consultados.
- Los reportes únicamente deben mostrar información correspondiente al módulo de comunicación.

---

# CU-08 Consultar Historial de Mensajes

## Descripción
Permite consultar el historial de mensajes, comunicados y anuncios registrados en el sistema. El Administrador puede consultar el historial general de comunicaciones, mientras que los residentes y trabajadores pueden consultar únicamente los mensajes o comunicados dirigidos a ellos.

## Actor Principal
Administrador, Residente, Trabajador

## Actores Secundarios
Ninguno

## Precondiciones
- El usuario debe haber iniciado sesión en el sistema.
- Deben existir mensajes, comunicados o anuncios registrados dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- El historial de mensajes queda visualizado correctamente.
- El usuario accede únicamente a la información permitida según su rol.
- El sistema mantiene protegida la información que no corresponde al usuario.

## Flujo Principal
1. El usuario ingresa al módulo de comunicación.
2. El sistema muestra la opción de consultar historial de mensajes.
3. El usuario selecciona los filtros de consulta disponibles.
4. El sistema valida los permisos del usuario.
5. El sistema muestra los mensajes, comunicados o anuncios correspondientes según el rol del usuario.

## Flujos Alternativos

### A1. No existen registros de comunicación
1. El usuario solicita consultar el historial de mensajes.
2. El sistema detecta que no existen registros de comunicación almacenados.
3. El sistema informa que no existen mensajes o anuncios disponibles para visualizar.
4. El caso de uso finaliza sin mostrar información.

### A2. Filtros de búsqueda inválidos
1. El usuario ingresa filtros de búsqueda incorrectos o incompletos.
2. El sistema detecta inconsistencias en la información ingresada.
3. El sistema informa que los filtros proporcionados no son válidos.

### A3. Acceso no permitido
1. El usuario intenta acceder a mensajes o anuncios que no le corresponden según su rol.
2. El sistema detecta que el usuario no posee permisos suficientes.
3. El sistema restringe el acceso a la información solicitada.
4. El caso de uso finaliza manteniendo protegida la información del sistema.

## Reglas de Negocio
- El Administrador puede consultar el historial general de comunicaciones.
- Los residentes y trabajadores únicamente pueden consultar mensajes o anuncios dirigidos hacia ellos.
- El historial de comunicaciones debe conservarse almacenado dentro del sistema.
- El acceso a la información debe respetar los permisos definidos según el rol del usuario.

---

# CU-09 Registrar Historial de Comunicaciones

## Descripción
Permite al sistema almacenar automáticamente el historial de mensajes, comunicados y anuncios realizados dentro del módulo de comunicación.

## Actor Principal
Sistema

## Actores Secundarios
Administrador, Residente, Trabajador

## Precondiciones
- Debe existir una acción de comunicación realizada dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.
- Deben existir usuarios registrados dentro del sistema de gestión del condominio.

## Postcondiciones
- La comunicación queda almacenada dentro del historial del sistema.
- El historial de comunicaciones queda actualizado.
- El sistema conserva la información necesaria para futuras consultas o reportes.

## Flujo Principal
1. El sistema detecta una acción de comunicación realizada.
2. El sistema identifica el tipo de comunicación registrada.
3. El sistema almacena la información correspondiente.
4. El sistema actualiza el historial de comunicaciones.

## Flujos Alternativos

### A1. Información incompleta
1. El sistema detecta que la información asociada a la comunicación se encuentra incompleta.
2. El sistema impide registrar la comunicación dentro del historial.
3. El sistema conserva el estado anterior del historial de comunicaciones.
4. El caso de uso finaliza sin registrar la información.

### A2. Error durante el registro del historial
1. El sistema inicia el proceso de almacenamiento de la comunicación.
2. El sistema detecta un error durante el registro de la información.
3. El sistema mantiene la información sin almacenar dentro del historial.
4. El caso de uso finaliza sin actualizar el historial de comunicaciones.

## Reglas de Negocio
- Toda comunicación realizada dentro del módulo debe quedar registrada dentro del historial de comunicaciones.
- El historial de comunicaciones debe conservar la fecha y hora de cada registro realizado.
- El sistema no debe permitir registros duplicados de una misma comunicación.
- La información almacenada dentro del historial debe mantenerse disponible para futuras consultas y reportes.

---

# CU-10 Notificar Mensaje Recibido

## Descripción
Permite al sistema notificar automáticamente a los usuarios cuando reciben un mensaje, comunicado o anuncio.

## Actor Principal
Sistema

## Actores Secundarios
Administrador, Residente, Trabajador

## Precondiciones
- Debe existir un mensaje, comunicado o anuncio enviado dentro del sistema.
- Debe existir al menos un destinatario registrado dentro del sistema.
- El módulo de comunicación debe encontrarse disponible.

## Postcondiciones
- Los usuarios destinatarios reciben la notificación correspondiente.
- El sistema registra la notificación generada.
- La comunicación queda disponible para consulta dentro del sistema.

## Flujo Principal
1. El sistema detecta un nuevo mensaje, comunicado o anuncio enviado.
2. El sistema identifica a los destinatarios correspondientes.
3. El sistema genera la notificación respectiva.
4. El sistema envía la notificación a los usuarios destinatarios.
5. El sistema registra la notificación realizada.

## Flujos Alternativos

### A1. Destinatario inexistente
1. El sistema intenta identificar a los destinatarios correspondientes.
2. El sistema detecta que uno o varios destinatarios no existen dentro del sistema.
3. El sistema omite los destinatarios inválidos.
4. El sistema continúa el proceso únicamente con los destinatarios válidos.

### A2. Error durante el envío de la notificación
1. El sistema inicia el proceso de envío de notificaciones.
2. El sistema detecta un error durante el envío de una o varias notificaciones.
3. El sistema registra el error correspondiente.
4. El caso de uso finaliza manteniendo la comunicación almacenada dentro del sistema.

### A3. Módulo de comunicación no disponible
1. El sistema intenta generar las notificaciones correspondientes.
2. El sistema detecta que el módulo de comunicación no se encuentra disponible.
3. El sistema suspende temporalmente el proceso de notificación.
4. El caso de uso finaliza sin enviar las notificaciones correspondientes.

## Reglas de Negocio
- Toda comunicación enviada debe generar una notificación para los destinatarios correspondientes.
- Las notificaciones deben quedar registradas dentro del historial de comunicaciones.
- El sistema únicamente debe enviar notificaciones a usuarios registrados dentro del sistema.
- Las notificaciones deben estar asociadas a una comunicación previamente registrada.

