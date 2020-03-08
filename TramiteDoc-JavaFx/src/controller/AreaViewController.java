/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.database.Conexion;
import modelo.Area;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class AreaViewController implements Initializable {
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Area> tableArea;
    @FXML
    private TableColumn<Area, Integer> idArea;
    @FXML
    private TableColumn<Area, String> nomArea;
    @FXML
    private TableColumn<Area, String> estArea;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnNuevo;
    @FXML
    private JFXTextField txtnomarea;
    @FXML
    private JFXComboBox<String> cmbEstado;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Area> listaArea;
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
        Area a = new Area(0,
					txtnomarea.getText(),
					fijo);
		//Llamar al metodo guardarRegistro de la clase Alumno
		conexion.establecerConexion();
		int resultado = a.guardarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaArea.add(a);
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro agregado");
			mensaje.setContentText("El registro ha sido agregado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
		}
    }

    @FXML
    private void onUpdate(MouseEvent event) {
        Area selected = tableArea.getSelectionModel().getSelectedItem();
        int id = selected.getCodigoArea();
         String fijo;
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }
        Area a = new Area(
				id,
				txtnomarea.getText(),
				fijo);
		conexion.establecerConexion();
		int resultado = a.actualizarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaArea.set(tableArea.getSelectionModel().getSelectedIndex(),a);
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro actualizado");
			mensaje.setContentText("El registro ha sido actualizado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
		}
                limpiarComponentes();
                tableArea.setItems(listaArea);
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
		tableArea.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Area>() {
					@Override
					public void changed(ObservableValue<? extends Area> arg0,
							Area valorAnterior, Area valorSeleccionado) {
							if (valorSeleccionado!=null){
								
								txtnomarea.setText(valorSeleccionado.getNombreArea());
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
    listaArea = FXCollections.observableArrayList();
    //Llenar listas
    Area.llenarInformacion(conexion.getConnection(),listaArea);
    //Enlazar columnas con atributos
       tableArea.setItems(listaArea);;
         idArea.setCellValueFactory(new PropertyValueFactory<Area,Integer>("codigoArea"));
        nomArea.setCellValueFactory(new PropertyValueFactory<Area,String>("nombreArea"));
        estArea.setCellValueFactory(new PropertyValueFactory<Area, String>("estado"));
    gestionarEventos();
    conexion.cerrarConexion();
        
        }
        
        public void limpiarComponentes(){
		txtnomarea.setText("");
cmbEstado.setValue("Activo");
		btnAdd.setDisable(false);
		btnDelete.setDisable(true);
		btnUpdate.setDisable(true);
	}
        
       public void eliminarRegistro(){
		conexion.establecerConexion();
		int resultado = tableArea.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaArea.remove(tableArea.getSelectionModel().getSelectedIndex());
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro eliminado");
			mensaje.setContentText("El registro ha sido eliminado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
		}else{
                
                Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("Prohibido");
			mensaje.setContentText("El registro no puede ser eliminado, porque el area contiene empleados.");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
                
                }
                tableArea.setItems(listaArea);
	} 

    @FXML
    private void nombreArea(KeyEvent event) {
        
         char key = event.getCharacter().charAt(0);
        if(!Character.isLetter(key))
            event.consume();
    }
}
