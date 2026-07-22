package fis.dsw.sgc.finanzas.dto;

import java.util.List;

public class CertificadoNoDeudorDTO {
    public List<DetalleDeudaDTO> detallesDeuda;
    public double totalDeudas;
    public double totalMora;
    public boolean tieneDeuda;
    public String mensajeResumen;

    // (Agrega getters, setters y constructor según necesites)
}