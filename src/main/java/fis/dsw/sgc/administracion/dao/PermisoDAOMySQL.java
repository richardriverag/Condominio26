package fis.dsw.sgc.administracion.dao;

import fis.dsw.sgc.conexion_bd.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermisoDAOMySQL implements IPermisoDAO {

    @Override
    public boolean existePermisoParaCuenta(int idCuenta, String nombrePermiso) {
        String sql = "SELECT 1 FROM cuenta c "
                + "JOIN usuario_rol ur ON ur.id_usuario = c.id_usuario "
                + "JOIN rol_permiso rp ON rp.id_rol = ur.id_rol "
                + "JOIN permiso p ON p.id_permiso = rp.id_permiso "
                + "WHERE c.id_cuenta = ? AND p.nombre = ? LIMIT 1";

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCuenta);
            pstmt.setString(2, nombrePermiso);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar permiso: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> listarPermisosPorCuenta(int idCuenta) {
        List<String> permisos = new ArrayList<>();
        String sql = "SELECT DISTINCT p.nombre FROM cuenta c "
                + "JOIN usuario_rol ur ON ur.id_usuario = c.id_usuario "
                + "JOIN rol_permiso rp ON rp.id_rol = ur.id_rol "
                + "JOIN permiso p ON p.id_permiso = rp.id_permiso "
                + "WHERE c.id_cuenta = ? ORDER BY p.nombre";

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCuenta);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    permisos.add(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar permisos por cuenta: " + e.getMessage());
        }
        return permisos;
    }

    @Override
    public void crearPermiso(String nombre, String recurso, String accion, String descripcion) {
        String sql = "INSERT OR IGNORE INTO permiso (nombre, recurso, accion, descripcion) VALUES (?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, recurso);
            pstmt.setString(3, accion);
            pstmt.setString(4, descripcion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al crear permiso: " + e.getMessage());
        }
    }

    @Override
    public void asignarPermisoARol(int idRol, String nombrePermiso) {
        String sql = "INSERT OR IGNORE INTO rol_permiso (id_rol, id_permiso) "
                + "SELECT ?, id_permiso FROM permiso WHERE nombre = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idRol);
            pstmt.setString(2, nombrePermiso);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al asignar permiso a rol: " + e.getMessage());
        }
    }
}
