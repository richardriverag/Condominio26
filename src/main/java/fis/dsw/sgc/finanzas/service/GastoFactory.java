package fis.dsw.sgc.finanzas.service;
import java.time.LocalDate;
import fis.dsw.sgc.finanzas.model.Gasto;

public class GastoFactory implements IGastoFactory {
    @Override
    public Gasto crearGasto(LocalDate fecha, double valor, String motivo, String descripcion) {
        // Lógica de creación DEJAR EN BLANCO[cite: 1, 2]
        return null;
    }
}