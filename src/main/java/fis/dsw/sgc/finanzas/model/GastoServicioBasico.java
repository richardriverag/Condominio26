package fis.dsw.sgc.finanzas.model;

public class GastoServicioBasico implements ITipoGasto {
    @Override
    public String getMotivo() {
        return "SERVICIO BASICO"; // Motivo definido en el caso de uso[cite: 3]
    }
}
