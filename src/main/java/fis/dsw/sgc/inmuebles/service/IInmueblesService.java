package fis.dsw.sgc.inmuebles.service;

import java.util.List;

import fis.dsw.sgc.inmuebles.dto.CasoFortuitoDTO;
import fis.dsw.sgc.inmuebles.dto.InmuebleResumenDTO;
import fis.dsw.sgc.inmuebles.dto.OpcionComboDTO;
import fis.dsw.sgc.inmuebles.model.Inmueble;

public interface IInmueblesService {
    List<InmuebleResumenDTO> listarInmuebles(String filtro);

    List<OpcionComboDTO> obtenerEdificios();

    List<OpcionComboDTO> obtenerTiposInmueble();

    void registrarInmueble(Inmueble inmueble);

    Inmueble buscarInmueblePorCodigo(String codigo);

    void actualizarInmueble(Inmueble inmueble);

    /**
     * Registra un caso fortuito (incidente imprevisto) para un inmueble.
     */
    void registrarCasoFortuito(int idInmueble, String descripcion);

    /**
     * Lista el historial de casos fortuitos registrados para un inmueble.
     */
    List<CasoFortuitoDTO> listarCasosFortuitos(int idInmueble);
}