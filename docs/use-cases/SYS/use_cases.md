# Especificación General de Casos de Uso — Sistema Condominio

Este documento consolida y unifica los casos de uso definidos por todos los equipos para el sistema de gestión del condominio.

---

## 1. Introducción al Sistema
El Sistema Condominio es una plataforma integral orientada a facilitar la administración y convivencia dentro de un conjunto habitacional o edificio. Está dividido en seis módulos funcionales interconectados:
1. **GRA (Pagos y Finanzas):** Gestión de alícuotas, cobros, diferimiento de deudas y reportes de solvencia.
2. **GRB (Gestión de Usuarios y Administradores):** Control de acceso, perfiles, asignación de roles y permisos.
3. **GRC (Gestión de Inmuebles):** Control del inventario físico (edificios, departamentos, locales), asignación de propietarios y agenda de reservas general.
4. **GRD (Reservas de Espacios Comunes):** Reservación específica de áreas comunes, salones de eventos, control de disponibilidad y cancelaciones por los residentes.
5. **GRE (Check-in / Accesos):** Control y registro de entrada de visitantes, registro de vehículos y parqueaderos, programación de visitas y envío de alertas de seguridad.
6. **GRF (Comunicación):** Difusión de anuncios, envío de mensajería interna global y segmentada a residentes y trabajadores.

---

## 2. Modelo de Actores
A continuación se definen los actores que interactúan con el sistema:

*   **Usuario:** Actor general del sistema. Posee una cuenta y credenciales para autenticarse. Todos los actores físicos heredan los permisos básicos del Usuario (inicio de sesión, actualización de perfil y recuperación de contraseña).
*   **Administrador:** Encargado de la gestión general del condominio, incluyendo las finanzas, usuarios, inmuebles, catálogo, alertas y anuncios de comunicación.
*   **Residente:** Habitante del condominio. Puede consultar y pagar sus deudas, solicitar financiamientos, descargar certificados de solvencia, realizar reservas de áreas comunes, registrar visitas programadas y recibir notificaciones y alertas.
*   **Propietario:** Dueño de uno o varios inmuebles en el condominio. Puede gestionar las relaciones de sus propiedades.
*   **Trabajador:** Conserjes, personal de mantenimiento, etc. Puede recibir comunicados y consultar su historial de mensajes.
*   **Personal de Seguridad:** Encargado de la vigilancia física y del registro de ingresos de visitantes y vehículos en la garita.
*   **Sistema (Automático):** Proceso autónomo encargado de la generación mensual de alícuotas, envíos de recordatorios automáticos de deudas, registro de bitácoras de comunicación y notificaciones push/correo electrónico.

---

## 3. Especificación de Casos de Uso por Módulo

### 3.1. GRA - Módulo de Pagos y Finanzas

#### UC-GRA-01: registrarDeuda
*   **Descripción:** Permite al Administrador registrar una deuda específica o cargo extraordinario a un residente.
*   **Actor Principal:** Administrador
*   **Precondiciones:** El Administrador ha iniciado sesión. El residente destinatario existe.
*   **Flujo Principal:**
    1. El Administrador ingresa el identificador del residente, monto y descripción.
    2. El Sistema valida los datos ingresados.
    3. El Administrador confirma el registro.
    4. El Sistema guarda la deuda con estado "PENDIENTE".
*   **Flujos Alternativos:**
    *   **A1. Datos inválidos:** El Sistema muestra un mensaje de error y no registra la deuda.

#### UC-GRA-02: registrarPagoEfectivoTransferencia (Anderson)
*   **Descripción:** Permite al Administrador registrar manualmente un pago recibido por transferencia bancaria o en efectivo.
*   **Actor Principal:** Administrador
*   **Precondiciones:** El Administrador ha iniciado sesión. La deuda identificada existe en estado "PENDIENTE".
*   **Flujo Principal:**
    1. El Administrador ingresa el ID de la deuda.
    2. El Sistema verifica la existencia y el estado "PENDIENTE" de la deuda.
    3. Si es por transferencia, el Sistema muestra el comprobante cargado.
    4. El Administrador confirma la recepción del pago y cambia el estado a "PAGADA".
    5. El Sistema muestra el mensaje de éxito.
*   **Flujos Alternativos:**
    *   **A1. Deuda no encontrada:** El Sistema emite "No existe una deuda con el identificador proporcionado".
    *   **A2. Deuda ya pagada:** El Sistema emite "Esta deuda ya ha sido pagada".

#### UC-GRA-03: modificarFechaMaximaDePagoDeUnaDeuda (Ricardo)
*   **Descripción:** Permite al Administrador ampliar o modificar la fecha máxima de vencimiento de una deuda pendiente.
*   **Actor Principal:** Administrador
*   **Precondiciones:** La deuda existe en el sistema.
*   **Flujo Principal:**
    1. El Administrador ingresa el número de deuda.
    2. El Sistema recupera los datos de la deuda.
    3. El Administrador ingresa la nueva fecha de pago.
    4. El Sistema valida que la fecha sea válida (futura y posterior a la fecha máxima actual).
    5. El Sistema actualiza la fecha máxima de pago de la deuda.
*   **Flujos Alternativos:**
    *   **A1. Deuda no registrada:** El Sistema emite "Deuda no registrada en el sistema".
    *   **A2. Fecha inválida:** El Sistema emite "La fecha ingresada no es válida...".

#### UC-GRA-04: definirValorMensualDeAlicuotas (Ricardo)
*   **Descripción:** Permite al Administrador establecer el valor estándar mensual a cobrar por concepto de alícuotas ordinarias.
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador ingresa el nuevo valor de la alícuota.
    2. El Sistema valida que sea un número mayor que 0 y con hasta 2 decimales.
    3. El Sistema actualiza el valor mensual configurado.
*   **Flujos Alternativos:**
    *   **A1. Valor inválido:** El Sistema emite "El valor de la alícuota ingresado no es válido...".

#### UC-GRA-05: generarReporteDePagosRealizados (Bryan)
*   **Descripción:** Permite generar un reporte en PDF/pantalla de todos los pagos efectuados por un residente.
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador ingresa el identificador del residente.
    2. El Sistema verifica la existencia del residente y busca sus pagos asociados.
    3. El Sistema genera y muestra el reporte con detalles de montos, descripción, fechas y métodos de pago.
*   **Flujos Alternativos:**
    *   **A1. Residente no existe:** El Sistema emite "No existe un residente asociado al identificador ingresado".
    *   **A2. Sin pagos registrados:** El Sistema emite "No existen pagos realizados asociados al residente".

#### UC-GRA-06: registrarEntidadBancaria (Bryan)
*   **Descripción:** Permite registrar las cuentas de banco autorizadas del condominio para recibir transferencias.
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador ingresa el nombre de la entidad bancaria y el número de cuenta.
    2. El Sistema valida la información y verifica la no duplicidad del número de cuenta.
    3. El Sistema almacena la entidad bancaria.
*   **Flujos Alternativos:**
    *   **A1. Cuenta ya registrada:** El Sistema emite "Ya existe una entidad bancaria registrada con el número de cuenta ingresado".

#### UC-GRA-07: eliminarDeuda (Jorge)
*   **Descripción:** Permite al Administrador anular de forma lógica una deuda registrada por error.
*   **Actor Principal:** Administrador
*   **Precondiciones:** La deuda se encuentra en estado "PENDIENTE" y no está asociada a ningún convenio de cuotas activo.
*   **Flujo Principal:**
    1. El Administrador busca la deuda y selecciona "Eliminar Deuda".
    2. El Sistema solicita confirmación y justificación obligatoria.
    3. El Administrador ingresa la justificación y confirma.
    4. El Sistema ejecuta el borrado lógico ("ELIMINADA"), registra la auditoría y recalcula el saldo del residente.
*   **Flujos Alternativos:**
    *   **A1. Vinculada a plan de pagos:** El Sistema bloquea la acción indicando que debe disolver el convenio de pago primero.

#### UC-GRA-08: registrarPago
*   **Descripción:** Permite al Residente notificar la realización de un pago de alícuota adjuntando el comprobante.
*   **Actor Principal:** Residente
*   **Precondiciones:** El Residente ha iniciado sesión y tiene deudas pendientes.
*   **Flujo Principal:**
    1. El Residente selecciona la deuda a pagar.
    2. Selecciona el método de pago (transferencia/depósito).
    3. Adjunta la foto/PDF del comprobante y envía.
    4. El Sistema guarda el pago con estado "PENDIENTE_VERIFICACION" y notifica al Administrador.

#### UC-GRA-09: definirPagoDeAlicuotasDeFormaMensual (Ricardo)
*   **Descripción:** Permite configurar la automatización temporal del pago mensual para proyectar deudas de alícuotas recurrentes.
*   **Actor Principal:** Administrador (y se asigna al cronograma del Residente)
*   **Flujo Principal:**
    1. El Administrador ingresa el número de meses de proyección y el día límite de pago (entre 1 y 28).
    2. El Sistema valida los datos y calcula las fechas límites de pago de cada alícuota proyectada.
    3. El Sistema genera las deudas mensuales proyectadas.

#### UC-GRA-10: solicitarPagoEnCuotas (Jorge)
*   **Descripción:** Permite al Residente diferir el pago de una deuda pendiente (ej. extraordinarias) en cuotas mensuales (de 2 a 12 meses).
*   **Actor Principal:** Residente
*   **Precondiciones:** El Residente tiene deudas pendientes.
*   **Flujo Principal:**
    1. El Residente selecciona la deuda elegible y hace clic en "Solicitar Pago en Cuotas".
    2. El Residente define el número de cuotas deseadas.
    3. El Sistema simula el plan de pagos (número de cuotas, montos redondeados, fechas límites).
    4. El Residente acepta los términos.
    5. El Sistema registra el plan de pagos como "SOLICITADO" y genera una alerta al Administrador.
*   **Flujos Alternativos:**
    *   **A1. Deuda ya diferida:** El Sistema alerta que la obligación ya tiene un plan activo.
    *   **A2. Cancelación voluntaria:** El Residente cancela la simulación y el sistema revierte los cálculos sin alterar la base de datos.

#### UC-GRA-11: eliminarFormaDePagoRecurrente (Jorge)
*   **Descripción:** Permite al Residente desvincular un método de pago guardado para cobros recurrentes de alícuotas (débito automático).
*   **Actor Principal:** Residente
*   **Flujo Principal:**
    1. El Residente ingresa al módulo de métodos de pago y selecciona la tarjeta/cuenta configurada como recurrente.
    2. Selecciona "Eliminar Forma de Pago Recurrente".
    3. El Sistema muestra una advertencia sobre la necesidad de pago manual futuro.
    4. El Residente confirma; el sistema se comunica con la pasarela de pagos, desactiva la recurrencia y envía confirmación al correo.
*   **Flujos Alternativos:**
    *   **A1. Error en pasarela de pagos:** El Sistema revierte la acción local e informa que el débito sigue activo y debe reintentarse.

#### UC-GRA-12: generarCertificadoDeNoDeudor (Jorge)
*   **Descripción:** Genera un certificado de solvencia (No Deudor) en PDF no editable para el residente que se encuentra al día.
*   **Actor Principal:** Residente o Administrador
*   **Flujo Principal:**
    1. El usuario solicita generar el certificado.
    2. El Sistema valida que no existan obligaciones pendientes ni en mora.
    3. El Sistema genera el PDF con los datos del residente, departamento y código de verificación.
    4. Registra la emisión en la bitácora de auditoría y habilita la descarga.
*   **Flujos Alternativos:**
    *   **A1. Convenio de pago al día:** Si el residente tiene cuotas diferidas no vencidas, se le permite generar un certificado parcial con la observación de que posee un plan activo.

#### UC-GRA-13: consultarDeuda (Ricardo)
*   **Descripción:** Permite visualizar el estado y detalle de las obligaciones de pago de un residente.
*   **Actor Principal:** Administrador, Residente
*   **Flujo Principal:**
    1. El Administrador (por cédula) o el Residente (de su propia cuenta) solicita consultar deudas.
    2. El Sistema recupera todas las obligaciones financieras asociadas.
    3. Se muestra un desglose de deudas pendientes, pagadas o vencidas.

#### UC-GRA-14: consultarPagosEfectuados (Bryan)
*   **Descripción:** Permite consultar los pagos realizados filtrando por un rango de fechas.
*   **Actor Principal:** Administrador, Residente
*   **Flujo Principal:**
    1. El actor ingresa la fecha de inicio y la fecha de fin.
    2. El Sistema valida el formato y que la fecha de fin sea posterior a la de inicio.
    3. El Sistema muestra los pagos registrados correspondientes.

#### UC-GRA-15: registrarDeudaMensual (Sistema)
*   **Descripción:** Caso de uso automático que se ejecuta el primer día de cada mes para registrar las alícuotas ordinarias a todas las propiedades.
*   **Actor Principal:** Sistema (Automático)
*   **Postcondiciones:** Se crean deudas en estado "PENDIENTE" para cada departamento y suite según el valor configurado.

#### UC-GRA-16: enviarRecordatorioDeDeudaPendiente (Sistema / Bryan)
*   **Descripción:** Envía notificaciones de cobro automáticas al correo de los residentes que tienen deudas pendientes o en mora.
*   **Actor Principal:** Sistema (Automático)
*   **Flujo Principal:**
    1. El Sistema detecta deudas vencidas o próximas a vencer.
    2. Genera un correo electrónico detallando el saldo pendiente y fecha límite.
    3. Envía el correo al destinatario y registra el evento en el log.

---

### 3.2. GRB - Módulo de Gestión de Usuarios y Administradores

#### UC-GRB-01: registrarCuenta
*   **Descripción:** Permite al Administrador registrar un nuevo usuario (residente, guardia, trabajador) en el sistema.
*   **Actor Principal:** Administrador
*   **Precondiciones:** El Administrador ha iniciado sesión y tiene permisos.
*   **Flujo Principal:**
    1. El Administrador proporciona nombres, apellidos y correo electrónico del usuario.
    2. El Sistema valida los campos y comprueba que el correo no esté registrado.
    3. El Administrador define la contraseña inicial.
    4. El Sistema guarda la cuenta y envía un correo con credenciales de acceso.

#### UC-GRB-02: actualizarInformacionDeCuenta
*   **Descripción:** Permite modificar datos básicos de una cuenta existente.
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador selecciona el usuario y el sistema muestra los datos actuales.
    2. El Administrador modifica los datos y guarda.
    3. El Sistema valida y aplica los cambios.

#### UC-GRB-03: desactivarCuenta
*   **Descripción:** Permite desactivar el acceso de un usuario al sistema temporal o definitivamente, sin borrar su historial de transacciones.
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador selecciona la cuenta activa y solicita desactivar.
    2. El Sistema solicita confirmación.
    3. Al confirmar, el estado de la cuenta cambia a "desactivada" y se revoca su token de sesión.

#### UC-GRB-04: asignarRolAUsuario
*   **Descripción:** Permite cambiar el rol asignado a un usuario (ej. de Residente a Directiva, o agregar rol Conserje).
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador busca al usuario.
    2. Selecciona "Asignar nuevo rol" y elige de la lista de roles del sistema.
    3. Guarda los cambios. Los nuevos permisos se aplican inmediatamente.

#### UC-GRB-05: definirPermisosDeRol
*   **Descripción:** Permite configurar las acciones autorizadas para cada rol en el sistema.
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador ingresa el nombre del rol y selecciona los permisos.
    2. El Sistema guarda o actualiza el mapeo de permisos del rol.

#### UC-GRB-06: iniciarSesion
*   **Descripción:** Permite la autenticación de usuarios y administradores en la plataforma.
*   **Actor Principal:** Usuario
*   **Flujo Principal:**
    1. El Usuario ingresa su correo electrónico y contraseña.
    2. El Sistema valida las credenciales y el estado activo de la cuenta.
    3. Si es correcto, otorga acceso y redirige según el rol.
*   **Flujos Alternativos:**
    *   **A1. Credenciales incorrectas:** Se muestra "Credenciales incorrectas" y se niega el ingreso.
    *   **A2. Cuenta desactivada:** Se emite "La cuenta se encuentra desactivada".

#### UC-GRB-07: actualizarPerfil
*   **Descripción:** Permite a cualquier usuario logueado cambiar su información personal (foto, teléfono, contraseña).
*   **Actor Principal:** Usuario
*   **Flujo Principal:**
    1. El Usuario ingresa a su perfil.
    2. Modifica la información deseada.
    3. El Sistema valida y almacena los cambios.

#### UC-GRB-08: recuperarContrasena
*   **Descripción:** Permite a un usuario restablecer su contraseña olvidada mediante un correo electrónico.
*   **Actor Principal:** Usuario
*   **Flujo Principal:**
    1. El Usuario ingresa su correo en la página de inicio.
    2. El Sistema valida que la cuenta exista.
    3. El Sistema ejecuta el caso de uso "enviarTokenDeRestablecimiento".
    4. El Usuario ingresa el token recibido y su nueva contraseña.
    5. El Sistema valida la contraseña, actualiza la cuenta y confirma.

#### UC-GRB-09: enviarTokenDeRestablecimiento
*   **Descripción:** Caso de uso automático que genera un token temporal único para recuperar contraseñas.
*   **Actor Principal:** Sistema (Automático)
*   **Postcondiciones:** Se asocia el token de seguridad a la cuenta y se envía al correo del usuario.

---

### 3.3. GRC - Módulo de Gestión de Inmuebles

#### UC-GRC-01: administrarInmuebles
*   **Descripción:** Permite al Administrador gestionar los elementos físicos del condominio.
*   **Actor Principal:** Administrador
*   **Flujo Principal:**
    1. El Administrador selecciona "administrarInmuebles".
    2. El Sistema despliega la lista de inmuebles.
    3. El Administrador puede agregar, editar o eliminar registros de unidades.
*   **Relaciones:** Incluye `registrarEdificios` y `registrarDepartamentosSuitesEstudiosYLocales`.

#### UC-GRC-02: registrarEdificios
*   **Descripción:** Permite ingresar nuevos edificios/bloques dentro de la infraestructura del condominio.
*   **Actor Principal:** Administrador (incluido en administrarInmuebles)

#### UC-GRC-03: registrarDepartamentosSuitesEstudiosYLocales
*   **Descripción:** Permite registrar unidades habitacionales específicas asociadas a un edificio padre.
*   **Actor Principal:** Administrador (incluido en administrarInmuebles)

#### UC-GRC-04: mantenerCatalogoDelCondominio
*   **Descripción:** Permite actualizar el listado global de áreas comunes, servicios y reglamentos del condominio.
*   **Actor Principal:** Administrador
*   **Relaciones:** Incluye `gestionarCaracteristicasDelCondominio`.

#### UC-GRC-05: gestionarCaracteristicasDelCondominio
*   **Descripción:** Configura variables del condominio (horarios generales, políticas, reglas de convivencia).
*   **Actor Principal:** Administrador (incluido en mantenerCatalogoDelCondominio)

#### UC-GRC-06: gestionarPropiedades
*   **Descripción:** Permite definir qué propietario es dueño de qué unidades específicas (departamentos, locales, estacionamientos).
*   **Actor Principal:** Administrador, Propietario
*   **Flujo Principal:**
    1. El actor selecciona la propiedad.
    2. Asigna o modifica la relación entre la unidad y el propietario.
    3. El Administrador puede asignar a cualquier cuenta; el Propietario solo visualiza y gestiona sus relaciones asignadas.

#### UC-GRC-07: realizarReservaOAgenda
*   **Descripción:** Permite solicitar el uso de una instalación del condominio en una fecha y hora determinadas.
*   **Actor Principal:** Usuario (Residente)
*   **Relaciones:** Incluye `gestionarNotificacionesYRecordatorios` (UC8).

#### UC-GRC-08: gestionarNotificacionesYRecordatorios
*   **Descripción:** Permite configurar y disparar alertas relacionadas con las reservaciones.
*   **Actor Principal:** Usuario
*   **Postcondiciones:** Se envían confirmaciones y alertas periódicas por los canales configurados (SMS, correo, app).

#### UC-GRC-09: actualizarEstadoDelInmueble
*   **Descripción:** Permite cambiar la operatividad de un inmueble (disponible, mantenimiento, inhabilitado).
*   **Actor Principal:** Administrador
*   **Relaciones:** Incluye `generarReportesDeCambios`.

#### UC-GRC-10: generarReportesDeCambios
*   **Descripción:** Muestra un histórico detallado de los cambios de estado y modificaciones del inmueble.
*   **Actor Principal:** Administrador

---

### 3.4. GRD - Módulo de Reservas de Espacios Comunes

#### UC-GRD-01: reservarAreaComun
*   **Descripción:** Permite al Residente realizar la reserva de un área común disponible, seleccionando fecha, hora y espacio deseado.
*   **Actor Principal:** Residente
*   **Precondiciones:**
    - El Residente debe haber iniciado sesión en el sistema.
    - Deben existir áreas comunes registradas en el sistema de gestión del condominio.
    - El módulo de reservas debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Residente ingresa al módulo de reservas de áreas comunes.
    2. El sistema muestra las opciones de áreas comunes disponibles.
    3. El Residente selecciona el área común que desea reservar.
    4. El sistema consulta la disponibilidad del espacio seleccionado.
    5. El Residente selecciona la fecha y horario de la reserva.
    6. El sistema valida que la información ingresada sea correcta y que el espacio esté disponible.
    7. El sistema registra la reserva del área común.
    8. El sistema notifica al Residente la confirmación de la reserva.
*   **Postcondiciones:**
    - La reserva queda registrada en el sistema.
    - El espacio disponible queda no disponible en la fecha y horario seleccionado.
    - El sistema conserva la fecha, hora y datos del Residente asociados en la reserva.
*   **Relaciones:**
    - Especializaciones: `reservarSalonDeEventos` (UC2) y `reservarAreaRecreacional` (UC3).
    - Incluye: `visualizarDisponibilidadDeEspacios` (UC4).

#### UC-GRD-02: reservarSalonDeEventos
*   **Descripción:** Permite al Residente reservar la reserva de un salón de eventos del condominio, seleccionando fecha, hora y espacio deseado (hereda de reservarAreaComun).
*   **Actor Principal:** Residente
*   **Precondiciones:**
    - El Residente debe haber iniciado sesión en el sistema.
    - El salón de eventos debe encontrarse registrado dentro del sistema de gestión del condominio.
    - El módulo de reservas debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Residente ingresa al módulo de reservas de espacios comunes.
    2. El sistema muestra las opciones de áreas comunes disponibles.
    3. El Residente selecciona la opción de reservar salón de eventos.
    4. El sistema consulta la disponibilidad del salón de eventos seleccionado.
    5. El Residente selecciona la fecha y horario de la reserva.
    6. El sistema valida que la información ingresada sea correcta y que el salón de eventos esté disponible.
    7. El sistema registra la reserva del salón de eventos.
    8. El sistema notifica al Residente la confirmación de la reserva.
*   **Postcondiciones:**
    - La reserva queda registrada en el sistema.
    - El espacio disponible queda no disponible en la fecha y horario seleccionado.
    - El sistema conserva la fecha, hora y datos del Residente asociados en la reserva.

#### UC-GRD-03: reservarAreaRecreacional
*   **Descripción:** Permite al Residente realizar la reserva de un área recreacional, seleccionando fecha, hora y espacio deseado (hereda de reservarAreaComun).
*   **Actor Principal:** Residente
*   **Precondiciones:**
    - El Residente debe haber iniciado sesión en el sistema.
    - Deben existir áreas recreacionales registradas en el sistema de gestión del condominio.
    - El módulo de reservas debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Residente ingresa al módulo de reservas de espacios comunes.
    2. El sistema muestra las opciones de áreas comunes disponibles.
    3. El Residente selecciona la opción de reservar área recreacional.
    4. El sistema consulta la disponibilidad del área recreacional seleccionada.
    5. El Residente selecciona la fecha y horario de la reserva.
    6. El sistema valida que la información ingresada sea correcta y que el salón de eventos esté disponible.
    7. El sistema registra la reserva del área recreacional.
    8. El sistema notifica al Residente la confirmación de la reserva.
*   **Postcondiciones:**
    - La reserva queda registrada en el sistema.
    - El espacio disponible queda no disponible en la fecha y horario seleccionado.
    - El sistema conserva la fecha, hora y datos del Residente asociados en la reserva.

#### UC-GRD-04: visualizarDisponibilidadDeEspacios
*   **Descripción:** Permite al sistema mostrar la disponibilidad actual de los espacios comunes, salón de eventos y áreas recreacionales del condominio al momento de realizar una reserva (incluido en reservarAreaComun).
*   **Actor Principal:** Sistema (Automático)
*   **Precondiciones:**
    - Debe existir al menos un área común, salón de eventos o área recreacional registrada.
    - El módulo de reservas debe encontrarse disponible.
*   **Flujo Principal:**
    1. El sistema recibe la solicitud de consulta de disponibilidad desde el proceso de reserva.
    2. El sistema consulta los registros de reservas existentes para el espacio seleccionado.
    3. El sistema identifica los horarios disponibles y no disponibles.
    4. El sistema muestra al Residente la disponibilidad actualizada del espacio consultado.
*   **Postcondiciones:**
    - La disponibilidad de los espacios queda visible para el Residente.
    - El sistema mantiene actualizada la disponibilidad de espacios.
    - La información de disponibilidad refleja la información real de las reservas registradas.

#### UC-GRD-05: cancelarReserva
*   **Descripción:** Permite al Residente realizar la cancelación de una reserva programada con anticipación.
*   **Actor Principal:** Residente
*   **Precondiciones:**
    - El Residente debe haber iniciado sesión en el sistema.
    - Debe existir al menos una reserva activa registrada a nombre del Residente.
    - El módulo de reservas debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Residente ingresa al módulo de reservas de espacios comunes.
    2. El sistema muestra el listado de reservas activas del Residente.
    3. El Residente selecciona la reserva que desea cancelar.
    4. El sistema solicita confirmación antes de proceder con la cancelación.
    5. El Residente confirma la cancelación.
    6. El sistema cancela la reserva seleccionada.
    7. El sistema actualiza la disponibilidad del espacio liberado.
    8. El sistema notifica al Residente la confirmación de la cancelación.
*   **Postcondiciones:**
    - La reserva queda cancelada dentro del sistema.
    - El espacio liberado queda nuevamente disponible para el horario correspondiente.
    - El sistema conserva un registro de la cancelación realizada.

---

### 3.5. GRE - Módulo Check-in

#### UC-GRE-01: enviarAlerta
*   **Descripción:** Envía una alerta de seguridad (emergencia, incendio, etc.) a todos los residentes de forma inmediata.
*   **Actor Principal:** Administrador, Personal de Seguridad
*   **Destinatario:** Residente
*   **Flujo Principal:**
    1. El actor selecciona enviar alerta de seguridad.
    2. Detalla el tipo de emergencia y mensaje de alerta.
    3. Confirma el envío.
    4. Los residentes reciben la alerta en tiempo real en sus dispositivos.

#### UC-GRE-02: programarVisita
*   **Descripción:** Permite a un residente registrar previamente una visita para facilitar el paso por garita.
*   **Actor Principal:** Residente
*   **Flujo Principal:**
    1. El Residente ingresa los datos del visitante (nombre, CI, fecha, hora, motivo).
    2. El Sistema registra la visita programada.
    3. El Personal de Seguridad puede ver la lista de visitas programadas en garita.

#### UC-GRE-03: registrarEntrada
*   **Descripción:** Registra el ingreso físico de una persona al condominio.
*   **Actor Principal:** Personal de Seguridad
*   **Precondiciones:** El visitante está en la garita.
*   **Flujo Principal:**
    1. El guardia ingresa los datos del visitante y del departamento visitado.
    2. El Sistema notifica al residente de la llegada (incluido).
    3. Se almacena el ingreso en la bitácora histórica (incluido).
*   **Relaciones:**
    *   Incluye: `notificarEntrada` (UC4) y `registrarHistorial` (UC5).
    *   Extiende: `registrarIngresoParqueadero` (UC6) si el visitante ingresa en vehículo.

#### UC-GRE-04: notificarEntrada
*   **Descripción:** Informa inmediatamente al residente que su visita ha cruzado el control (incluido en registrarEntrada).

#### UC-GRE-05: registrarHistorial
*   **Descripción:** Almacena de forma inmutable la fecha, hora e información del visitante en la base de datos (incluido en registrarEntrada).

#### UC-GRE-06: registrarIngresoParqueadero
*   **Descripción:** Asigna un cajón de estacionamiento de visitas libre si la persona ingresa con vehículo.
*   **Actor Principal:** Personal de Seguridad (extiende registrarEntrada)

---

### 3.6. GRF - Módulo de Comunicación

#### UC-GRF-01: enviarMensajeAResidentes
*   **Descripción:** Permite al Administrador enviar mensajes dirigidos únicamente a los residentes del condominio, comunicar información relevante relacionada con la administración del condominio.
*   **Actor Principal:** Administrador
*   **Actores Secundarios:** Residente
*   **Precondiciones:**
    - El Administrador debe haber iniciado sesión en el sistema.
    - Deben existir residentes registrados dentro del sistema de gestión del condominio.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Administrador ingresa al módulo de comunicación.
    2. El sistema muestra las opciones disponibles de mensajería.
    3. El Administrador selecciona la opción de enviar mensaje a residentes.
    4. El Administrador redacta el asunto y el contenido del mensaje.
    5. El sistema valida que la información ingresada sea correcta.
    6. El sistema envía el mensaje a los residentes correspondientes.
    7. El sistema registra el envío dentro del historial de comunicaciones.
*   **Postcondiciones:**
    - El mensaje queda enviado a los residentes correspondientes.
    - El mensaje queda almacenado dentro del historial de comunicaciones del sistema.
    - El sistema conserva la fecha y hora del envío realizado.
*   **Flujos Alternativos:**
    - **A1. Campos obligatorios incompletos:**
        1. El Administrador intenta enviar el mensaje sin completar el asunto o el contenido.
        2. El sistema detecta que existen campos obligatorios vacíos.
        3. El sistema informa que la información requerida está incompleta.
    - **A2. Error durante el envío del mensaje:**
        1. El Administrador confirma el envío del mensaje.
        2. El sistema detecta un error durante el proceso de envío.
        3. El sistema informa que no fue posible completar el envío del mensaje.
        4. El caso de uso finaliza manteniendo la información sin enviar.
*   **Reglas de Negocio:**
    - Solo el Administrador puede enviar mensajes dirigidos a residentes.
    - Los mensajes enviados deben quedar registrados dentro del historial de comunicaciones.
    - El mensaje debe contener obligatoriamente un contenido.
    - Los residentes únicamente pueden visualizar mensajes dirigidos hacia ellos.

#### UC-GRF-02: enviarComunicadoATrabajadores
*   **Descripción:** Permite al Administrador enviar comunicados dirigidos a los trabajadores del condominio.
*   **Actor Principal:** Administrador
*   **Actores Secundarios:** Trabajador
*   **Precondiciones:**
    - El Administrador debe haber iniciado sesión en el sistema.
    - Deben existir trabajadores registrados dentro del sistema de gestión del condominio.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Administrador ingresa al módulo de comunicación.
    2. El sistema muestra las opciones disponibles de comunicación.
    3. El Administrador selecciona la opción de enviar comunicado a trabajadores.
    4. El Administrador redacta el asunto y el contenido del comunicado.
    5. El sistema valida la información ingresada.
    6. El sistema envía el comunicado a los trabajadores correspondientes.
    7. El sistema registra el comunicado dentro del historial de comunicaciones.
*   **Postcondiciones:**
    - El comunicado queda enviado a los trabajadores correspondientes.
    - El comunicado queda almacenado dentro del historial de comunicaciones del sistema.
    - El sistema conserva la fecha y hora del envío realizado.
*   **Flujos Alternativos:**
    - **A1. Campos obligatorios incompletos:**
        1. El Administrador intenta enviar el comunicado sin completar el asunto o el contenido.
        2. El sistema detecta que existen campos obligatorios vacíos.
        3. El sistema informa que la información requerida está incompleta.
    - **A2. Error durante el envío del comunicado:**
        1. El Administrador confirma el envío del comunicado.
        2. El sistema detecta un error durante el proceso de envío.
        3. El sistema informa que no fue posible completar el envío del comunicado.
        4. El caso de uso finaliza manteniendo la información sin enviar.
*   **Reglas de Negocio:**
    - Solo el Administrador puede enviar comunicados dirigidos a trabajadores.
    - Los comunicados enviados deben quedar registrados dentro del historial de comunicaciones.
    - El comunicado debe contener obligatoriamente un asunto y un contenido.
    - Los trabajadores únicamente pueden visualizar comunicados dirigidos hacia ellos.

#### UC-GRF-03: enviarMensajeGlobal
*   **Descripción:** Permite al Administrador enviar mensajes generales a residentes y trabajadores del condominio.
*   **Actor Principal:** Administrador
*   **Actores Secundarios:** Residente, Trabajador
*   **Precondiciones:**
    - El Administrador debe haber iniciado sesión en el sistema.
    - Deben existir usuarios registrados dentro del sistema de gestión del condominio.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Administrador ingresa al módulo de comunicación.
    2. El sistema muestra las opciones disponibles de mensajería.
    3. El Administrador selecciona la opción de enviar mensaje global.
    4. El Administrador redacta el asunto y el contenido del mensaje.
    5. El sistema valida que la información ingresada sea correcta.
    6. El sistema envía el mensaje a todos los usuarios correspondientes.
    7. El sistema registra el mensaje global dentro del historial de comunicaciones.
*   **Postcondiciones:**
    - El mensaje global queda enviado a residentes y trabajadores.
    - El mensaje queda almacenado dentro del historial de comunicaciones del sistema.
    - El sistema conserva la fecha y hora del envío realizado.
*   **Flujos Alternativos:**
    - **A1. Campos obligatorios incompletos:**
        1. El Administrador intenta enviar el mensaje sin completar el asunto o el contenido.
        2. El sistema detecta que existen campos obligatorios vacíos.
        3. El sistema informa que la información requerida está incompleta.
    - **A2. Error durante el envío del mensaje:**
        1. El Administrador confirma el envío del mensaje.
        2. El sistema detecta un error durante el proceso de envío.
        3. El sistema informa que no fue posible completar el envío del mensaje.
        4. El caso de uso finaliza manteniendo la información sin enviar.
*   **Reglas de Negocio:**
    - Solo el Administrador puede enviar mensajes globales.
    - Los mensajes globales deben enviarse a todos los residentes y trabajadores registrados.
    - Los mensajes enviados deben quedar registrados dentro del historial de comunicaciones.
    - El mensaje debe contener obligatoriamente un asunto y un contenido.

#### UC-GRF-04: crearAnuncioGeneral
*   **Descripción:** Permite al Administrador publicar anuncios generales para los usuarios del condominio.
*   **Actor Principal:** Administrador
*   **Actores Secundarios:** Residente, Trabajador
*   **Precondiciones:**
    - El Administrador debe haber iniciado sesión en el sistema.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Administrador ingresa al módulo de comunicación.
    2. El sistema muestra la opción de gestión de anuncios generales.
    3. El Administrador selecciona la opción de crear anuncio general.
    4. El Administrador ingresa el título y contenido del anuncio.
    5. El sistema valida que la información ingresada sea correcta.
    6. El sistema publica el anuncio general.
    7. El sistema registra la creación del anuncio dentro del historial de comunicaciones.
*   **Postcondiciones:**
    - El anuncio general queda publicado dentro del sistema.
    - El anuncio queda disponible para los usuarios correspondientes.
    - El sistema conserva un registro de la fecha y hora de publicación.
*   **Flujos Alternativos:**
    - **A1. Campos obligatorios incompletos:**
        1. El Administrador intenta publicar el anuncio sin completar el título o el contenido.
        2. El sistema detecta que existen campos obligatorios vacíos.
        3. El sistema informa que la información requerida está incompleta.
    - **A2. Error durante la publicación del anuncio:**
        1. El Administrador confirma la publicación del anuncio.
        2. El sistema detecta un error durante el proceso de publicación.
        3. El sistema informa que no fue posible completar la publicación del anuncio.
*   **Reglas de Negocio:**
    - Solo el Administrador puede crear anuncios generales.
    - Los anuncios generales deben quedar registrados dentro del historial de comunicaciones.
    - Todo anuncio general debe contener obligatoriamente un título y un contenido.
    - Los anuncios publicados deben ser visibles para residentes y trabajadores registrados.

#### UC-GRF-05: modificarAnuncioGeneral
*   **Descripción:** Permite al Administrador modificar anuncios generales publicados previamente, con el fin de corregir, actualizar o complementar información comunicada a los usuarios del condominio.
*   **Actor Principal:** Administrador
*   **Actores Secundarios:** Residente, Trabajador
*   **Precondiciones:**
    - El Administrador debe haber iniciado sesión en el sistema.
    - Debe existir al menos un anuncio general registrado dentro del sistema.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Administrador ingresa al módulo de comunicación.
    2. El sistema muestra la lista de anuncios generales registrados.
    3. El Administrador selecciona el anuncio que desea modificar.
    4. El Administrador edita el título o contenido del anuncio.
    5. El sistema valida los cambios realizados.
    6. El Administrador confirma la modificación del anuncio.
    7. El sistema actualiza el anuncio general.
    8. El sistema registra la modificación realizada.
*   **Postcondiciones:**
    - El anuncio general queda actualizado correctamente.
    - El sistema guarda los cambios realizados.
    - El sistema conserva un registro de la fecha y hora de modificación.
*   **Flujos Alternativos:**
    - **A1. Campos obligatorios incompletos:**
        1. El Administrador intenta modificar el anuncio sin completar el título o el contenido.
        2. El sistema detecta que existen campos obligatorios vacíos.
        3. El sistema informa que la información requerida está incompleta.
    - **A2. Anuncio inexistente:**
        1. El Administrador selecciona un anuncio para modificar.
        2. El sistema detecta que el anuncio ya no existe o fue eliminado previamente.
        3. El sistema informa que el anuncio seleccionado no se encuentra disponible.
        4. El caso de uso finaliza sin realizar modificaciones.
    - **A3. Error durante la modificación del anuncio:**
        1. El Administrador confirma la modificación del anuncio.
        2. El sistema detecta un error durante el proceso de actualización.
        3. El sistema informa que no fue posible completar la modificación del anuncio.
        4. El caso de uso finaliza manteniendo la información anterior del anuncio.
*   **Reglas de Negocio:**
    - Solo el Administrador puede modificar anuncios generales.
    - Todo anuncio general debe contener obligatoriamente un título y un contenido.
    - Las modificaciones realizadas deben quedar registradas dentro del historial de comunicaciones.
    - Los cambios realizados en un anuncio deben reflejarse para todos los usuarios correspondientes.

#### UC-GRF-06: eliminarAnuncioGeneral
*   **Descripción:** Permite al Administrador eliminar anuncios generales publicados previamente cuando la información ya no sea necesaria, esté desactualizada o no deba permanecer visible para los usuarios.
*   **Actor Principal:** Administrador
*   **Actores Secundarios:** Residente, Trabajador
*   **Precondiciones:**
    - El Administrador debe haber iniciado sesión en el sistema.
    - Debe existir al menos un anuncio general registrado dentro del sistema.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Administrador ingresa al módulo de comunicación.
    2. El sistema muestra la lista de anuncios generales registrados.
    3. El Administrador selecciona el anuncio que desea eliminar.
    4. El sistema solicita confirmación antes de eliminar el anuncio.
    5. El Administrador confirma la eliminación.
    6. El sistema elimina el anuncio general seleccionado.
    7. El sistema registra la eliminación realizada.
*   **Postcondiciones:**
    - El anuncio general deja de estar disponible para los usuarios.
    - El sistema actualiza la lista de anuncios generales.
    - El sistema conserva un registro de la eliminación realizada.
*   **Flujos Alternativos:**
    - **A1. Cancelación de la eliminación:**
        1. El Administrador selecciona un anuncio para eliminar.
        2. El sistema solicita confirmación de la eliminación.
        3. El Administrador cancela la operation.
        4. El sistema mantiene el anuncio sin modificaciones.
        5. El caso de uso finaliza sin eliminar el anuncio.
    - **A2. Anuncio inexistente:**
        1. El Administrador selecciona un anuncio para eliminar.
        2. El sistema detecta que el anuncio ya no existe o fue eliminado previamente.
        3. El sistema informa que el anuncio seleccionado no se encuentra disponible.
        4. El caso de uso finaliza sin realizar modificaciones.
    - **A3. Error durante la eliminación del anuncio:**
        1. El Administrador confirma la eliminación del anuncio.
        2. El sistema detecta un error durante el proceso de eliminación.
        3. El sistema informa que no fue posible completar la eliminación del anuncio.
        4. El caso de uso finaliza manteniendo el anuncio disponible dentro del sistema.
*   **Reglas de Negocio:**
    - Solo el Administrador puede eliminar anuncios generales.
    - Toda eliminación de anuncios debe quedar registrada dentro del historial de comunicaciones.
    - Los anuncios eliminados dejan de estar visibles para residentes y trabajadores.
    - El sistema debe conservar un registro histórico de las eliminaciones realizadas.

#### UC-GRF-07: crearReporteDeComunicacion
*   **Descripción:** Permite al Administrador generar reportes sobre mensajes, comunicados y anuncios enviados dentro del sistema.
*   **Actor Principal:** Administrador
*   **Actores Secundarios:** Ninguno
*   **Precondiciones:**
    - El Administrador debe haber iniciado sesión en el sistema.
    - Deben existir registros de comunicación dentro del sistema.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El Administrador ingresa al módulo de comunicación.
    2. El sistema muestra la opción de reportes de comunicación.
    3. El Administrador selecciona los filtros del reporte.
    4. El sistema consulta los registros de comunicación disponibles.
    5. El sistema genera el reporte solicitado.
    6. El Administrador visualiza el reporte generado.
*   **Postcondiciones:**
    - El reporte de comunicación queda generado correctamente.
    - El Administrador puede revisar la información de mensajes, comunicados y anuncios.
    - El sistema mantiene disponibles los registros consultados.
*   **Flujos Alternativos:**
    - **A1. No existen registros de comunicación:**
        1. El Administrador solicita generar un reporte de comunicación.
        2. El sistema detecta que no existen registros almacenados dentro del sistema.
        3. El sistema informa que no existen datos disponibles para generar el reporte.
    - **A2. Filtros de búsqueda inválidos:**
        1. El Administrador ingresa filtros de búsqueda incorrectos o incompletos.
        2. El sistema detecta inconsistencias en la información ingresada.
        3. El sistema informa que los filtros proporcionados no son válidos.
        4. El flujo regresa al paso 5 del Flujo Principal.
    - **A3. Error durante la generación del reporte:**
        1. El sistema inicia el proceso de generación del reporte.
        2. El sistema detecta un error durante la consulta o generación de la información.
        3. El sistema informa que no fue posible generar el reporte solicitado.
        4. El caso de uso finaliza sin generar el reporte.
*   **Reglas de Negocio:**
    - Solo el Administrador puede generar reportes de comunicación.
    - Los reportes deben generarse utilizando información almacenada dentro del historial de comunicaciones.
    - El sistema debe conservar la integridad de los registros consultados.
    - Los reportes únicamente deben mostrar información correspondiente al módulo de comunicación.

#### UC-GRF-08: consultarHistorialDeMensajes
*   **Descripción:** Permite consultar el historial de mensajes, comunicados y anuncios registrados en el sistema. El Administrador puede consultar el historial general de comunicaciones, mientras que los residentes y trabajadores pueden consultar únicamente los mensajes o comunicados dirigidos a ellos.
*   **Actor Principal:** Administrador, Residente, Trabajador
*   **Actores Secundarios:** Ninguno
*   **Precondiciones:**
    - El usuario debe haber iniciado sesión en el sistema.
    - Deben existir mensajes, comunicados o anuncios registrados dentro del sistema.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El usuario ingresa al módulo de comunicación.
    2. El sistema muestra la opción de consultar historial de mensajes.
    3. El usuario selecciona los filtros de consulta disponibles.
    4. El sistema valida los permisos del usuario.
    5. El sistema muestra los mensajes, comunicados o anuncios correspondientes según el rol del usuario.
*   **Postcondiciones:**
    - El historial de mensajes queda visualizado correctamente.
    - El usuario accede únicamente a la información permitida según su rol.
    - El sistema mantiene protegida la información que no corresponde al usuario.
*   **Flujos Alternativos:**
    - **A1. No existen registros de comunicación:**
        1. El usuario solicita consultar el historial de mensajes.
        2. El sistema detecta que no existen registros de comunicación almacenados.
        3. El sistema informa que no existen mensajes o anuncios disponibles para visualizar.
        4. El caso de uso finaliza sin mostrar información.
    - **A2. Filtros de búsqueda inválidos:**
        1. El usuario ingresa filtros de búsqueda incorrectos o incompletos.
        2. El sistema detecta inconsistencias en la información ingresada.
        3. El sistema informa que los filtros proporcionados no son válidos.
    - **A3. Acceso no permitido:**
        1. El usuario intenta acceder a mensajes o anuncios que no le corresponden según su rol.
        2. El sistema detecta que el usuario no posee permisos suficientes.
        3. El sistema restringe el acceso a la información solicitada.
        4. El caso de uso finaliza manteniendo protegida la información del sistema.
*   **Reglas de Negocio:**
    - El Administrador puede consultar el historial general de comunicaciones.
    - Los residentes y trabajadores únicamente pueden consultar mensajes o anuncios dirigidos hacia ellos.
    - El historial de comunicaciones debe conservarse almacenado dentro del sistema.
    - El acceso a la información debe respetar los permisos definidos según el rol del usuario.

#### UC-GRF-09: registrarHistorialDeComunicaciones (Sistema)
*   **Descripción:** Permite al sistema almacenar automáticamente el historial de mensajes, comunicados y anuncios realizados dentro del módulo de comunicación.
*   **Actor Principal:** Sistema (Automático)
*   **Actores Secundarios:** Administrador, Residente, Trabajador
*   **Precondiciones:**
    - Debe existir una acción de comunicación realizada dentro del sistema.
    - El módulo de comunicación debe encontrarse disponible.
    - Deben existir usuarios registrados dentro del sistema de gestión del condominio.
*   **Flujo Principal:**
    1. El sistema detecta una acción de comunicación realizada.
    2. El sistema identifica el tipo de comunicación registrada.
    3. El sistema almacena la información correspondiente.
    4. El sistema actualiza el historial de comunicaciones.
*   **Postcondiciones:**
    - La comunicación queda almacenada dentro del historial del sistema.
    - El historial de comunicaciones queda actualizado.
    - El sistema conserva la información necesaria para futuras consultas o reportes.
*   **Flujos Alternativos:**
    - **A1. Información incompleta:**
        1. El sistema detecta que la información asociada a la comunicación se encuentra incompleta.
        2. El sistema impide registrar la comunicación dentro del historial.
        3. El sistema conserva el estado anterior del historial de comunicaciones.
        4. El caso de uso finaliza sin registrar la información.
    - **A2. Error durante el registro del historial:**
        1. El sistema inicia el proceso de almacenamiento de la comunicación.
        2. El sistema detecta un error durante el registro de la información.
        3. El sistema mantiene la información sin almacenar dentro del historial.
        4. El caso de uso finaliza sin actualizar el historial de comunicaciones.
*   **Reglas de Negocio:**
    - Toda comunicación realizada dentro del módulo debe quedar registrada dentro del historial de comunicaciones.
    - El historial de comunicaciones debe conservar la fecha y hora de cada registro realizado.
    - El sistema no debe permitir registros duplicados de una misma comunicación.
    - La información almacenada dentro del historial debe mantenerse disponible para futuras consultas y reportes.

#### UC-GRF-10: notificarMensajeRecibido (Sistema)
*   **Descripción:** Permite al sistema notificar automáticamente a los usuarios cuando reciben un mensaje, comunicado o anuncio.
*   **Actor Principal:** Sistema (Automático)
*   **Actores Secundarios:** Administrador, Residente, Trabajador
*   **Precondiciones:**
    - Debe existir un mensaje, comunicado o anuncio enviado dentro del sistema.
    - Debe existir al menos un destinatario registrado dentro del sistema.
    - El módulo de comunicación debe encontrarse disponible.
*   **Flujo Principal:**
    1. El sistema detecta un nuevo mensaje, comunicado o anuncio enviado.
    2. El sistema identifica a los destinatarios correspondientes.
    3. El sistema genera la notificación respectiva.
    4. El sistema envía la notificación a los usuarios destinatarios.
    5. El sistema registra la notificación realizada.
*   **Postcondiciones:**
    - Los usuarios destinatarios reciben la notificación correspondiente.
    - El sistema registra la notificación generada.
    - La comunicación queda disponible para consulta dentro del sistema.
*   **Flujos Alternativos:**
    - **A1. Destinatario inexistente:**
        1. El sistema intenta identificar a los destinatarios correspondientes.
        2. El sistema detecta que uno o varios destinatarios no existen dentro del sistema.
        3. El sistema omite los destinatarios inválidos.
        4. El sistema continúa el proceso únicamente con los destinatarios válidos.
    - **A2. Error durante el envío de la notificación:**
        1. El sistema inicia el proceso de envío de notificaciones.
        2. El sistema detecta un error durante el envío de una o varias notificaciones.
        3. El sistema registra el error correspondiente.
        4. El caso de uso finaliza manteniendo la comunicación almacenada dentro del sistema.
    - **A3. Módulo de comunicación no disponible:**
        1. El sistema intenta generar las notificaciones correspondientes.
        2. El sistema detecta que el módulo de comunicación no se encuentra disponible.
        3. El sistema suspende temporalmente el proceso de notificación.
        4. El caso de uso finaliza sin enviar las notificaciones correspondientes.
*   **Reglas de Negocio:**
    - Toda comunicación enviada debe generar una notificación para los destinatarios correspondientes.
    - Las notificaciones deben quedar registradas dentro del historial de comunicaciones.
    - El sistema únicamente debe enviar notificaciones a usuarios registrados dentro del sistema.
    - Las notificaciones deben estar asociadas a una comunicación previamente registrada.
