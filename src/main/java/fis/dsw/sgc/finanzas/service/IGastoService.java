package fis.dsw.sgc.finanzas.service;
import fis.dsw.sgc.finanzas.dao.IGastoDAO;
import java.time.LocalDate;

public interface IGastoService {
    // Casos de uso de gastos[cite: 3]
    void registrarPagosCondominio(LocalDate fechaPago, double valorPagado, String motivoPago, String descripcion); // Caso de uso: registrarPagosCondominio
    void generarReporteGastos(LocalDate fechaInicio, LocalDate fechaFin); // Caso de uso: generarReportedeGastos
}