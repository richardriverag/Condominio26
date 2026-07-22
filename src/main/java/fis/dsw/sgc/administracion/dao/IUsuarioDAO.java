package fis.dsw.sgc.administracion.dao;

import fis.dsw.sgc.administracion.model.Usuario;
import fis.dsw.sgc.usuarios.dto.ResidenteFachadaDTO;
import java.util.List;

public interface IUsuarioDAO {
    int guardar(Usuario usuario);
    void actualizarInformacion(int idUsuario, String nombres, String apellidos, String correo);
    void actualizarPerfil(int idUsuario, String telefono, String direccion, String fotoPerfil);
    Usuario buscarPorId(int idUsuario);
    Usuario buscarPorCorreo(String correo);
    List<Usuario> listarTodos();
    boolean existeCorreo(String correo);
    boolean existeCedula(String cedula);
    ResidenteFachadaDTO buscarResidentePorCedula(String cedula);
}
