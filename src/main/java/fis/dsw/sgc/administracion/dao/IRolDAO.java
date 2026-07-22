package fis.dsw.sgc.administracion.dao;

import fis.dsw.sgc.administracion.model.NombreRol;
import fis.dsw.sgc.administracion.model.Rol;
import java.util.List;

public interface IRolDAO {
    List<Rol> listarTodos();
    void asignarRolAUsuario(int idUsuario, int idRol);
    int buscarIdPorNombre(NombreRol nombre);
}
