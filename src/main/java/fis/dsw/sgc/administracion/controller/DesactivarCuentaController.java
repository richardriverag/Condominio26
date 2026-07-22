package fis.dsw.sgc.administracion.controller;

import fis.dsw.sgc.administracion.exception.GestionCuentasException;
import fis.dsw.sgc.administracion.model.EstadoCuenta;
import fis.dsw.sgc.administracion.model.Usuario;
import fis.dsw.sgc.administracion.service.GestionCuentasServiceImpl;
import fis.dsw.sgc.administracion.service.IGestionCuentasService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DesactivarCuentaController {

    private final IGestionCuentasService cuentasService = new GestionCuentasServiceImpl();

    @FXML private VBox panelPrincipal;
    @FXML private TextField txtBuscarCorreo;
    @FXML private Button btnBuscar;
    @FXML private Label lblMensaje;

    @FXML private VBox panelInfoCuenta;
    @FXML private Label lblUsuarioEncontrado;
    @FXML private Label lblEstadoCuenta;
    @FXML private Button btnAccionEstado; // Modificado para servir como interruptor

    @FXML private VBox panelConfirmacion;
    @FXML private Label lblResumenConfirmacion;
    @FXML private Button btnConfirmarAccion;

    // Cuenta actualmente seleccionada
    private Usuario usuarioSeleccionado;

    @FXML
    void buscarCuenta(ActionEvent event) {
        String correo = valorSeguro(txtBuscarCorreo);
        ocultarInfoCuenta();

        if (correo.isEmpty()) {
            mostrarError("Ingrese un correo para buscar la cuenta.");
            return;
        }

        Usuario usuario = cuentasService.buscarPorCorreo(correo);

        if (usuario == null) {
            mostrarError("No se encontró ninguna cuenta con ese correo.");
            return;
        }

        this.usuarioSeleccionado = usuario;
        EstadoCuenta estado = usuario.getCuenta().getEstado();

        lblUsuarioEncontrado.setText(usuario.getNombre() + " " + usuario.getApellido()
                + " (" + usuario.getCorreo() + ")");

        actualizarBadgeEstado(estado);

        panelInfoCuenta.setVisible(true);
        panelInfoCuenta.setManaged(true);
        lblMensaje.setText("");

        // Configurar el botón según el estado actual
        if (estado == EstadoCuenta.DESACTIVADA) {
            btnAccionEstado.setText("Activar Cuenta");
            btnAccionEstado.getStyleClass().setAll("primary-button");
        } else {
            btnAccionEstado.setText("Desactivar Cuenta");
            btnAccionEstado.getStyleClass().setAll("danger-button");
        }
        btnAccionEstado.setDisable(false);
    }

    @FXML
    void solicitarCambioEstado(ActionEvent event) {
        if (usuarioSeleccionado == null) return;

        EstadoCuenta estado = usuarioSeleccionado.getCuenta().getEstado();

        // Cambiar el texto de confirmación dinámicamente
        if (estado == EstadoCuenta.ACTIVA) {
            lblResumenConfirmacion.setText(
                    "¿Está seguro de desactivar la cuenta de " + usuarioSeleccionado.getNombre() + " " + usuarioSeleccionado.getApellido() + "?\n"
                            + "El usuario perderá el acceso al sistema hasta que la cuenta sea reactivada.");
            btnConfirmarAccion.setText("Sí, desactivar cuenta");
            btnConfirmarAccion.getStyleClass().setAll("danger-button");
        } else {
            lblResumenConfirmacion.setText(
                    "¿Está seguro de reactivar la cuenta de " + usuarioSeleccionado.getNombre() + " " + usuarioSeleccionado.getApellido() + "?\n"
                            + "El usuario volverá a tener acceso al sistema inmediatamente.");
            btnConfirmarAccion.setText("Sí, activar cuenta");
            btnConfirmarAccion.getStyleClass().setAll("primary-button");
        }

        panelPrincipal.setVisible(false);
        panelPrincipal.setManaged(false);
        panelConfirmacion.setVisible(true);
        panelConfirmacion.setManaged(true);
    }

    @FXML
    void cancelarConfirmacion(ActionEvent event) {
        panelConfirmacion.setVisible(false);
        panelConfirmacion.setManaged(false);
        panelPrincipal.setVisible(true);
        panelPrincipal.setManaged(true);
        mostrarInfo("Operación cancelada.");
    }

    @FXML
    void confirmarCambioEstado(ActionEvent event) {
        if (usuarioSeleccionado != null) {
            EstadoCuenta estadoActual = usuarioSeleccionado.getCuenta().getEstado();
            EstadoCuenta nuevoEstado = estadoActual == EstadoCuenta.ACTIVA
                    ? EstadoCuenta.DESACTIVADA : EstadoCuenta.ACTIVA;

            try {
                cuentasService.cambiarEstadoCuenta(usuarioSeleccionado.getCuenta().getIdCuenta(), nuevoEstado);
            } catch (GestionCuentasException e) {
                mostrarError(e.getMessage());
                panelConfirmacion.setVisible(false);
                panelConfirmacion.setManaged(false);
                panelPrincipal.setVisible(true);
                panelPrincipal.setManaged(true);
                return;
            }

            usuarioSeleccionado.getCuenta().setEstado(nuevoEstado);
            mostrarExito(nuevoEstado == EstadoCuenta.DESACTIVADA
                    ? "Cuenta desactivada exitosamente." : "Cuenta reactivada exitosamente.");

            actualizarBadgeEstado(nuevoEstado);

            if (nuevoEstado == EstadoCuenta.DESACTIVADA) {
                btnAccionEstado.setText("Activar Cuenta");
                btnAccionEstado.getStyleClass().setAll("primary-button");
            } else {
                btnAccionEstado.setText("Desactivar Cuenta");
                btnAccionEstado.getStyleClass().setAll("danger-button");
            }
        }

        panelConfirmacion.setVisible(false);
        panelConfirmacion.setManaged(false);
        panelPrincipal.setVisible(true);
        panelPrincipal.setManaged(true);
    }

    private void actualizarBadgeEstado(EstadoCuenta estado) {
        lblEstadoCuenta.setText(estado.name());
        lblEstadoCuenta.getStyleClass().setAll("badge-estado",
                estado == EstadoCuenta.ACTIVA ? "badge-activa" : "badge-desactivada");
    }

    private void ocultarInfoCuenta() {
        panelInfoCuenta.setVisible(false);
        panelInfoCuenta.setManaged(false);
        usuarioSeleccionado = null;
    }

    private String valorSeguro(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void mostrarError(String texto) {
        lblMensaje.getStyleClass().setAll("message-label", "message-error");
        lblMensaje.setText(texto);
    }

    private void mostrarExito(String texto) {
        lblMensaje.getStyleClass().setAll("message-label", "message-success");
        lblMensaje.setText(texto);
    }

    private void mostrarInfo(String texto) {
        lblMensaje.getStyleClass().setAll("message-label", "message-info");
        lblMensaje.setText(texto);
    }
}