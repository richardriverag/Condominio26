package fis.dsw.sgc.administracion.dao;

import fis.dsw.sgc.conexion_bd.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenRecuperacionDAOMySQL implements ITokenRecuperacionDAO {

    @Override
    public void guardarToken(int idCuenta, String codigo, String fechaExpiracion) {
        String sql = "INSERT INTO token_restablecimiento (id_cuenta, codigo, fecha_expiracion, intentos, utilizado) "
                + "VALUES (?, ?, ?, 0, 0)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCuenta);
            pstmt.setString(2, codigo);
            pstmt.setString(3, fechaExpiracion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar token de recuperación: " + e.getMessage());
        }
    }

    @Override
    public Integer buscarIdTokenValido(int idCuenta, String codigo, String ahora) {
        String sql = "SELECT id_token FROM token_restablecimiento "
                + "WHERE id_cuenta = ? AND codigo = ? AND utilizado = 0 "
                + "AND intentos < 5 AND fecha_expiracion > ? "
                + "ORDER BY id_token DESC LIMIT 1";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCuenta);
            pstmt.setString(2, codigo);
            pstmt.setString(3, ahora);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_token");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar token válido: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void marcarUtilizado(int idToken) {
        String sql = "UPDATE token_restablecimiento SET utilizado = 1 WHERE id_token = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToken);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al marcar token utilizado: " + e.getMessage());
        }
    }
}
