/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.database.Conexion;
import modelo.Expediente;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class ExpedienteController implements Initializable {
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Expediente> expediente;
    @FXML
    private TableColumn<Expediente, Integer> id;
    @FXML
    private TableColumn<Expediente, Integer> numero;
    @FXML
    private TableColumn<Expediente, Date> fechaSalida;
    @FXML
    private TableColumn<Expediente, String> asunto;
    @FXML
    private TableColumn<Expediente, String> estado;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private JFXTextField txtnumero;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private JFXTextArea txtAsunto;

    /**
     * Initializes the controller class.
     */
    //Colecciones
	private ObservableList<Expediente> listaExpediente;
        private Conexion conexion;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          llenarCmbBox();
    llenaTabla();
    }    


    @FXML
    private void onAdd(MouseEvent event) {
        String fijo;
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }
        Expediente a = new Expediente(0,
					Integer.valueOf(txtnumero.getText()),
                                           Date.valueOf(dateFecha.getValue()),
                txtAsunto.getText(),
					fijo);
		//Llamar al metodo guardarRegistro de la clase doc
		conexion.establecerConexion();
		int resultado = a.guardarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaExpediente.add(a);
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
         Expediente selected = expediente.getSelectionModel().getSelectedItem();
           String fijo;
                   int id = selected.getCodigoExpediente();
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }
        Expediente a = new Expediente(id,
					Integer.valueOf(txtnumero.getText()),
                                           Date.valueOf(dateFecha.getValue()),
                txtAsunto.getText(),
					fijo);
		//Llamar al metodo guardarRegistro de la clase doc
		conexion.establecerConexion();
		int resultado = a.actualizarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaExpediente.set(expediente.getSelectionModel().getSelectedIndex(),a);
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro actualizado");
			mensaje.setContentText("El registro ha sido actualizado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
		}
                  limpiarComponentes();
                llenaTabla();
                expediente.setItems(listaExpediente);
    }

    @FXML
    private void onDelete(MouseEvent event) {
        		conexion.establecerConexion();
		int resultado =expediente.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaExpediente.remove(expediente.getSelectionModel().getSelectedIndex());
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro eliminado");
			mensaje.setContentText("El registro ha sido eliminado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
		}else{
                
                Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("Prohibido");
			mensaje.setContentText("El expediente contiene documentos activos.");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
                
                }
                expediente.setItems(listaExpediente);
                limpiarComponentes();
                llenaTabla();
    }



    private void limpiarComponentes() {
      	txtAsunto.setText("");
cmbEstado.setValue("Activo");
txtnumero.setText("");
dateFecha.setValue(null);

		btnAdd.setDisable(false);
		btnDelete.setDisable(true);
		btnUpdate.setDisable(true);
    }
    
    
    
     public void llenarCmbBox() {

        cmbEstado.setValue("Activo");
        cmbEstado.setItems(FXCollections.observableArrayList(
                "Activo", "Inactivo"
        ));}
     
     
        public void llenaTabla(){
        
        conexion = new Conexion();
    conexion.establecerConexion();
    //Inicializar listas
    listaExpediente = FXCollections.observableArrayList();
    //Llenar listas
    Expediente.llenarInformacion(conexion.getConnection(),listaExpediente);
    //Enlazar columnas con atributos
       expediente.setItems(listaExpediente);;
         id.setCellValueFactory(new PropertyValueFactory<Expediente,Integer>("codigoExpediente"));
        numero.setCellValueFactory(new PropertyValueFactory<Expediente,Integer>("numero"));
        fechaSalida.setCellValueFactory(new PropertyValueFactory<Expediente, Date>("fecha"));
         asunto.setCellValueFactory(new PropertyValueFactory<Expediente,String>("asunto"));
        estado.setCellValueFactory(new PropertyValueFactory<Expediente, String>("estado"));
    gestionarEventos();
    conexion.cerrarConexion();
        
        }
    public void gestionarEventos(){
		expediente.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Expediente>() {
					@Override
					public void changed(ObservableValue<? extends Expediente> arg0,
							Expediente valorAnterior, Expediente valorSeleccionado) {
							if (valorSeleccionado!=null){
                                               
								txtnumero.setText(String.valueOf(valorSeleccionado.getNumero()));
								dateFecha.setValue(valorSeleccionado.getFecha().toLocalDate());
								txtAsunto.setText(valorSeleccionado.getAsunto());
								cmbEstado.setValue(String.valueOf(valorSeleccionado.getEstado()));
						

								btnAdd.setDisable(true);
								btnDelete.setDisable(false);
								btnUpdate.setDisable(false);
							}
					}

				}
		);
	}

    @FXML
    private void numeroExpediente(KeyEvent event) {
                    char c = event.getCharacter().charAt(0);
        if ((c<'0' || c>'9')){
            
            event.consume();}  
        if (txtnumero.getText().length()== 8)
 
     event.consume();
        
        
    }
}
