public interface IDeudaDAO {
    void guardar(Deuda deuda);
    Deuda buscarPorId(String id);
    List<Deuda> buscarPorUsuario(String idUsuario);
}