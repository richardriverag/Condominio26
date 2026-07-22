package fis.dsw.sgc.administracion.dao;

import fis.dsw.sgc.administracion.model.Cuenta;
import fis.dsw.sgc.administracion.model.EstadoCuenta;

public interface ICuentaDAO {
    void crearCuenta(int idUsuario, String nombreUsuario, String hashContrasena);
    void actualizarEstado(int idCuenta, EstadoCuenta estado);
    void actualizarContrasena(int idCuenta, String hashContrasena);
    boolean existeNombreUsuario(String nombreUsuario);
    Cuenta buscarPorIdUsuario(int idUsuario);
    Cuenta autenticar(String correo, String contrasena);
}
