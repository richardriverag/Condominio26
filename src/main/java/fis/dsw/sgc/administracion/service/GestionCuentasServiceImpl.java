package fis.dsw.sgc.administracion.service;

import fis.dsw.sgc.administracion.dao.CuentaDAOMySQL;
import fis.dsw.sgc.administracion.dao.ICuentaDAO;
import fis.dsw.sgc.administracion.dao.IPermisoDAO;
import fis.dsw.sgc.administracion.dao.IRolDAO;
import fis.dsw.sgc.administracion.dao.ITokenRecuperacionDAO;
import fis.dsw.sgc.administracion.dao.IUsuarioDAO;
import fis.dsw.sgc.administracion.dao.PermisoDAOMySQL;
import fis.dsw.sgc.administracion.dao.RolDAOMySQL;
import fis.dsw.sgc.administracion.dao.TokenRecuperacionDAOMySQL;
import fis.dsw.sgc.administracion.dao.UsuarioDAOMySQL;
import fis.dsw.sgc.administracion.dto.RegistrarCuentaDTO;
import fis.dsw.sgc.administracion.exception.GestionCuentasException;
import fis.dsw.sgc.administracion.model.EstadoCuenta;
import fis.dsw.sgc.administracion.model.NombreRol;
import fis.dsw.sgc.administracion.model.Usuario;
import fis.dsw.sgc.administracion.util.PasswordHasher;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class GestionCuentasServiceImpl implements IGestionCuentasService {

    private final IUsuarioDAO usuarioDAO;
    private final ICuentaDAO cuentaDAO;
    private final IRolDAO rolDAO;
    private final IPermisoDAO permisoDAO;
    private final ITokenRecuperacionDAO tokenDAO;
    private final SecureRandom random = new SecureRandom();

    public GestionCuentasServiceImpl() {
        this.usuarioDAO = new UsuarioDAOMySQL();
        this.cuentaDAO = new CuentaDAOMySQL();
        this.rolDAO = new RolDAOMySQL();
        this.permisoDAO = new PermisoDAOMySQL();
        this.tokenDAO = new TokenRecuperacionDAOMySQL();
    }

    @Override
    public void registrarCuenta(RegistrarCuentaDTO dto) {
        if (dto.getRol() == null) {
            throw new GestionCuentasException("Debe seleccionar un rol para la cuenta.");
        }
        if (usuarioDAO.existeCorreo(dto.getCorreo())) {
            throw new GestionCuentasException("Ya existe una cuenta registrada con ese correo.");
        }
        if (usuarioDAO.existeCedula(dto.getCedula())) {
            throw new GestionCuentasException("Ya existe un usuario con esa cédula.");
        }
        if (cuentaDAO.existeNombreUsuario(dto.getNombreUsuario())) {
            throw new GestionCuentasException("Ya existe ese nombre de usuario.");
        }

        int idRol = rolDAO.buscarIdPorNombre(dto.getRol());
        if (idRol <= 0) {
            throw new GestionCuentasException("El rol seleccionado no existe en el sistema.");
        }

        Usuario usuario = new Usuario();
        usuario.setCedula(dto.getCedula());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());

        int idUsuario = usuarioDAO.guardar(usuario);
        if (idUsuario <= 0) {
            throw new GestionCuentasException("No se pudo registrar el usuario.");
        }

        cuentaDAO.crearCuenta(idUsuario, dto.getNombreUsuario(), PasswordHasher.hash(dto.getContrasena()));
        rolDAO.asignarRolAUsuario(idUsuario, idRol);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioDAO.buscarPorCorreo(correo);
    }

    @Override
    public void actualizarInformacionCuenta(int idUsuario, String nombre, String apellido, String correo) {
        Usuario existente = usuarioDAO.buscarPorCorreo(correo);
        if (existente != null && existente.getIdUsuario() != idUsuario) {
            throw new GestionCuentasException("El correo ingresado ya está registrado por otro usuario.");
        }
        usuarioDAO.actualizarInformacion(idUsuario, nombre, apellido, correo);
    }

    @Override
    public void cambiarEstadoCuenta(int idCuenta, EstadoCuenta estado) {
        cuentaDAO.actualizarEstado(idCuenta, estado);
    }

    @Override
    public void asignarRol(int idUsuario, NombreRol rol) {
        int idRol = rolDAO.buscarIdPorNombre(rol);
        if (idRol <= 0) {
            throw new GestionCuentasException("El rol seleccionado no existe en el sistema.");
        }
        rolDAO.asignarRolAUsuario(idUsuario, idRol);
    }

    @Override
    public void definirPermiso(NombreRol rol, String nombrePermiso, String recurso) {
        int idRol = rolDAO.buscarIdPorNombre(rol);
        if (idRol <= 0) {
            throw new GestionCuentasException("El rol seleccionado no existe en el sistema.");
        }
        permisoDAO.crearPermiso(nombrePermiso, recurso, null, null);
        permisoDAO.asignarPermisoARol(idRol, nombrePermiso);
    }

    @Override
    public void actualizarPerfil(int idUsuario, String telefono, String direccion, String fotoPerfil) {
        usuarioDAO.actualizarPerfil(idUsuario, telefono, direccion, fotoPerfil);
    }

    @Override
    public String generarTokenRecuperacion(String correo) {
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        if (usuario == null) {
            throw new GestionCuentasException("No existe una cuenta asociada a ese correo electrónico.");
        }
        String codigo = String.format("%06d", random.nextInt(1_000_000));
        String fechaExpiracion = LocalDateTime.now().plusHours(1).toString();
        tokenDAO.guardarToken(usuario.getCuenta().getIdCuenta(), codigo, fechaExpiracion);
        return codigo;
    }

    @Override
    public void restablecerContrasena(String correo, String codigo, String nuevaContrasena) {
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        if (usuario == null) {
            throw new GestionCuentasException("No existe una cuenta asociada a ese correo electrónico.");
        }
        int idCuenta = usuario.getCuenta().getIdCuenta();
        Integer idToken = tokenDAO.buscarIdTokenValido(idCuenta, codigo, LocalDateTime.now().toString());
        if (idToken == null) {
            throw new GestionCuentasException("Token inválido o expirado.");
        }
        cuentaDAO.actualizarContrasena(idCuenta, PasswordHasher.hash(nuevaContrasena));
        tokenDAO.marcarUtilizado(idToken);
    }
}
