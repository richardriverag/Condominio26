package fis.dsw.sgc.administracion.exception;

public class GestionCuentasException extends RuntimeException {
    public GestionCuentasException(String mensaje) {
        super(mensaje);
    }

    public GestionCuentasException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
