package fis.dsw.sgc.finanzas.model;

public class GastoOtro implements ITipoGasto {
    @Override
    public String getMotivo() {
        return "OTROS"; // Motivo definido en el caso de uso[cite: 3]
    }
}
