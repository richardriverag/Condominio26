public interface IServicioInmuebles {
    boolean registrarInmueble(String cod, String est, String tipo);
    void cambiarEstadoPropiedad(String idInm, String nuevoEst);
    void procesarCasoFortuito(String tipo, String desc, Date f);
    List<Inmueble> parametrizarCatalogo();
}