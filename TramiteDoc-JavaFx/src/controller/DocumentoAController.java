/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.net.URL;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.database.Conexion;
import modelo.Documento;
import modelo.Empleado;
import modelo.Expediente;
import modelo.TipoDoc;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class DocumentoAController implements Initializable {

    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Documento> documento;
    @FXML
    private TableColumn<Documento, Integer> id;
    @FXML
    private TableColumn<Documento, TipoDoc> tipdoc;
    @FXML
    private TableColumn<Documento, Expediente> expediente;
    @FXML
    private TableColumn<Documento, Empleado> remitente;
    @FXML
    private TableColumn<Documento, String> estado;
    @FXML
    private TableColumn<Documento, String> asunto;

    @FXML
    private JFXTextArea txtAsunto;
    
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
    private ObservableList<Expediente> listaExpediente;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<TipoDoc> listaTipoDoc;
    private ObservableList<Documento> listaDocumento;
    private Conexion conexion;
    @FXML
    private ComboBox<Empleado> cmbPersonals;
    @FXML
    private ComboBox<TipoDoc> cmbTipDocs;
    @FXML
    private ComboBox<Expediente> cmbExpedientes;
    @FXML
    private ComboBox<String> cmbEstados;
    @FXML
    private Button btnAddFile;
   
    @FXML
    private TextField txtpdf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenaTabla();
        llenarCmbBox();
         gestionarEventos();
    }

    @FXML
    private void onTable(MouseEvent event) {
    }

    @FXML
    private void onAdd(MouseEvent event) {
        byte[] bt = (txtpdf.getText()).getBytes();
        String fijo;
        if (!cmbEstados.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }

        Documento a = new Documento(0,
                txtAsunto.getText(),
                fijo,
                cmbTipDocs.getSelectionModel().getSelectedItem(),
                cmbExpedientes.getSelectionModel().getSelectedItem(),
                cmbPersonals.getSelectionModel().getSelectedItem(),
                bt
                 
        //Condicion?ValorVerdadero:ValorFalso
        );
        //Llamar al metodo guardarRegistro de la clase Alumno
        conexion.establecerConexion();
        int resultado = a.guardarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaDocumento.add(a);
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
           Documento selected = documento.getSelectionModel().getSelectedItem();
                   int id = selected.getCodigoDocumento();
         String fijo;
        if (!cmbEstados.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }

        Documento a = new Documento(id,
                txtAsunto.getText(),
                fijo,
                cmbTipDocs.getSelectionModel().getSelectedItem(),
                cmbExpedientes.getSelectionModel().getSelectedItem(),
                cmbPersonals.getSelectionModel().getSelectedItem()
        //Condicion?ValorVerdadero:ValorFalso
        );
        //Llamar al metodo guardarRegistro de la clase Alumno
        conexion.establecerConexion();
        int resultado = a.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaDocumento.add(a);
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
        int resultado = documento.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaDocumento.remove(documento.getSelectionModel().getSelectedIndex());
            //JDK 8u>40
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        } else {

            Alert mensaje = new Alert(Alert.AlertType.WARNING);
            mensaje.setTitle("Prohibido");
            mensaje.setContentText("El registro no puede ser eliminado, porque el documento esta en uso.");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();

        }
        documento.setItems(listaDocumento);
        limpiarComponentes();

    }

    public void llenaTabla() {

        conexion = new Conexion();
        conexion.establecerConexion();

        //Inicializar listas
        listaDocumento = FXCollections.observableArrayList();
        listaEmpleado = FXCollections.observableArrayList();
        listaExpediente = FXCollections.observableArrayList();
        listaTipoDoc = FXCollections.observableArrayList();

        //Llenar listas
        Documento.llenarInformacion(conexion.getConnection(), listaDocumento);
        Empleado.llenarInformacion(conexion.getConnection(), listaEmpleado);
        Expediente.llenarInformacion(conexion.getConnection(), listaExpediente);
        TipoDoc.llenarInformacion(conexion.getConnection(), listaTipoDoc);
        //Enlazar listas con ComboBox y TableView
        cmbExpedientes.setItems(listaExpediente);
        cmbPersonals.setItems(listaEmpleado);
        cmbTipDocs.setItems(listaTipoDoc);

        documento.setItems(listaDocumento);

        //Enlazar columnas con atributos
        id.setCellValueFactory(new PropertyValueFactory<Documento, Integer>("codigoDocumento"));
        asunto.setCellValueFactory(new PropertyValueFactory<Documento, String>("asunto"));
        tipdoc.setCellValueFactory(new PropertyValueFactory<Documento, TipoDoc>("tipodocumento"));
        expediente.setCellValueFactory(new PropertyValueFactory<Documento, Expediente>("idexpediente"));
        remitente.setCellValueFactory(new PropertyValueFactory<Documento, Empleado>("idempleado"));
        estado.setCellValueFactory(new PropertyValueFactory<Documento, String>("estado"));

        gestionarEventos();
        conexion.cerrarConexion();

    }

    private void gestionarEventos() {
        documento.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Documento>() {
                    @Override
                    public void changed(ObservableValue<? extends Documento> arg0,
                            Documento valorAnterior, Documento valorSeleccionado) {
                        if (valorSeleccionado != null) {
                            txtAsunto.setText(String.valueOf(valorSeleccionado.getAsunto()));
                            cmbExpedientes.setValue(valorSeleccionado.getIdexpediente());
                            cmbPersonals.setValue(valorSeleccionado.getIdempleado());
                            cmbTipDocs.setValue(valorSeleccionado.getTipodocumento());
                            cmbEstados.setValue(valorSeleccionado.getEstado());

                            btnAdd.setDisable(true);
                            btnDelete.setDisable(false);
                            btnUpdate.setDisable(false);
                        }
                    }

                }
        );
    }

    public void llenarCmbBox() {

        cmbEstados.setValue("Activo");
        cmbEstados.setItems(FXCollections.observableArrayList(
                "Activo", "Inactivo"
        ));
    }

    public void limpiarComponentes() {

        txtAsunto.setText(null);
        cmbEstados.setValue(null);
        cmbExpedientes.setValue(null);
        cmbPersonals.setValue(null);
        cmbTipDocs.setValue(null);

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }

    @FXML
    private void onAddFile(MouseEvent event) {
        
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users"));
        fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files","*.pdf"));
        File selectedFile = fc.showOpenDialog(null);
        
        if (selectedFile != null) {
            txtpdf.setText(String.valueOf(selectedFile.getAbsolutePath()));
        }else{
        
            System.out.println("File is not valid");
        
        
        }
        }
        
        
    
}
