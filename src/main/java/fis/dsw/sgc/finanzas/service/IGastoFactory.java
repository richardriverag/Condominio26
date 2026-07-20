package fis.dsw.sgc.finanzas.service;
import java.time.LocalDate;
import fis.dsw.sgc.finanzas.model.Gasto;

public interface IGastoFactory {
    Gasto crearGasto(LocalDate fecha, double valor, String motivo, String descripcion); // Interface que define el método para crear[cite: 2]
}