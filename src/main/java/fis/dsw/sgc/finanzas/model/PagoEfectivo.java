package fis.dsw.sgc.finanzas.model;

public class PagoEfectivo implements ITipoPago {
    @Override
    public String getMetodo() {
        return "EFECTIVO"; // Método de pago del Residente
    }
}
