package fis.dsw.sgc.finanzas.model;

public class GastoSueldo implements ITipoGasto {
    @Override
    public String getMotivo() {
        return "SUELDOS"; // Motivo definido en el caso de uso[cite: 3]
    }
}
