package fis.dsw.sgc.administracion.dao;

import fis.dsw.sgc.administracion.model.NombreRol;
import fis.dsw.sgc.administracion.model.Rol;
import fis.dsw.sgc.conexion_bd.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAOMySQL implements IRolDAO {

    @Override
    public List<Rol> listarTodos() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT nombre, descripcion FROM rol WHERE estado = 'ACTIVO' ORDER BY nombre";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setNombre(NombreRol.valueOf(rs.getString("nombre")));
                rol.setDescripcion(rs.getString("descripcion"));
                roles.add(rol);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar roles: " + e.getMessage());
        }
        return roles;
    }

    @Override
    public void asignarRolAUsuario(int idUsuario, int idRol) {
        String sql = "INSERT OR IGNORE INTO usuario_rol (id_usuario, id_rol) VALUES (?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idRol);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al asignar rol a usuario: " + e.getMessage());
        }
    }

    @Override
    public int buscarIdPorNombre(NombreRol nombre) {
        String sql = "SELECT id_rol FROM rol WHERE nombre = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_rol");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar id de rol por nombre: " + e.getMessage());
        }
        return -1;
    }
}
