package fis.dsw.sgc.finanzas.service;

import fis.dsw.sgc.finanzas.dao.IDeudaDAO;
import fis.dsw.sgc.finanzas.dto.CuotaDTO;
import fis.dsw.sgc.finanzas.model.Deuda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeudaService implements IDeudaService {
    private final IDeudaDAO deudaDAO;
    private final IDeudaFactory deudaFactory; // Cambiamos a la interfaz

    public DeudaService(IDeudaDAO deudaDAO, IDeudaFactory deudaFactory) {
        this.deudaDAO = deudaDAO;
        this.deudaFactory = deudaFactory;
    }

    // Constructor de prueba: todavía no existe una implementación real de IDeudaDAO,
    // por eso se permite instanciar el Service sin DAO mientras se usan datos quemados.
    public DeudaService() {
        this(null, null);
    }

    @Override
    public void registrarDeuda(String cedulaResidente, String motivoDeuda, LocalDate fechaMaximaPago, String descripcion, Double valor) {
        if(motivoDeuda.equals("RESERVA")){
            System.out.println("DEUDA POR RESERVA GENERADA CON EXITO");
            System.out.println("USUARIO: ");
            System.out.println("ROL: ");
            System.out.println("ROL: ");
        }
    }

    @Override
    public void modificarFechaMaximaDePagoDeUnaDeuda(Integer idDeuda, LocalDate nuevaFechaMaximaPago) {

    }

    @Override
    public void eliminarDeuda(Integer idDeuda) {

    }

    @Override
    public void pagarDeuda(Integer idDeuda, String metodoPago) {

    }

    @Override
    public void pagarDeudaTarjeta(Integer idDeuda, String numeroTarjeta, LocalDate fechaVencimientoTarjeta, String nombreTitularTarjeta, Integer ccv) {

    }

    @Override
    public List<CuotaDTO> solicitarPagoEnCuotas(Integer idDeuda, Integer numeroMesesADiferir) {
        // TODO: reemplazar por la orquestación real (Model -> DAO) cuando exista el DAO de deudas.
        // Datos quemados de prueba: simula la validación de mora y la generación de cuotas.
        if (idDeuda != null && idDeuda == 2) {
            throw new IllegalStateException("No puede ser beneficiario a este beneficio porque tiene deudas en estado EN MORA");
        }

        double valorDeudaSimulada = 90.00;
        double valorCuota = valorDeudaSimulada / numeroMesesADiferir;
        LocalDate hoy = LocalDate.now();

        List<CuotaDTO> cuotas = new ArrayList<>();
        for (int i = 1; i <= numeroMesesADiferir; i++) {
            cuotas.add(new CuotaDTO(
                    "Cuota " + i + "/" + numeroMesesADiferir,
                    hoy.plusMonths(i).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    String.format(Locale.US, "$%.2f", valorCuota)
            ));
        }
        return cuotas;
    }

    @Override
    public void consultarDeuda(String numeroCedulaResidente) {

    }

    @Override
    public void registrarDeudaAlicuotaMensual(String numeroCedulaResidente) {

    }

    @Override
    public void enviarRecordatorioDeudaPendiente(String numeroCedulaResidente) {

    }

    @Override
    public void registrarMoraDeuda(String numeroCedulaResidente) {

    }
}
