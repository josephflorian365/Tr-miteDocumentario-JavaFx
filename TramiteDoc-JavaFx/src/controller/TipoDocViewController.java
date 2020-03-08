/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.database.Conexion;
import modelo.Area;
import modelo.TipoDoc;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class TipoDocViewController implements Initializable {
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private AnchorPane rootpane;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private JFXTextField txtnom;
    @FXML
    private TableView<TipoDoc> tipdoc;
    @FXML
    private TableColumn<TipoDoc, Integer> idtd;
    @FXML
    private TableColumn<TipoDoc, String> nombretd;
    @FXML
    private TableColumn<TipoDoc, String> estadotd;

    /**
     * Initializes the controller class.
     */
     private Conexion conexion;
  private ObservableList<TipoDoc> listaTipDoc;
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
        TipoDoc a = new TipoDoc(0,
					txtnom.getText(),
					fijo);
		//Llamar al metodo guardarRegistro de la clase doc
		conexion.establecerConexion();
		int resultado = a.guardarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaTipDoc.add(a);
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
    private void onUpdate(MouseEvent event) {
        TipoDoc selected = tipdoc.getSelectionModel().getSelectedItem();
        int id = selected.getCodigoTipDoc();
         String fijo;
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }
        TipoDoc a = new TipoDoc(
				id,
				txtnom.getText(),
				fijo);
		conexion.establecerConexion();
		int resultado = a.actualizarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaTipDoc.set(tipdoc.getSelectionModel().getSelectedIndex(),a);
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro actualizado");
			mensaje.setContentText("El registro ha sido actualizado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
		}
                limpiarComponentes();
                tipdoc.setItems(listaTipDoc);
             
                llenaTabla();
    }

    @FXML
    private void onDelete(MouseEvent event) {
        eliminarRegistro();
    }

    @FXML
    private void onNew(MouseEvent event) {
        limpiarComponentes();
        
    }
  	public void gestionarEventos(){
		tipdoc.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<TipoDoc>() {
					@Override
					public void changed(ObservableValue<? extends TipoDoc> arg0,
							TipoDoc valorAnterior, TipoDoc valorSeleccionado) {
							if (valorSeleccionado!=null){
								
								txtnom.setText(valorSeleccionado.getNombreTipDoc());
								cmbEstado.setValue(valorSeleccionado.getEstado());

								btnAdd.setDisable(true);
								btnDelete.setDisable(false);
								btnUpdate.setDisable(false);
							}
					}

                 

				}
		);
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
    listaTipDoc = FXCollections.observableArrayList();
    //Llenar listas
    TipoDoc.llenarInformacion(conexion.getConnection(),listaTipDoc);
    //Enlazar columnas con atributos
       tipdoc.setItems(listaTipDoc);;
         idtd.setCellValueFactory(new PropertyValueFactory<TipoDoc,Integer>("codigoTipDoc"));
        nombretd.setCellValueFactory(new PropertyValueFactory<TipoDoc,String>("nombreTipDoc"));
        estadotd.setCellValueFactory(new PropertyValueFactory<TipoDoc, String>("estado"));
    gestionarEventos();
    conexion.cerrarConexion();
        
        }
        
        public void limpiarComponentes(){
		txtnom.setText("");
cmbEstado.setValue("Activo");
		btnAdd.setDisable(false);
		btnDelete.setDisable(true);
		btnUpdate.setDisable(true);
	}
        
       public void eliminarRegistro(){
		conexion.establecerConexion();
		int resultado = tipdoc.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaTipDoc.remove(tipdoc.getSelectionModel().getSelectedIndex());
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro eliminado");
			mensaje.setContentText("El registro ha sido eliminado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
		}else{
                
                Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("Prohibido");
			mensaje.setContentText("El tipo de documento esta en uso.");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
                
                }
                tipdoc.setItems(listaTipDoc);
                limpiarComponentes();
                llenaTabla();
	} 

    @FXML
    private void descripcionDoc(KeyEvent event) {
            char key = event.getCharacter().charAt(0);
        if(!Character.isLetter(key))
            event.consume();
    }

}

