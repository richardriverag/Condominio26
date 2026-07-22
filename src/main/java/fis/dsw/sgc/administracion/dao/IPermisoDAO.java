package fis.dsw.sgc.administracion.dao;

import java.util.List;

public interface IPermisoDAO {
    boolean existePermisoParaCuenta(int idCuenta, String nombrePermiso);
    List<String> listarPermisosPorCuenta(int idCuenta);
    void crearPermiso(String nombre, String recurso, String accion, String descripcion);
    void asignarPermisoARol(int idRol, String nombrePermiso);
}
