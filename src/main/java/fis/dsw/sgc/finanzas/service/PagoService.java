package fis.dsw.sgc.finanzas.service;

import fis.dsw.sgc.finanzas.dao.IPagoDAO;

import java.time.LocalDate;

public class PagoService implements IPagoService {
    private final IPagoDAO pagoDAO;
    private final IPagoFactory pagoFactory;

    public PagoService(IPagoDAO pagoDAO, IPagoFactory pagoFactory) {
        this.pagoDAO = pagoDAO;
        this.pagoFactory = pagoFactory;
    }

    @Override
    public void registrarPagoEfectivoTransferenciaResidente(Integer idDeuda) {
        // Orquestación: Model -> Factory -> DAO[cite: 2]
    }

    @Override
    public void generarReporteDePagosRealizados(LocalDate fechaInicio, LocalDate fechaFin) {
    }

    @Override
    public void consultarPagosEfectuados(LocalDate fechaInicio, LocalDate fechaFin, String cedula) {
    }
}