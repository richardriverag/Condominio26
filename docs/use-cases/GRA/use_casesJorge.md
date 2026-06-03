## Requisito: solicitarPagoEnCuotas
El Sistema permitirá a un residente solicitar el pago en cuotas.
Este escenario describe el camino ideal donde el residente realiza 
la solicitud de manera correcta y cumple con todos los parámetros del Sistema.

**Escenario Básico:**

1. El Residente se encuentra autenticado en el Sistema.
2. El Residente posee al menos una obligación de pago o cargo extraordinario en estado "PENDIENTE" o "EN MORA".
3. El Residente ingresa al módulo de "Mis Obligaciones" y selecciona una deuda específica elegible para financiamiento (por ejemplo, un cargo extraordinario).
4. El Residente hace clic en la opción "Solicitar Pago en Cuotas".
5. El Sistema despliega un formulario solicitando la información del financiamiento: Número de cuotas deseadas (entre 2 y 12 meses)
6. El Residente selecciona el número de cuotas (entero mayor a uno y menor o igual a 12 ej. 6) y confirma la solicitud.
7. El Sistema calcula automáticamente el valor individual de cada cuota dividiendo el valor total de la deuda para el número de cuotas ingresadas, redondeando a dos decimales.
8. El Sistema muestra una vista previa del plan de pagos detallando: ID del plan, ID del residente, obligación asociada, valor total de la deuda, número de cuotas, valor de cada cuota, fecha de inicio y fecha de finalización.
9. El Residente revisa y acepta los términos del diferimiento.
10. El Sistema registra el plan de pagos con estado "SOLICITADO", genera una notificación para el Administrador, y muestra un mensaje de éxito en pantalla.
11. El plan de pagos queda registrado en la base de datos.

**Escenario(s) alternativo(s):**

**Escenario alternativo 1**
1. El Residente se encuentra autenticado en el sistema.
2. El Residente no posee obligaciones de pago o cargo extraordinario en estado "PENDIENTE" o "EN MORA".
3. El Residente intenta solicitar un diferimiento sobre una obligación cuyo estado es "PAGADO".
4. El Sistema verifica el estado de la obligación en la base de datos.
5. El Sistema al detectar que el estado es "PAGADO", oculta o inhabilita por completo el botón "Solicitar Pago en Cuotas" para esa transacción específica, impidiendo el inicio del flujo.

**Escenario alternativo 2**
1. El Residente se encuentra autenticado en el sistema.
2. El Residente posee al menos una obligación de pago o cargo extraordinario en estado "PENDIENTE" o "EN MORA".
3. El Residente ingresa al módulo de "Mis Obligaciones" y selecciona una deuda específica elegible para financiamiento (por ejemplo, un cargo extraordinario).
4. El Residente intenta solicitar un plan de pagos para una obligación que ya se encuentra asociada a un plan previo en estado "SOLICITADO" o "APROBADO".
5. El Sistema, al procesar la solicitud, realiza una consulta cruzada en la tabla de planes de pago.
6. El Sistema interrumpe el proceso y despliega una alerta en pantalla: "Acción no permitida: Esta obligación ya cuenta con un plan de pagos activo o en proceso de aprobación".
7. El Sistema redirecciona al usuario al módulo de "Mis Obligaciones".

**Escenario alternativo 3**
1. El Residente se encuentra autenticado en el sistema.
2. El Residente posee al menos una obligación de pago o cargo extraordinario en estado "PENDIENTE" o "EN MORA".
3. El Residente ingresa al módulo de "Mis Obligaciones" y selecciona una deuda específica elegible para financiamiento (por ejemplo, un cargo extraordinario).
4. El Residente hace clic en la opción "Solicitar Pago en Cuotas".
5. El Sistema despliega un formulario solicitando la información del financiamiento: Número de cuotas deseadas (entre 2 y 12 meses)
6. El Residente selecciona el número de cuotas (entero mayor a uno y menor o igual a 12 ej. 6) y confirma la solicitud.
7. El Sistema calcula automáticamente el valor individual de cada cuota dividiendo el valor total de la deuda para el número de cuotas ingresadas, redondeando a dos decimales.
8. El Sistema muestra una vista previa del plan de pagos detallando: ID del plan, ID del residente, obligación asociada, valor total de la deuda, número de cuotas, valor de cada cuota, fecha de inicio y fecha de finalización.
9. el Residente decide no aceptar la simulación de las cuotas o el valor individual calculado por el sistema.
10. El Residente hace clic en el botón "Cancelar Solicitud".
11. Sistema: Revierte los cálculos temporales en memoria, no realiza ningún registro en la base de datos y regresa al usuario al módulo de "Mis Obligaciones".

## Requisito: eliminarFormaDePagoRecurrente
El sistema permitira eliminar la forma de pago recurrente.
Este escenario describe el camino ideal en el que el usuario elimina una tarjeta o cuenta bancaria configurada para débitos automáticos sin inconvenientes.

**Escenario Básico:**

1. El usuario (Residente) se encuentra autenticado en el sistema. 
2. El usuario tiene al menos una forma de pago configurada en estado "RECURRENTE" o "ACTIVA" (débito automático para alícuotas o mensualidades).
3. El residente ingresa al módulo de "Métodos de Pago".
4. El Sistema consulta y despliega la lista de tarjetas o cuentas asociadas, identificando claramente cuál de ellas está marcada como "Pago Recurrente / Débito Automático".
5. El Residente selecciona el método de pago recurrente y hace clic en el botón "Eliminar Forma de Pago Recurrente" (o "Desvincular Débito Automático").
6. El Sistema despliega un mensaje emergente de confirmación advirtiendo sobre las implicaciones (ej. «Si elimina este método, sus próximas facturas deberán ser canceladas de forma manual antes de la fecha de vencimiento»). El mensaje incluye dos botones: "Confirmar" y "Cancelar".
7. El Residente hace clic en "Confirmar".
8. El Sistema se comunica de forma segura mediante API con la pasarela de pagos para dar de baja el token de recurrencia.
9. La pasarela responde con éxito. El sistema actualiza el estado de la forma de pago en la base de datos interna (la elimina o cambia su estado a "INDIVIDUAL / MANUAL").
10. El Sistema muestra un mensaje de éxito en pantalla: «La recurrencia de pago ha sido eliminada exitosamente».
11. El Sistema envía una notificación automática (correo electrónico) al Residente, confirmando la cancelación del débito automático.
12. Los futuros cargos del residente ya no se debitarán automáticamente y pasarán al flujo de facturación manual en estado "PENDIENTE".

**Escenario(s) alternativo(s):**

**Escenario alternativo 1**

1. El usuario (Residente) se encuentra autenticado en el sistema.
2. El usuario tiene al menos una forma de pago configurada en estado "RECURRENTE" o "ACTIVA" (débito automático para alícuotas o mensualidades).
3. El Residente ingresa al módulo de "Métodos de Pago".
4. El Sistema consulta y despliega la lista de tarjetas o cuentas asociadas, identificando claramente cuál de ellas está marcada como "Pago Recurrente / Débito Automático".
5. El Residente selecciona el método de pago recurrente y hace clic en el botón "Eliminar Forma de Pago Recurrente" (o "Desvincular Débito Automático").
6. El Sistema despliega un mensaje emergente de confirmación advirtiendo sobre las implicaciones (ej. «Si elimina este método, sus próximas facturas deberán ser canceladas de forma manual antes de la fecha de vencimiento»). El mensaje incluye dos botones: "Confirmar" y "Cancelar".
7. El Residente hace clic en el botón "Cancelar" en el mensaje emergente.
8. El Sistema cierra la ventana de confirmación, no realiza modificaciones en la base de datos ni en la pasarela de pagos, y mantiene al usuario en la interfaz de "Métodos de Pago" con la recurrencia intacta.

**Escenario alternativo 2**

1. El usuario (Residente) se encuentra autenticado en el sistema.
2. El usuario tiene al menos una forma de pago configurada en estado "RECURRENTE" o "ACTIVA" (débito automático para alícuotas o mensualidades).
3. El Residente ingresa al módulo de "Métodos de Pago".
4. El Sistema consulta y despliega la lista de tarjetas o cuentas asociadas, identificando claramente cuál de ellas está marcada como "Pago Recurrente / Débito Automático".
5. El Residente selecciona el método de pago recurrente y hace clic en el botón "Eliminar Forma de Pago Recurrente" (o "Desvincular Débito Automático").
6. El Sistema despliega un mensaje emergente de confirmación advirtiendo sobre las implicaciones (ej. «Si elimina este método, sus próximas facturas deberán ser canceladas de forma manual antes de la fecha de vencimiento»). El mensaje incluye dos botones: "Confirmar" y "Cancelar".
7. El Residente hace clic en "Confirmar".
8. El Sistema se comunica de forma segura mediante API con la pasarela de pagos para dar de baja el token de recurrencia.
9. El Sistema espera la respuesta de la pasarela durante un tiempo límite (Timeout). Al no recibir respuesta o recibir un código de error de red (ej. HTTP 503), el sistema revierte los cambios locales en memoria.
10. El Sistema despliega una notificación de error técnico en la interfaz: «Temporalmente no podemos procesar su solicitud. Su método de pago recurrente sigue activo. Por favor, intente más tarde o contacte al administrador».
11. El Sistema registra el incidente en el log de errores interno para auditoría técnica.

## Requisito: eliminarDeuda

El sistema permitirá eliminar una deuda
Este escenario describe el camino ideal en el que un usuario con privilegios de Administrador elimina una deuda (ej. una alícuota ordinaria) que fue registrada por error y que aún no ha sido procesada ni cancelada.

**Escenario Básico:**

1. El usuario se encuentra autenticado en el sistema con el rol de Administrador.
2. La deuda del usuario Residente existe en el sistema y se encuentra en estado "PENDIENTE".
3. El Administrador ingresa al módulo de "Gestión de Obligaciones" o "Cuentas por Cobrar".
4. El Administrador busca la deuda del residente filtrando por ID de residente o por número de cédula.
5. El Sistema realiza la consulta y despliega en una tabla el listado de deudas asociadas que cumplen con los criterios de búsqueda.
6. El Administrador ubica la deuda específica (ej. Alícuota Ordinaria en estado "PENDIENTE") y hace clic en el botón "Eliminar Deuda".
7. El Sistema despliega una ventana emergente de confirmación de seguridad que solicita obligatoriamente:
- La confirmación explícita de la acción (Botones "Confirmar" / "Cancelar").
- Un campo de texto para ingresar el Motivo o Justificación de la eliminación.
8. El Administrador introduce el motivo del borrado (ej. «Cargo de alícuota duplicado por error del sistema») y hace clic en "Confirmar".
9. El Sistema valida que el campo de motivo contenga texto y verifica en tiempo real que la deuda mantenga el estado "PENDIENTE" en la base de datos.
10. El Sistema ejecuta un borrado lógico de la obligación (cambia su estado interno a "ELIMINADA" o "ANULADA" para preservar la integridad referencial y no romper la secuencia cronológica).
11. El Sistema registra de forma automática un asiento en la bitácora de auditoría, guardando: fecha y hora exacta, ID de la deuda, tipo de concepto (alícuota), valor monetario eliminado, motivo ingresado y el usuario Administrador responsable.
12. El Sistema cierra la ventana emergente, actualiza la tabla de la interfaz y muestra un mensaje de éxito: «La deuda ha sido eliminada y auditada correctamente».
13. El Sistema recalcula automáticamente el saldo acumulado total del residente (restando el valor de la deuda eliminada).

**Escenario(s) alternativo(s):**

**Escenario alternativo 1**
1. El usuario se encuentra autenticado en el sistema con el rol de Administrador.
2. La deuda del usuario Residente existe en el sistema y se encuentra en estado "PENDIENTE".
3. El Administrador ingresa al módulo de "Gestión de Obligaciones" o "Cuentas por Cobrar".
4. El Administrador busca la deuda del residente filtrando por ID de residente o por número de cédula.
5. El Sistema realiza la consulta y despliega en una tabla el listado de deudas asociadas que cumplen con los criterios de búsqueda.
6. El Administrador ubica la deuda específica (ej. Alícuota Ordinaria en estado "PENDIENTE") y hace clic en el botón "Eliminar Deuda".
7. El Administrador intenta eliminar una deuda por cargo extraordinario o alícuota que previamente fue reestructurada y dividida en cuotas mensuales.
8. El Sistema detecta que el ID de la deuda está vinculado a un plan de pagos activo.
9. El Sistema Interrumpe el flujo de eliminación y muestra una alerta en la interfaz: «Acción restringida: Esta obligación está asociada a un convenio de pago en cuotas vigente. Debe disolver o cancelar el plan de pagos antes de poder eliminar la deuda origen».
10. El Sistema retorna al módulo de inicio del usuario Administrador.

## Requisito: generarCertificadoDeNoDeudor
El sistema permitirá generar un certificado de no deudor.
Este escenario describe el camino ideal en el que un residente solvente solicita su certificado y el sistema lo genera de manera automática y exitosa.

**Escenario Básico:**

1. El usuario (Residente o Administrador) se encuentra autenticado en el sistema.
2. El Residente cuenta con un expediente registrado (ID de residente y departamento asignado).
3. El usuario ingresa al módulo de "Certificados" o "Estado de Cuenta".
4. El usuario hace clic en el botón "Generar Certificado de No Deudor".
5. El Sistema identifica el ID del residente e inicia una consulta en tiempo real en la base de datos de obligaciones financieras asociadas a dicho usuario.
6. El Sistema verifica que el residente no tenga ninguna obligación en estado "PENDIENTE" o "EN MORA".
7. El Sistema compila la información requerida para el documento: Nombre completo del residente (validando la cadena de texto), Cédula de identidad, ID de departamento, fecha y hora de emisión.
8. El Sistema genera un archivo digital en formato PDF no editable con el Certificado de No Deudor / Solvencia.
9. El Sistema registra en la bitácora de auditoría que se emitió un certificado de solvencia para el residente con su respectiva fecha y hora.
10. El Sistema despliega el documento en pantalla y habilita de forma automática las opciones de "Descargar" e "Imprimir".
11. El Sistema registra la acción en un historial de logs.




**Escenario(s) alternativo(s):**

**Escenario alternativo 1**

1. El usuario (Residente o Administrador) se encuentra autenticado en el sistema.
2. El Residente cuenta con un expediente registrado (ID de residente y departamento asignado).
3. El usuario ingresa al módulo de "Certificados" o "Estado de Cuenta".
4. El usuario hace clic en el botón "Generar Certificado de No Deudor".
5. El Sistema identifica el ID del residente e inicia una consulta en tiempo real en la base de datos de obligaciones financieras asociadas a dicho usuario.
6. El Sistema verifica que el residente no tenga ninguna obligación en estado "PENDIENTE" o "EN MORA", pero detecta que tiene un plan de pagos diferido vigente (cuotas futuras que aún no se han vencido).
7. Sistema: Evalúa si el plan de pagos se encuentra al día.
8. Si las cuotas facturadas hasta la fecha están pagadas, pero quedan cuotas futuras por vencer, el sistema detiene la emisión regular y muestra una advertencia: «Usted posee un plan de pagos activo. Para emitir un certificado de liberación total, debe liquidar las cuotas diferidas anticipadamente. ¿Desea generar un certificado parcial con observaciones?».
9. El usuario selecciona el botón "Aceptar".
10. El Sistema genera el certificado en PDF añadiendo una cláusula de observación obligatoria que indica: "El residente se encuentra al día, manteniendo un convenio de pago activo en cuotas pendientes".
11. El Sistema compila la información requerida para el documento: Nombre completo del residente (validando la cadena de texto), Cédula de identidad, ID de departamento, fecha y hora de emisión.
12. El Sistema genera un archivo digital en formato PDF no editable con el Certificado de No Deudor / Solvencia.
13. El Sistema registra en la bitácora de auditoría que se emitió un certificado de solvencia para el residente con su respectiva fecha y hora.
14. El Sistema despliega el documento en pantalla y habilita de forma automática las opciones de "Descargar" e "Imprimir".
15. El Sistema registra la acción en un historial de logs.



