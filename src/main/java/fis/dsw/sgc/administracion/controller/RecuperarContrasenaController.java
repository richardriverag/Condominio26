package fis.dsw.sgc.administracion.controller;

import fis.dsw.sgc.administracion.exception.GestionCuentasException;
import fis.dsw.sgc.administracion.service.GestionCuentasServiceImpl;
import fis.dsw.sgc.administracion.service.IGestionCuentasService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RecuperarContrasenaController {

    private final IGestionCuentasService cuentasService;

    public RecuperarContrasenaController(IGestionCuentasService cuentasService) {
        this.cuentasService = cuentasService;
    }

    public RecuperarContrasenaController() {
        this(new GestionCuentasServiceImpl());
    }

    // Panel Paso 1
    @FXML private VBox panelSolicitud;
    @FXML private TextField txtCorreo;
    @FXML private Label lblMensajeSolicitud;

    // Panel Paso 2
    @FXML private VBox panelRestablecimiento;
    @FXML private TextField txtToken;
    @FXML private PasswordField txtNuevaContrasena;
    @FXML private Label lblMensajeRestablecimiento;

    private String correoSolicitado;

    @FXML
    void enviarToken(ActionEvent event) {
        String correo = txtCorreo.getText() == null ? "" : txtCorreo.getText().trim();

        if (correo.isEmpty()) {
            mostrarMensaje(lblMensajeSolicitud, "Debe ingresar su correo.", "message-error");
            return;
        }

        String codigo;
        try {
            codigo = cuentasService.generarTokenRecuperacion(correo);
        } catch (GestionCuentasException e) {
            mostrarMensaje(lblMensajeSolicitud, e.getMessage(), "message-error");
            return;
        }

        correoSolicitado = correo;
        mostrarMensaje(lblMensajeSolicitud, "Token generado: " + codigo, "message-success");

        panelSolicitud.setDisable(true);
        panelRestablecimiento.setVisible(true);
        panelRestablecimiento.setManaged(true);
    }

    @FXML
    void restablecerContrasena(ActionEvent event) {
        String tokenIngresado = txtToken.getText() == null ? "" : txtToken.getText().trim();
        String nuevaClave = txtNuevaContrasena.getText() == null ? "" : txtNuevaContrasena.getText();

        if (tokenIngresado.isEmpty() || nuevaClave.isEmpty()) {
            mostrarMensaje(lblMensajeRestablecimiento, "Todos los campos son obligatorios.", "message-error");
            return;
        }

        if (nuevaClave.length() < 6) {
            mostrarMensaje(lblMensajeRestablecimiento, "La contraseña no cumple con los requisitos de seguridad.", "message-error");
            return;
        }

        try {
            cuentasService.restablecerContrasena(correoSolicitado, tokenIngresado, nuevaClave);
        } catch (GestionCuentasException e) {
            mostrarMensaje(lblMensajeRestablecimiento, e.getMessage(), "message-error");
            return;
        }

        mostrarMensaje(lblMensajeRestablecimiento, "Contraseña restablecida exitosamente.", "message-success");

        txtToken.setDisable(true);
        txtNuevaContrasena.setDisable(true);
    }

    private void mostrarMensaje(Label label, String texto, String estilo) {
        label.getStyleClass().setAll("message-label", estilo);
        label.setText(texto);
    }
}
