package fis.dsw.sgc.inmuebles.dao;

import java.util.List;

import fis.dsw.sgc.inmuebles.dto.InmuebleResumenDTO;
import fis.dsw.sgc.inmuebles.dto.OpcionComboDTO;
import fis.dsw.sgc.inmuebles.model.Inmueble;

public interface IInmuebleDAO {
    void guardar(Inmueble inmueble);
    void actualizar(Inmueble inmueble);
    Inmueble buscarPorId(int idInmueble);
    Inmueble buscarPorCodigo(String codigo);
    List<InmuebleResumenDTO> buscarResumen(String filtro);
    List<OpcionComboDTO> listarEdificios();
    List<OpcionComboDTO> listarTiposInmueble();
    boolean existeCodigo(String codigo);
    boolean existeCodigoParaOtroInmueble(String codigo, int idInmueble);
}