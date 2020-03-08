/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.database.Conexion;
import modelo.Area;
import modelo.Documento;
import modelo.Empleado;
import modelo.Envio;
import modelo.Expediente;
import modelo.TipoDoc;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class EnvioAController implements Initializable {
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Envio> envio;
    @FXML
    private TableColumn<Envio, Integer> idenv;
    @FXML
    private TableColumn<Envio, Documento> iddoc;
    @FXML
    private TableColumn<Envio, Date> femit;
    @FXML
    private TableColumn<Envio, Empleado> recepEmp;
    @FXML
    private TableColumn<Envio, Area> areaRecp;
    @FXML
    private TableColumn<Envio, String> estEnvia;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private ComboBox<Empleado> cmbpersonal;
    @FXML
    private ComboBox<Area> cmbArea;
    @FXML
    private ComboBox<Documento> cmbDocumento;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    /**
     * Initializes the controller class.
     */
    
 
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<Area> listaArea;
    private ObservableList<Documento> listaDocumento;
    private ObservableList<Envio> listaEnvio;
    private Conexion conexion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      llenaTabla();
        llenarCmbBox();
         gestionarEventos();
    }    

    @FXML
    private void onEnvioClic(MouseEvent event) {
    }

    @FXML
    private void onAdd(MouseEvent event) {
           String fijo;
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }

        Envio a = new Envio(0,
               Date.valueOf(dateFecha.getValue()),
                fijo,
                cmbDocumento.getSelectionModel().getSelectedItem(),
                cmbArea.getSelectionModel().getSelectedItem(),
                cmbpersonal.getSelectionModel().getSelectedItem()
        //Condicion?ValorVerdadero:ValorFalso
        );
        //Llamar al metodo guardarRegistro de la clase Alumno
        conexion.establecerConexion();
        int resultado = a.guardarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaEnvio.add(a);
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro agregado");
            mensaje.setContentText("El registro ha sido agregado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
        limpiarComponentes();
        llenaTabla();

    }

    @FXML
    private void onNew(MouseEvent event) {
        limpiarComponentes();
    }

    @FXML
    private void onUpdate(MouseEvent event) {
        Envio selected = envio.getSelectionModel().getSelectedItem();
                   int id = selected.getCodigoEnvio(); 
        String fijo;
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }

        Envio a = new Envio(id,
               Date.valueOf(dateFecha.getValue()),
                fijo,
                cmbDocumento.getSelectionModel().getSelectedItem(),
                cmbArea.getSelectionModel().getSelectedItem(),
                cmbpersonal.getSelectionModel().getSelectedItem()
        //Condicion?ValorVerdadero:ValorFalso
        );
        //Llamar al metodo guardarRegistro de la clase Alumno
        conexion.establecerConexion();
        int resultado = a.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaEnvio.add(a);
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro agregado");
            mensaje.setContentText("El registro ha sido agregado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
        limpiarComponentes();
        llenaTabla();
    }

    @FXML
    private void onDelete(MouseEvent event) {
          conexion.establecerConexion();
        int resultado = envio.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaEnvio.remove(envio.getSelectionModel().getSelectedIndex());
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        } else {

            Alert mensaje = new Alert(Alert.AlertType.WARNING);
            mensaje.setTitle("Prohibido");
            mensaje.setContentText("El registro no puede ser eliminado, porque el envio esta compartido.");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();

        }
        envio.setItems(listaEnvio);
        limpiarComponentes();
    }

    private void llenaTabla() {
        conexion = new Conexion();
        conexion.establecerConexion();

        //Inicializar listas
        listaDocumento = FXCollections.observableArrayList();
        listaEmpleado = FXCollections.observableArrayList();
        listaArea = FXCollections.observableArrayList();
       listaEnvio = FXCollections.observableArrayList();

        //Llenar listas
        Documento.llenarInformacion(conexion.getConnection(), listaDocumento);
        Empleado.llenarInformacion(conexion.getConnection(), listaEmpleado);
        Area.llenarInformacion(conexion.getConnection(), listaArea);
       Envio.llenarInformacion(conexion.getConnection(), listaEnvio);
        //Enlazar listas con ComboBox y TableView
        cmbArea.setItems(listaArea);
        cmbpersonal.setItems(listaEmpleado);
        cmbDocumento.setItems(listaDocumento);

        envio.setItems(listaEnvio);

        //Enlazar columnas con atributos
        idenv.setCellValueFactory(new PropertyValueFactory<Envio, Integer>("codigoEnvio"));
        iddoc.setCellValueFactory(new PropertyValueFactory<Envio, Documento>("iddocumento"));
        femit.setCellValueFactory(new PropertyValueFactory<Envio, Date>("fechaIngreso"));
        recepEmp.setCellValueFactory(new PropertyValueFactory<Envio, Empleado>("idempleado"));
        areaRecp.setCellValueFactory(new PropertyValueFactory<Envio, Area>("idarea"));
        estEnvia.setCellValueFactory(new PropertyValueFactory<Envio, String>("estado"));

        gestionarEventos();
        conexion.cerrarConexion();
    }

    private void llenarCmbBox() {
      
        cmbEstado.setValue("Activo");
        cmbEstado.setItems(FXCollections.observableArrayList(
                "Activo", "Inactivo"
        ));
    }

    private void gestionarEventos() {
     envio.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Envio>() {
                    @Override
                    public void changed(ObservableValue<? extends Envio> arg0,
                           Envio valorAnterior, Envio valorSeleccionado) {
                        if (valorSeleccionado != null) {
                            dateFecha.setValue(valorSeleccionado.getFechaIngreso().toLocalDate());
                            cmbArea.setValue(valorSeleccionado.getIdarea());
                            cmbpersonal.setValue(valorSeleccionado.getIdempleado());
                            cmbDocumento.setValue(valorSeleccionado.getIddocumento());
                            cmbEstado.setValue(valorSeleccionado.getEstado());

                            btnAdd.setDisable(true);
                            btnDelete.setDisable(false);
                            btnUpdate.setDisable(false);
                        }
                    }

                }
        );
    }
    
    public void limpiarComponentes() {

        dateFecha.setValue(null);
        cmbEstado.setValue(null);
        cmbArea.setValue(null);
        cmbpersonal.setValue(null);
        cmbDocumento.setValue(null);

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }
    
}
