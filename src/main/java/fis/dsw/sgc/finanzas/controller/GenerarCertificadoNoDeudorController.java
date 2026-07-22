package fis.dsw.sgc.finanzas.controller;

import fis.dsw.sgc.finanzas.dto.CertificadoNoDeudorDTO;
import fis.dsw.sgc.finanzas.dto.DetalleDeudaDTO;
import fis.dsw.sgc.finanzas.service.IReportesService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GenerarCertificadoNoDeudorController implements Initializable {

    // 5 .- Inyección de dependencias a través del constructor
    private final IReportesService reportesService;

    public GenerarCertificadoNoDeudorController(IReportesService reportesService) {
        this.reportesService = reportesService;
    }

    // Campos de entrada
    @FXML private TextField txtCedula;
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;

    // 1 .- Tabla y Columnas con sus respectivos encabezados
    @FXML private TableView<DetalleDeudaDTO> tbDeudas;
    @FXML private TableColumn<DetalleDeudaDTO, String> colMotivo;
    @FXML private TableColumn<DetalleDeudaDTO, String> colDescripcion;
    @FXML private TableColumn<DetalleDeudaDTO, LocalDate> colFecha;
    @FXML private TableColumn<DetalleDeudaDTO, Double> colValor;

    // 2 .- TextFields respectivos para poner los datos que entrega el reporte
    @FXML private TextField txtTotalDeudas;
    @FXML private TextField txtTotalMora;

    // Elementos de la UI
    @FXML private Label lblMensaje;
    @FXML private HBox boxImprimir;
    @FXML private Label lblIconoImprimir;
    @FXML private Button btnImprimir;

    private static final DateTimeFormatter FMT_DDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Variables globales para mantener el estado al descargar el CSV
    private boolean tieneDeudaActiva = false;
    private String nombreResidente = "[Nombre y apellido]"; // Se asume que viene en el DTO

    // 6 .- Se mantiene el initialize para cargar los íconos y hacer el mapping de la tabla
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 3 .- Mapping perfecto de los DTO a las columnas de la tabla
        colMotivo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().motivo));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion));
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().fecha));
        colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().valor));

        dpInicio.setPromptText("Fecha de inicio");
        dpFin.setPromptText("Fecha de fin");

        // Carga de íconos requerida
        if(lblIconoImprimir != null) {
            FontIcon iconPrint = new FontIcon("fa-file-excel-o");
            iconPrint.getStyleClass().add("optionsIcon");
            lblIconoImprimir.setGraphic(iconPrint);
            lblIconoImprimir.setText(null);
        }

        if (boxImprimir != null) {
            boxImprimir.setVisible(false);
            boxImprimir.setManaged(false);
        }
    }

    // 1 .- Evento del botón "Generar certificado"
    @FXML
    void generar(ActionEvent event) {
        String cedula = txtCedula.getText();
        LocalDate fechaInicio = dpInicio.getValue();
        LocalDate fechaFin = dpFin.getValue();
        LocalDate fechaActual = LocalDate.now();

        // Validaciones básicas de las fechas según el Requisito
        if (cedula == null || cedula.trim().isEmpty()) {
            mostrarAlerta("Cédula requerida", "Debe ingresar el número de cédula del residente.", Alert.AlertType.WARNING);
            return;
        }
        if (fechaInicio == null || fechaFin == null) {
            mostrarAlerta("Error en Fechas", "Fechas con formato invalido. Ambas fechas deben tener el formato DD/MM/YYYY", Alert.AlertType.WARNING);
            return;
        }
        if (!fechaInicio.isBefore(fechaActual)) {
            mostrarAlerta("Error en Fechas", "La fecha de inicio tiene que ser menor que la fecha actual", Alert.AlertType.WARNING);
            return;
        }
        if (!fechaInicio.isBefore(fechaFin)) {
            mostrarAlerta("Error en Fechas", "La fecha de fin tiene que ser mayor que la fecha de inicio", Alert.AlertType.WARNING);
            return;
        }
        if (fechaFin.isAfter(fechaActual)) {
            mostrarAlerta("Error en Fechas", "La fecha de fin tiene que ser menor o igual a la fecha actual.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // 4 .- Se llama al caso de uso con el mismo nombre del requisito en el Service
            CertificadoNoDeudorDTO certificado = reportesService.generarCertificadoDeNoDeudor(cedula, fechaInicio, fechaFin);



            // 1 .- Limpiar y poblar tabla con los DTO recibidos
            tbDeudas.getItems().clear();
            if (certificado.detallesDeuda != null && !certificado.detallesDeuda.isEmpty()) {
                tbDeudas.getItems().setAll(certificado.detallesDeuda);
            }

            // 2 .- Poner los datos en sus txtRespectivos
            txtTotalDeudas.setText(String.format("%.2f", certificado.totalDeudas));
            txtTotalMora.setText(String.format("%.2f", certificado.totalMora));

            if (tieneDeudaActiva) {
                mostrarAlerta("Deudas encontradas", "El Residente tiene Deudas Pendientes de Pago o EN MORA, no se puede generar el certificado libre de deuda.", Alert.AlertType.WARNING);
            } else {
                mostrarAlerta("Éxito", "Certificado generado exitosamente", Alert.AlertType.INFORMATION);
            }

            // Mostrar el botón de descarga independientemente para permitir descargar los archivos correspondientes
            if (boxImprimir != null) {
                boxImprimir.setVisible(true);
                boxImprimir.setManaged(true);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar la solicitud: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // 7 .- Función para descargar reporte
    @FXML
    void descargarReporte(ActionEvent event) {
        String strInicio = (dpInicio.getValue() != null) ? dpInicio.getValue().format(FMT_DDMMYYYY) : "";
        String strFin = (dpFin.getValue() != null) ? dpFin.getValue().format(FMT_DDMMYYYY) : "";
        String cedula = (txtCedula != null && txtCedula.getText() != null) ? txtCedula.getText().trim() : "N/A";

        try {
            if (tieneDeudaActiva) {
                // EN CASO DE QUE EXISTA DEUDA: 2 archivos (detalles y total)

                // Archivo 1: Detalles
                File archivoDetalles = new File("deudas_detalles_" + cedula + ".csv");
                try (PrintWriter writer = new PrintWriter(archivoDetalles)) {
                    writer.println("MOTIVO,DESCRIPCION,FECHA,VALOR");
                    for (DetalleDeudaDTO detalle : tbDeudas.getItems()) {
                        String fechaStr = (detalle.fecha != null) ? detalle.fecha.format(FMT_DDMMYYYY) : "";
                        writer.printf("\"%s\",\"%s\",\"%s\",%.2f\n",
                                detalle.motivo != null ? detalle.motivo : "",
                                detalle.descripcion != null ? detalle.descripcion : "",
                                fechaStr,
                                detalle.valor != null ? detalle.valor : 0.0);
                    }
                }

                // Archivo 2: Totales
                File archivoTotales = new File("deudas_totales_" + cedula + ".csv");
                try (PrintWriter writer = new PrintWriter(archivoTotales)) {
                    writer.println("CONCEPTO,VALOR");
                    String valDeudas = txtTotalDeudas.getText().isEmpty() ? "0.00" : txtTotalDeudas.getText();
                    String valMora = txtTotalMora.getText().isEmpty() ? "0.00" : txtTotalMora.getText();

                    writer.println("Total Deudas Pendientes," + valDeudas);
                    writer.println("Total Mora," + valMora);
                }
                mostrarAlerta("Descarga completada", "Archivos CSV de deudas descargados en su PC.", Alert.AlertType.INFORMATION);

            } else {
                // EN CASO DE QUE NO EXISTA DEUDA: 1 archivo con el texto solicitado
                File archivoCertificado = new File("certificado_no_deudor_" + cedula + ".csv");
                try (PrintWriter writer = new PrintWriter(archivoCertificado)) {
                    writer.println("CERTIFICADO_NO_DEUDOR");
                    // Texto exacto solicitado (Nota: se ha corregido gramaticalmente la frase "presenta deudas" a "no presenta deudas")
                    String texto = String.format("el sistema de gestión del condominio [Nombre del Condominio] certifica que el residente %s con cédula %s no presenta deudas pendientes entre las fechas %s %s.",
                            nombreResidente, cedula, strInicio, strFin);
                    writer.println("\"" + texto + "\"");
                }
                mostrarAlerta("Descarga completada", "Certificado CSV descargado en su PC.", Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            mostrarAlerta("Error de exportación", "Error al crear los archivos de descarga: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // 8 .- Limpiar solo deja vacía la tabla y los textfield
    @FXML
    void limpiar(ActionEvent event) {
        if(txtCedula != null) txtCedula.clear();
        if(txtTotalDeudas != null) txtTotalDeudas.clear();
        if(txtTotalMora != null) txtTotalMora.clear();

        if(dpInicio != null) dpInicio.setValue(null);
        if(dpFin != null) dpFin.setValue(null);

        if(tbDeudas != null) tbDeudas.getItems().clear();

        if (boxImprimir != null) {
            boxImprimir.setVisible(false);
            boxImprimir.setManaged(false);
        }

        tieneDeudaActiva = false;
        nombreResidente = "[Nombre y apellido]";
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}