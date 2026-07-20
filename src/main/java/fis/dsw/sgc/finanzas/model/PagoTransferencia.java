package fis.dsw.sgc.finanzas.model;

public class PagoTransferencia implements ITipoPago {
    @Override
    public String getMetodo() {
        return "TRANSFERENCIA"; // Método de pago del Residente[cite: 3]
    }
}
