package fis.dsw.sgc.finanzas.service;

import java.time.LocalDate;

public class FachadaRendicionCuentasImpl implements IFachadaRendicionCuentas {
    private final IGastoService gastoService;
    private final IPagoService pagoService;

    // La fachada instanciará los Services y se comunicará a través de sus contratos
    public FachadaRendicionCuentasImpl(IGastoService gastoService, IPagoService pagoService) {
        this.gastoService = gastoService;
        this.pagoService = pagoService;
    }

    @Override
    public void generarReporteRendicionCuentas(LocalDate fechaInicio, LocalDate fechaFin, String observaciones) {
        // Delegará consultas al gastosService y al pagosService para balance[cite: 2, 3]
    }

    @Override
    public void consultarReporteRendicionCuentas(LocalDate fechaInicio, LocalDate fechaFin) {
        // Delegará consultas al gastosService y al pagosService para mostrar el reporte[cite: 2, 3]
    }
}
