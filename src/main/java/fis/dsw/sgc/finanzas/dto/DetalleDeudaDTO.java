package fis.dsw.sgc.finanzas.dto;

import java.time.LocalDate;

public class DetalleDeudaDTO {
    public String motivo;
    public String descripcion;
    public LocalDate fecha;
    public Double valor;

    public DetalleDeudaDTO(String motivo, String descripcion, LocalDate fecha, Double valor) {
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.valor = valor;
    }
}
