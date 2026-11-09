package co.edu.uniquindio.poo.parcial3.viewController;

import co.edu.uniquindio.poo.parcial3.controller.PanaderiaController;
import co.edu.uniquindio.poo.parcial3.model.Cajero;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainViewController {

    private final PanaderiaController controller = new PanaderiaController();

    @FXML private ComboBox<String> cmbTipoEmpleado;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtMinutos;
    @FXML private TextField txtCaja;
    @FXML private TableView<Empleado> tablaEmpleados;
    @FXML private TableColumn<Empleado, String> colEmpCodigo;
    @FXML private TableColumn<Empleado, String> colEmpNombre;
    @FXML private TableColumn<Empleado, String> colEmpDocumento;
    @FXML private TableColumn<Empleado, String> colEmpTipo;
    @FXML private TableColumn<Empleado, String> colEmpMinutos;
    @FXML private TableColumn<Empleado, String> colEmpCaja;

    @FXML private TextField txtCodigoPedido;
    @FXML private TextField txtCliente;
    @FXML private ComboBox<TipoPan> cmbTipoPan;
    @FXML private TextField txtCantidad;
    @FXML private TableView<Pedido> tablaPedidos;
    @FXML private TableColumn<Pedido, String> colPedCodigo;
    @FXML private TableColumn<Pedido, String> colPedCliente;
    @FXML private TableColumn<Pedido, String> colPedTipo;
    @FXML private TableColumn<Pedido, String> colPedCantidad;
    @FXML private TableColumn<Pedido, String> colPedEstado;
    @FXML private TableColumn<Pedido, String> colPedEmpleado;
    @FXML private TableColumn<Pedido, String> colPedMinutos;

    @FXML private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbTipoEmpleado.setItems(FXCollections.observableArrayList(PanaderiaController.TIPOS_EMPLEADO));
        cmbTipoPan.setItems(FXCollections.observableArrayList(TipoPan.values()));

        // El campo "caja" solo aplica a los cajeros
        txtCaja.setDisable(true);
        cmbTipoEmpleado.valueProperty().addListener((obs, anterior, nuevo) -> {
            boolean esCajero = PanaderiaController.CAJERO.equals(nuevo);
            txtCaja.setDisable(!esCajero);
            if (!esCajero) {
                txtCaja.clear();
            }
        });

        colEmpCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colEmpNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colEmpDocumento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDocumento()));
        colEmpTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        colEmpMinutos.setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getMinutosDisponibles())));
        colEmpCaja.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue() instanceof Cajero cajero ? cajero.getCaja() : "-"));

        colPedCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colPedCliente.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreCliente()));
        colPedTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipoPan().name()));
        colPedCantidad.setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getCantidad())));
        colPedEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().name()));
        colPedEmpleado.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getCodigoEmpleado().isEmpty() ? "-" : c.getValue().getCodigoEmpleado()));
        colPedMinutos.setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getMinutosReservados())));

        refrescarTablas();
    }


    @FXML
    private void onRegistrarEmpleado() {
        try {
            int minutos = leerEntero(txtMinutos.getText(), "Los minutos disponibles");
            Empleado e = controller.registrarEmpleado(
                    cmbTipoEmpleado.getValue(),
                    txtNombre.getText().trim(),
                    txtDocumento.getText().trim(),
                    txtCodigoEmpleado.getText().trim(),
                    minutos,
                    txtCaja.getText().trim());
            limpiarEmpleado();
            refrescarTablas();
            mostrarExito("Empleado registrado: " + e);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void onCargarEjemplo() {
        try {
            controller.cargarDatosEjemplo();
            refrescarTablas();
            mostrarExito("Datos de ejemplo cargados (Ana, Diana, Carlos y Lucia).");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }


    @FXML
    private void onRegistrarPedido() {
        try {
            int cantidad = leerEntero(txtCantidad.getText(), "La cantidad");
            Pedido p = controller.registrarPedido(
                    txtCodigoPedido.getText().trim(),
                    txtCliente.getText().trim(),
                    cmbTipoPan.getValue(),
                    cantidad);
            limpiarPedido();
            refrescarTablas();
            mostrarExito("Pedido registrado: " + p);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void onAsignarPedido() {
        Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un pedido de la tabla.");
            return;
        }
        try {
            Pedido p = controller.asignarPedido(seleccionado.getCodigo());
            refrescarTablas();
            mostrarExito("Pedido " + p.getCodigo() + " asignado a " + p.getCodigoEmpleado()
                    + " (" + p.getMinutosReservados() + " min reservados).");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void onCancelarPedido() {
        Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un pedido de la tabla.");
            return;
        }
        try {
            Pedido p = controller.cancelarPedido(seleccionado.getCodigo());
            refrescarTablas();
            mostrarExito("Pedido " + p.getCodigo() + " cancelado.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }


    private void refrescarTablas() {
        tablaEmpleados.setItems(FXCollections.observableArrayList(controller.getEmpleados()));
        tablaPedidos.setItems(FXCollections.observableArrayList(controller.getPedidos()));
    }

    private int leerEntero(String texto, String campo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(campo + " debe ser un numero entero.");
        }
    }

    private void limpiarEmpleado() {
        cmbTipoEmpleado.setValue(null);
        txtNombre.clear();
        txtDocumento.clear();
        txtCodigoEmpleado.clear();
        txtMinutos.clear();
        txtCaja.clear();
    }

    private void limpiarPedido() {
        txtCodigoPedido.clear();
        txtCliente.clear();
        cmbTipoPan.setValue(null);
        txtCantidad.clear();
    }

    private void mostrarExito(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: #1b7a3a;");
        lblMensaje.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: #b00020;");
        lblMensaje.setText(mensaje);
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
