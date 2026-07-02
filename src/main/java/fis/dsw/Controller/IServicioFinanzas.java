public interface IServicioFinanzas {
    String registrarDeuda(String idUsr, String mot, Date fMax, String desc, double val);
    String procesarPago(String idDeuda, String met, String comp, double val);
    List<Deuda> consultarDeuda(String idUsr);
    String registrarGastoCondominio(Date f, double val, String mot, String desc);
    Reporte generarRendicionCuentas(Date inicio, Date fin);
}