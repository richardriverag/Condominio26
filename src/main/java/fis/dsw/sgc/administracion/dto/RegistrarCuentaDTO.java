package fis.dsw.sgc.administracion.dto;

import fis.dsw.sgc.administracion.model.NombreRol;

public class RegistrarCuentaDTO {
    private String cedula;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private NombreRol rol;

    public RegistrarCuentaDTO(String cedula, String nombreUsuario, String nombre, String apellido,
                              String correo, String contrasena, NombreRol rol) {
        this.cedula = cedula;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getCedula() { return cedula; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public NombreRol getRol() { return rol; }
}
