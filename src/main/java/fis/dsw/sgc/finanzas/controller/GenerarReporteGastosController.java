package fis.dsw.sgc.finanzas.controller;

import fis.dsw.sgc.finanzas.dto.DetalleGastoDTO;
import fis.dsw.sgc.finanzas.dto.ReporteGastosDTO;
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
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GenerarReporteGastosController implements Initializable {

    // 5. Inyección de dependencias
    private final IReportesService reportesService;

    public GenerarReporteGastosController(IReportesService reportesService) {
        this.reportesService = reportesService;
    }

    @FXML private Button btnBuscar;
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;

    // 1. Mapping de las 4 columnas de la tabla
    @FXML private TableColumn<DetalleGastoDTO, String> colMotivo;
    @FXML private TableColumn<DetalleGastoDTO, String> colDescripcion;
    @FXML private TableColumn<DetalleGastoDTO, LocalDate> colFecha;
    @FXML private TableColumn<DetalleGastoDTO, Double> colValor;

    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;

    @FXML private Label lblIconoAgua;
    @FXML private Label lblIconoFechas;
    @FXML private Label lblIconoFechas1;
    @FXML private Label lblIconoGastos;
    @FXML private Label lblIconoGuardar;
    @FXML private Label lblIconoInternet;
    @FXML private Label lblIconoLimpiar;
    @FXML private Label lblIconoLuz;
    @FXML private Label lblIconoTelefono;
    @FXML private Label lblTotalGastos;
    @FXML private Label lblTotalOtros;
    @FXML private Label lblTotalServiciosBasicos;
    @FXML private Label lblTotalSueldos;

    @FXML private TableView<DetalleGastoDTO> tbReporteGastos;

    @FXML private TextField txtTotalAgua;
    @FXML private TextField txtTotalGastos;
    @FXML private TextField txtTotalInternet;
    @FXML private TextField txtTotalLuz;
    @FXML private TextField txtTotalOtros;
    @FXML private TextField txtTotalServiciosBasicos;
    @FXML private TextField txtTotalSueldos;
    @FXML private TextField txtTotalTelefono;

    @FXML
    void consultarGastos(ActionEvent event) {
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();

        // Escenario Alterno 1: Validación de ingreso/existencia de fechas
        if (fechaInicio == null || fechaFin == null) {
            mostrarAlerta("Formato de fechas incorrecto", "Formato de fechas incorrecto, ingrese la fecha en formato DD/MM/YYYY", Alert.AlertType.WARNING);
            return;
        }

        LocalDate fechaActual = LocalDate.now();

        // Escenario Alterno 2: La fecha de inicio tiene que ser menor que la fecha actual
        if (!fechaInicio.isBefore(fechaActual)) {
            mostrarAlerta("Error en Fechas", "La fecha de inicio tiene que ser menor que la fecha actual", Alert.AlertType.WARNING);
            return;
        }

        // Escenario Alterno 3: La fecha de inicio debe ser menor que la fecha de fin
        if (!fechaInicio.isBefore(fechaFin)) {
            mostrarAlerta("Error en Fechas", "La fecha de fin tiene que ser mayor que la fecha de inicio", Alert.AlertType.WARNING);
            return;
        }

        // Escenario Alterno 4: La fecha de fin debe ser menor o igual a la fecha actual
        if (fechaFin.isAfter(fechaActual)) {
            mostrarAlerta("Error en Fechas", "La fecha de fin tiene que ser menor o igual a la fecha actual", Alert.AlertType.WARNING);
            return;
        }

        try {
            // 4. Ejecución del caso de uso desde el Service
            ReporteGastosDTO reporte = reportesService.generarReporteGastos(fechaInicio, fechaFin);

            // 1. Poblado de los datos en la tabla (las 4 columnas)
            if (reporte.detalles != null) {
                tbReporteGastos.getItems().setAll(reporte.detalles);
            }

            // 2. Asignación de los datos entregados por el reporte en sus txtRespectivos
            txtTotalAgua.setText(String.format("%.2f", reporte.totalAgua));
            txtTotalLuz.setText(String.format("%.2f", reporte.totalLuz));
            txtTotalTelefono.setText(String.format("%.2f", reporte.totalTelefono));
            txtTotalInternet.setText(String.format("%.2f", reporte.totalInternet));
            txtTotalSueldos.setText(String.format("%.2f", reporte.totalSueldos));
            txtTotalOtros.setText(String.format("%.2f", reporte.totalOtros));

            // Total de servicios básicos (Agua + Luz + Teléfono + Internet)
            double totalServicios = reporte.totalAgua + reporte.totalLuz + reporte.totalTelefono + reporte.totalInternet;
            txtTotalServiciosBasicos.setText(String.format("%.2f", totalServicios));

            txtTotalGastos.setText(String.format("%.2f", reporte.totalGeneral));

            // Escenario Básico: Mensaje final según el requisito
            mostrarAlerta("Éxito", "Reporte generado correctamente", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error al generar reporte", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void descargarReporte(ActionEvent event) {
        // 7. Descargar reporte en dos archivos CSV (detalles y resumen de totales)
        if (tbReporteGastos.getItems().isEmpty()) {
            mostrarAlerta("Sin datos", "No existen registros en la tabla para descargar.", Alert.AlertType.WARNING);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            // Generación de CSV con los detalles (incluyendo Fecha)
            File archivoDetalles = new File("reporte_gastos_detalles.csv");
            try (PrintWriter writer = new PrintWriter(archivoDetalles)) {
                writer.println("MOTIVO,DESCRIPCION,FECHA,VALOR");
                for (DetalleGastoDTO detalle : tbReporteGastos.getItems()) {
                    String fechaStr = (detalle.fecha != null) ? detalle.fecha.format(formatter) : "";
                    writer.printf("%s,%s,%s,%.2f\n",
                            detalle.motivo != null ? detalle.motivo : "",
                            detalle.descripcion != null ? detalle.descripcion : "",
                            fechaStr,
                            detalle.valor != null ? detalle.valor : 0.0);
                }
            }

            // Generación de CSV con los totales consignados
            File archivoTotales = new File("reporte_gastos_totales.csv");
            try (PrintWriter writer = new PrintWriter(archivoTotales)) {
                writer.println("CONCEPTO,VALOR");
                writer.println("Total Agua," + txtTotalAgua.getText());
                writer.println("Total Luz," + txtTotalLuz.getText());
                writer.println("Total Telefono," + txtTotalTelefono.getText());
                writer.println("Total Internet," + txtTotalInternet.getText());
                writer.println("Total Servicios Basicos," + txtTotalServiciosBasicos.getText());
                writer.println("Total Sueldos," + txtTotalSueldos.getText());
                writer.println("Total Otros," + txtTotalOtros.getText());
                writer.println("Total Gastos Generales," + txtTotalGastos.getText());
            }

            mostrarAlerta("Descarga completada", "Los archivos CSV fueron descargados con éxito.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error de exportación", "Error al crear los archivos de descarga: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void limpiarReporte(ActionEvent event) {
        // 8. Deja vacíos la tabla, los textfields y los filtros de fecha
        tbReporteGastos.getItems().clear();

        txtTotalAgua.clear();
        txtTotalLuz.clear();
        txtTotalTelefono.clear();
        txtTotalInternet.clear();
        txtTotalServiciosBasicos.clear();
        txtTotalSueldos.clear();
        txtTotalOtros.clear();
        txtTotalGastos.clear();

        dpFechaInicio.setValue(null);
        dpFechaFin.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1 y 3. Mapeo seguro directo hacia los atributos del DetalleGastoDTO
        colMotivo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().motivo));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion));
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().fecha));
        colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().valor));

        // 6. Carga y preservación de iconos de la interfaz
        FontIcon icon = new FontIcon("fa-external-link");
        icon.getStyleClass().add("titleIcon");
        lblIconoGastos.setGraphic(icon);
        lblIconoGastos.setText(null);

        FontIcon icon2 = new FontIcon("fa-calendar-minus-o");
        icon2.getStyleClass().add("totalsIcon");
        lblIconoFechas.setText(null);
        lblIconoFechas.setGraphic(icon2);

        FontIcon icon3 = new FontIcon("fa-calendar-plus-o");
        icon3.getStyleClass().add("totalsIcon");
        lblIconoFechas1.setText(null);
        lblIconoFechas1.setGraphic(icon3);

        FontIcon icon4 = new FontIcon("fa-phone");
        icon4.getStyleClass().add("totalsIcon");
        lblIconoTelefono.setText(null);
        lblIconoTelefono.setGraphic(icon4);

        FontIcon icon5 = new FontIcon("fa-tint");
        icon5.getStyleClass().add("totalsIcon");
        lblIconoAgua.setText(null);
        lblIconoAgua.setGraphic(icon5);

        FontIcon icon6 = new FontIcon("fa-plug");
        icon6.getStyleClass().add("totalsIcon");
        lblIconoLuz.setText(null);
        lblIconoLuz.setGraphic(icon6);

        FontIcon icon7 = new FontIcon("fa-wifi");
        icon7.getStyleClass().add("totalsIcon");
        lblIconoInternet.setText(null);
        lblIconoInternet.setGraphic(icon7);

        FontIcon icon8 = new FontIcon("fa-users");
        icon8.getStyleClass().add("totalsIcon");
        lblTotalSueldos.setText(null);
        lblTotalSueldos.setGraphic(icon8);

        FontIcon icon9 = new FontIcon("fa-wrench");
        icon9.getStyleClass().add("totalsIcon");
        lblTotalOtros.setText(null);
        lblTotalOtros.setGraphic(icon9);

        FontIcon icon10 = new FontIcon("fa-shower");
        icon10.getStyleClass().add("totalsIcon");
        lblTotalServiciosBasicos.setText(null);
        lblTotalServiciosBasicos.setGraphic(icon10);

        FontIcon icon11 = new FontIcon("fa-usd");
        icon11.getStyleClass().add("totalsIcon");
        lblTotalGastos.setText(null);
        lblTotalGastos.setGraphic(icon11);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}