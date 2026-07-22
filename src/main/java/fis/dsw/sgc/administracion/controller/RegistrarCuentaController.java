package fis.dsw.sgc.administracion.controller;

import fis.dsw.sgc.administracion.dto.RegistrarCuentaDTO;
import fis.dsw.sgc.administracion.exception.GestionCuentasException;
import fis.dsw.sgc.administracion.model.NombreRol;
import fis.dsw.sgc.administracion.service.GestionCuentasServiceImpl;
import fis.dsw.sgc.administracion.service.IGestionCuentasService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrarCuentaController {

    private static final String MSG_INICIAL =
            "Complete los datos del nuevo usuario y pulse Registrar cuenta.";

    private final IGestionCuentasService cuentasService = new GestionCuentasServiceImpl();

    @FXML private TextField txtCedula;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private ChoiceBox<NombreRol> cmbRol;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmarContrasena;
    @FXML private Button btnRegistrar;
    @FXML private Button btnLimpiar;
    @FXML private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbRol.getItems().setAll(NombreRol.values());
        setMensaje(MSG_INICIAL, "message-info");
    }

    @FXML
    void registrar(ActionEvent event) {
        String cedula = valorSeguro(txtCedula);
        String nombreUsuario = valorSeguro(txtNombreUsuario);
        String nombre = valorSeguro(txtNombre);
        String apellido = valorSeguro(txtApellido);
        String correo = valorSeguro(txtCorreo);
        NombreRol rol = cmbRol.getValue();
        String contrasena = txtContrasena.getText() == null ? "" : txtContrasena.getText();
        String confirmacion = txtConfirmarContrasena.getText() == null ? "" : txtConfirmarContrasena.getText();

        if (cedula.isEmpty() || nombreUsuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                || correo.isEmpty() || contrasena.isEmpty()) {
            setMensaje("Todos los campos son obligatorios.", "message-error");
            return;
        }

        if (rol == null) {
            setMensaje("Debe seleccionar un rol.", "message-error");
            return;
        }

        if (!correo.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            setMensaje("El correo ingresado no tiene un formato válido.", "message-error");
            return;
        }

        if (!contrasena.equals(confirmacion)) {
            setMensaje("Las contraseñas no coinciden.", "message-error");
            return;
        }

        if (contrasena.length() < 6) {
            setMensaje("La contraseña debe tener al menos 6 caracteres.", "message-error");
            return;
        }

        try {
            cuentasService.registrarCuenta(new RegistrarCuentaDTO(
                    cedula, nombreUsuario, nombre, apellido, correo, contrasena, rol));
            setMensaje("Cuenta registrada correctamente para " + nombre + " " + apellido + ".", "message-success");
            limpiarCampos();
        } catch (GestionCuentasException e) {
            setMensaje(e.getMessage(), "message-error");
        }
    }

    @FXML
    void limpiar(ActionEvent event) {
        limpiarCampos();
        setMensaje(MSG_INICIAL, "message-info");
    }

    private void limpiarCampos() {
        txtCedula.clear();
        txtNombreUsuario.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        cmbRol.getSelectionModel().clearSelection();
        txtContrasena.clear();
        txtConfirmarContrasena.clear();
    }

    private String valorSeguro(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void setMensaje(String texto, String estilo) {
        lblMensaje.getStyleClass().removeAll("message-info", "message-success", "message-error");
        if (!lblMensaje.getStyleClass().contains("message-label")) {
            lblMensaje.getStyleClass().add("message-label");
        }
        lblMensaje.getStyleClass().add(estilo);
        lblMensaje.setText(texto);
    }
}
