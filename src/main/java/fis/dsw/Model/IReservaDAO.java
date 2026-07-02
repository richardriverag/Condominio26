public interface IReservaDAO {
    void guardar(Reserva reserva);
    void actualizar(Reserva reserva);
    List<Reserva> buscarPorUsuario(String idUsuario);
}