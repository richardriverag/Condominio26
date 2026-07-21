package fis.dsw.sgc.finanzas.model;
import java.time.LocalDate;
public class Pago {
    private int idPago;
    private int idDeuda;
    private double valorPagado;
    private LocalDate fechaPago;

    private ITipoPago tipoPago; // Patrón Strategy para el tipo de pago[cite: 1]

    public Pago() {}

    public ITipoPago getTipoPago() { return tipoPago; }
    public void setTipoPago(ITipoPago tipoPago) { this.tipoPago = tipoPago; }

    // Aquí residirán reglas financieras y transiciones[cite: 2]
}