package fis.dsw.sgc.finanzas.model;

public class PagoTarjeta implements ITipoPago {
    @Override
    public String getMetodo() {
        return "TARJETA"; // Método de pago del Residente[cite: 3]
    }
}
