public interface IServicioSeguridad {
    void registrarIngresoPersona(String id, String nom, String tipo, String mot);
    void programarVisita(String idUsr, String idVis, Date fHora);
    int consultarDisponibilidadParqueaderos();
}