package fis.dsw.sgc.administracion.dao;

import fis.dsw.sgc.administracion.model.Cuenta;
import fis.dsw.sgc.administracion.model.EstadoCuenta;
import fis.dsw.sgc.administracion.model.NombreRol;
import fis.dsw.sgc.administracion.model.Perfil;
import fis.dsw.sgc.administracion.model.Rol;
import fis.dsw.sgc.administracion.model.Usuario;
import fis.dsw.sgc.conexion_bd.DBConnection;
import fis.dsw.sgc.usuarios.dto.ResidenteFachadaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOMySQL implements IUsuarioDAO {

    @Override
    public int guardar(Usuario usuario) {
        String sql = "INSERT INTO usuario (numero_documento, nombres, apellidos, correo, telefono, direccion, foto_perfil, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVO')";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Perfil perfil = usuario.getPerfil();
            pstmt.setString(1, usuario.getCedula());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getCorreo());
            pstmt.setString(5, perfil != null ? perfil.getTelefono() : null);
            pstmt.setString(6, perfil != null ? perfil.getDireccion() : null);
            pstmt.setString(7, perfil != null ? perfil.getFotoPerfil() : null);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public void actualizarInformacion(int idUsuario, String nombres, String apellidos, String correo) {
        String sql = "UPDATE usuario SET nombres = ?, apellidos = ?, correo = ?, "
                + "fecha_actualizacion = CURRENT_TIMESTAMP WHERE id_usuario = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombres);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, correo);
            pstmt.setInt(4, idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar información de usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizarPerfil(int idUsuario, String telefono, String direccion, String fotoPerfil) {
        String sql = "UPDATE usuario SET telefono = ?, direccion = ?, foto_perfil = ?, "
                + "fecha_actualizacion = CURRENT_TIMESTAMP WHERE id_usuario = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, telefono);
            pstmt.setString(2, direccion);
            pstmt.setString(3, fotoPerfil);
            pstmt.setInt(4, idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar perfil: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT u.nombres, u.apellidos, u.correo, u.telefono, u.direccion, u.foto_perfil, "
                + "u.id_usuario, u.numero_documento, c.id_cuenta, c.estado AS estado_cuenta "
                + "FROM usuario u JOIN cuenta c ON c.id_usuario = u.id_usuario WHERE u.id_usuario = ?";
        return buscarUno(sql, ps -> ps.setInt(1, idUsuario));
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT u.nombres, u.apellidos, u.correo, u.telefono, u.direccion, u.foto_perfil, "
                + "u.id_usuario, u.numero_documento, c.id_cuenta, c.estado AS estado_cuenta "
                + "FROM usuario u JOIN cuenta c ON c.id_usuario = u.id_usuario WHERE u.correo = ?";
        return buscarUno(sql, ps -> ps.setString(1, correo));
    }

    private interface Parametrizador {
        void aplicar(PreparedStatement ps) throws SQLException;
    }

    private Usuario buscarUno(String sql, Parametrizador parametrizador) {
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            parametrizador.aplicar(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setCedula(rs.getString("numero_documento"));
                usuario.setNombre(rs.getString("nombres"));
                usuario.setApellido(rs.getString("apellidos"));
                usuario.setCorreo(rs.getString("correo"));

                Cuenta cuenta = new Cuenta();
                cuenta.setIdCuenta(rs.getInt("id_cuenta"));
                cuenta.setEstado(EstadoCuenta.valueOf(rs.getString("estado_cuenta")));
                cuenta.setRoles(cargarRolesPorUsuario(conn, rs.getInt("id_usuario")));
                usuario.setCuenta(cuenta);

                Perfil perfil = new Perfil();
                perfil.setTelefono(rs.getString("telefono"));
                perfil.setDireccion(rs.getString("direccion"));
                perfil.setFotoPerfil(rs.getString("foto_perfil"));
                usuario.setPerfil(perfil);

                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            return null;
        }
    }

    private List<Rol> cargarRolesPorUsuario(Connection conn, int idUsuario) throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT r.nombre, r.descripcion FROM usuario_rol ur "
                + "JOIN rol r ON r.id_rol = ur.id_rol WHERE ur.id_usuario = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Rol rol = new Rol();
                    rol.setNombre(NombreRol.valueOf(rs.getString("nombre")));
                    rol.setDescripcion(rs.getString("descripcion"));
                    roles.add(rol);
                }
            }
        }
        return roles;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, correo FROM usuario ORDER BY nombres, apellidos";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setCorreo(rs.getString("correo"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public boolean existeCorreo(String correo) {
        return existe("SELECT 1 FROM usuario WHERE correo = ? LIMIT 1", correo);
    }

    @Override
    public boolean existeCedula(String cedula) {
        return existe("SELECT 1 FROM usuario WHERE numero_documento = ? LIMIT 1", cedula);
    }

    private boolean existe(String sql, String valor) {
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, valor);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ResidenteFachadaDTO buscarResidentePorCedula(String cedula) {
        String sql = "SELECT u.id_usuario, u.numero_documento, u.nombres, u.apellidos, u.correo, "
                + "(SELECT ui.id_inmueble FROM usuario_inmueble ui "
                + " WHERE ui.id_usuario = u.id_usuario AND ui.tipo_relacion = 'RESIDENTE' AND ui.estado = 'ACTIVO' "
                + " ORDER BY ui.es_principal DESC, ui.fecha_inicio DESC LIMIT 1) AS id_departamento "
                + "FROM usuario u "
                + "JOIN usuario_rol ur ON ur.id_usuario = u.id_usuario "
                + "JOIN rol r ON r.id_rol = ur.id_rol "
                + "WHERE u.numero_documento = ? AND r.nombre = ? "
                + "LIMIT 1";

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            pstmt.setString(2, NombreRol.RESIDENTE.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nombreCompleto = rs.getString("nombres") + " " + rs.getString("apellidos");
                    int idDepartamento = rs.getInt("id_departamento");
                    Integer idDepartamentoObj = rs.wasNull() ? null : idDepartamento;
                    return new ResidenteFachadaDTO(
                            rs.getInt("id_usuario"),
                            rs.getString("numero_documento"),
                            nombreCompleto,
                            rs.getString("correo"),
                            idDepartamentoObj);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar residente por cédula: " + e.getMessage());
        }
        return null;
    }
}
