package fis.dsw.sgc.finanzas.service;

import java.time.LocalDate;

public interface IFachadaRendicionCuentas {
    // Casos de uso que involucran Gastos y Pagos simultáneamente
    void generarReporteRendicionCuentas(LocalDate fechaInicio, LocalDate fechaFin, String observaciones); // Caso de uso: generarReporteRendicionCuentas[cite: 3]
    void consultarReporteRendicionCuentas(LocalDate fechaInicio, LocalDate fechaFin); // Caso de uso: consultarReporteRendicionCuentas[cite: 3]
}
