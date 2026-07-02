public interface IServicioComunicaciones {
    void enviarMensaje(String asu, String cont, String tipo, List<String> dest);
    void crearAnuncioGeneral(String tit, String cont);
    void registrarHistorial(String det);
}