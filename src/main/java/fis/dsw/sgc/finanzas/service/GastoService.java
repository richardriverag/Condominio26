package fis.dsw.sgc.finanzas.service;
import java.time.LocalDate;
import fis.dsw.sgc.finanzas.dao.IGastoDAO;

public class GastoService implements IGastoService {
    private final IGastoDAO gastoDAO;
    private final IGastoFactory gastoFactory;

    public GastoService(IGastoDAO gastoDAO, IGastoFactory gastoFactory) {
        this.gastoDAO = gastoDAO;
        this.gastoFactory = gastoFactory;
    }

    @Override
    public void registrarPagosCondominio(LocalDate fechaPago, double valorPagado, String motivoPago, String descripcion) {
        // Orquestación: Model -> Factory -> DAO
    }

    @Override
    public void generarReporteGastos(LocalDate fechaInicio, LocalDate fechaFin) {
        // Orquestación: Model -> Factory -> DAO
    }
}