package fis.dsw.sgc.finanzas.model;
import java.time.LocalDate;
public class Gasto {
    private int idGasto;
    private LocalDate fechaPago; // Validado para ser menor o igual a fecha actual[cite: 3]
    private double valorPagado;
    private String descripcion;

    private ITipoGasto tipoGasto; // Patrón Strategy para el tipo de gasto[cite: 1]

    public Gasto() {}

    public ITipoGasto getTipoGasto() { return tipoGasto; }
    public void setTipoGasto(ITipoGasto tipoGasto) { this.tipoGasto = tipoGasto; }

    // Aquí residirán reglas financieras y modificaciones de estado[cite: 1, 2]
}
