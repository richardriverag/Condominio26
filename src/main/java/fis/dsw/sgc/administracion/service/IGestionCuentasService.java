package fis.dsw.sgc.administracion.service;

import fis.dsw.sgc.administracion.dto.RegistrarCuentaDTO;
import fis.dsw.sgc.administracion.model.EstadoCuenta;
import fis.dsw.sgc.administracion.model.NombreRol;
import fis.dsw.sgc.administracion.model.Usuario;

/**
 * Servicio interno del módulo administración (GRB) para las operaciones de
 * gestión de cuentas que consumen las pantallas del propio módulo.
 * Todos los métodos operan contra la base de datos SQLite vía los DAOs.
 * Las validaciones de negocio fallan con GestionCuentasException.
 */
public interface IGestionCuentasService {

    void registrarCuenta(RegistrarCuentaDTO dto);

    Usuario buscarPorCorreo(String correo);

    void actualizarInformacionCuenta(int idUsuario, String nombre, String apellido, String correo);

    void cambiarEstadoCuenta(int idCuenta, EstadoCuenta estado);

    void asignarRol(int idUsuario, NombreRol rol);

    void definirPermiso(NombreRol rol, String nombrePermiso, String recurso);

    void actualizarPerfil(int idUsuario, String telefono, String direccion, String fotoPerfil);

    String generarTokenRecuperacion(String correo);

    void restablecerContrasena(String correo, String codigo, String nuevaContrasena);
}
