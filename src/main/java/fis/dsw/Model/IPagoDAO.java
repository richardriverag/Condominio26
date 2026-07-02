public interface IPagoDAO {
    void guardar(Pago pago);
    List<Pago> buscarPorRangoFechas(Date ini, Date fin);
}