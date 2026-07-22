package fis.dsw.sgc.administracion.dao;

public interface ITokenRecuperacionDAO {
    void guardarToken(int idCuenta, String codigo, String fechaExpiracion);
    Integer buscarIdTokenValido(int idCuenta, String codigo, String ahora);
    void marcarUtilizado(int idToken);
}
