/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.database.Conexion;
import modelo.Area;
import modelo.Empleado;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class EmpleadoAController implements Initializable {

    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Empleado> empleado;
    @FXML
    private TableColumn<Empleado, Integer> idEmpleado;
    @FXML
    private TableColumn<Empleado, String> nomEmpleado;
    @FXML
    private TableColumn<Empleado, String> paterno;
    @FXML
    private TableColumn<Empleado, String> materno;
    @FXML
    private TableColumn<Empleado, String> puestoEmpleado;
    @FXML
    private TableColumn<Empleado, String> dniEmpleado;
    @FXML
    private TableColumn<Empleado, Area> AreaEmpleado;
    @FXML
    private TableColumn<Empleado, String> estempleado;
    @FXML
    private TableColumn<Empleado, String> sexos;
    @FXML
    private TableColumn<Empleado, String> users;
    @FXML
    private TableColumn<Empleado, String> contraseña;
    @FXML
    private JFXTextField txtnom;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private JFXTextField txtapepat;
    @FXML
    private JFXTextField txtapemat;
    @FXML
    private JFXTextField txtpos;
    @FXML
    private JFXTextField txtdni;
    @FXML
    private JFXRadioButton rdmasc;
    @FXML
    private ToggleGroup sexo;
    @FXML
    private JFXRadioButton rdfem;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXTextField pass;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Area> listaArea;
    private ObservableList<Empleado> listaEmpleado;

    private Conexion conexion;
    @FXML
    private ComboBox<Area> cmbAreas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenaTabla();
        llenarCmbBox();
    }


    @FXML
    private void onUpdate(MouseEvent event) {
                 Empleado selected = empleado.getSelectionModel().getSelectedItem();
                   int id = selected.getCodigoEmpleado();
     String fijo;
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }
        
        Empleado a = new Empleado(id,
                txtnom.getText(),
                txtapepat.getText(),
                txtapemat.getText(),
                rdfem.isSelected() ? "F" : "M",
                txtpos.getText(),
                txtdni.getText(),
                
                
                
                
                fijo,
                user.getText(),
                pass.getText(),
                cmbAreas.getSelectionModel().getSelectedItem()
                //Condicion?ValorVerdadero:ValorFalso
);
        //Llamar al metodo guardarRegistro de la clase Alumno
        conexion.establecerConexion();
        int resultado = a.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaEmpleado.set(empleado.getSelectionModel().getSelectedIndex(),a);
			//JDK 8u>40
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("Registro actualizado");
			mensaje.setContentText("El registro ha sido actualizado exitosamente");
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
    private void onAdd(MouseEvent event) {
        String fijo;
        if (!cmbEstado.getValue().equals("Activo")) {
            fijo = "I";
        } else {
            fijo = "A";
        }
        
        Empleado a = new Empleado(0,
                txtnom.getText(),
                txtapepat.getText(),
                txtapemat.getText(),
                rdfem.isSelected() ? "F" : "M",
                txtpos.getText(),
                txtdni.getText(),
                
                
                
                
                fijo,
                user.getText(),
                pass.getText(),
                cmbAreas.getSelectionModel().getSelectedItem()
                //Condicion?ValorVerdadero:ValorFalso
);
        //Llamar al metodo guardarRegistro de la clase Alumno
        conexion.establecerConexion();
        int resultado = a.guardarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaEmpleado.add(a);
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
         eliminarRegistro();
    }

    private void gestionarEventos() {
        empleado.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Empleado>() {
                    @Override
                    public void changed(ObservableValue<? extends Empleado> arg0,
                            Empleado valorAnterior, Empleado valorSeleccionado) {
                        if (valorSeleccionado != null) {
                            txtnom.setText(String.valueOf(valorSeleccionado.getNombre()));
                            txtapemat.setText(valorSeleccionado.getApellidoM());
                            txtapepat.setText(valorSeleccionado.getApellidoP());
                            txtdni.setText(String.valueOf(valorSeleccionado.getDni()));
                            txtpos.setText(String.valueOf(valorSeleccionado.getPosicion()));
                            user.setText(String.valueOf(valorSeleccionado.getUser()));
                            pass.setText(String.valueOf(valorSeleccionado.getPass()));
                            if (valorSeleccionado.getGenero().equals("F")) {
                                rdfem.setSelected(true);
                                rdmasc.setSelected(false);
                            } else if (valorSeleccionado.getGenero().equals("M")) {
                                rdfem.setSelected(false);
                                rdmasc.setSelected(true);
                            }

                            cmbAreas.setValue(valorSeleccionado.getArea());
                            cmbEstado.setValue(valorSeleccionado.getEstado());

                            btnAdd.setDisable(true);
                            btnDelete.setDisable(false);
                            btnUpdate.setDisable(false);
                        }
                    }

                }
        );
    }

    public void llenaTabla() {

        conexion = new Conexion();
        conexion.establecerConexion();

        //Inicializar listas
        listaArea = FXCollections.observableArrayList();
        listaEmpleado = FXCollections.observableArrayList();

        //Llenar listas
        Area.llenarInformacion(conexion.getConnection(), listaArea);
        Empleado.llenarInformacion(conexion.getConnection(), listaEmpleado);

        //Enlazar listas con ComboBox y TableView
        cmbAreas.setItems(listaArea);
  
        empleado.setItems(listaEmpleado);

        //Enlazar columnas con atributos
        idEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        nomEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        paterno.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoP"));
        materno.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoM"));
        puestoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("posicion"));
        dniEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dni"));
        estempleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("estado"));
        sexos.setCellValueFactory(new PropertyValueFactory<Empleado, String>("genero"));
        users.setCellValueFactory(new PropertyValueFactory<Empleado, String>("user"));
        contraseña.setCellValueFactory(new PropertyValueFactory<Empleado, String>("pass"));
        AreaEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Area>("area"));

        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void llenarCmbBox() {

        cmbEstado.setValue("Activo");
        cmbEstado.setItems(FXCollections.observableArrayList(
                "Activo", "Inactivo"
        ));
    }

    private void eliminarRegistro() {
      conexion.establecerConexion();
		int resultado = empleado.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
		conexion.cerrarConexion();

		if (resultado == 1){
			listaEmpleado.remove(empleado.getSelectionModel().getSelectedIndex());
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
                empleado.setItems(listaEmpleado);
                 limpiarComponentes();
	} 
   public void limpiarComponentes(){
     txtapemat.setText(null);
		txtapepat.setText(null);
		txtnom.setText(null);
		txtdni.setText(null);
                txtpos.setText(null);
                user.setText(null);
               pass.setText(null);
		rdfem.setSelected(false);
		rdmasc.setSelected(false);
		
		cmbAreas.setValue(null);
		cmbEstado.setValue("Activo");

		btnAdd.setDisable(false);
		btnDelete.setDisable(true);
		btnUpdate.setDisable(true); 
   
   } 

    @FXML
    private void nombreEmpleado(KeyEvent event) {
              
         char key = event.getCharacter().charAt(0);
        if(!Character.isLetter(key))
            event.consume();
    }

    @FXML
    private void apellidoPaternoEmp(KeyEvent event) {
              
         char key = event.getCharacter().charAt(0);
        if(!Character.isLetter(key))
            event.consume();
    }

    @FXML
    private void apellidoMaternoEmp(KeyEvent event) {
              
         char key = event.getCharacter().charAt(0);
        if(!Character.isLetter(key))
            event.consume();
    }

    @FXML
    private void PuestoEmp(KeyEvent event) {
              
         char key = event.getCharacter().charAt(0);
        if(!Character.isLetter(key))
            event.consume();
    }

    @FXML
    private void dniEmpleado(KeyEvent event) {
            char c = event.getCharacter().charAt(0);
        if ((c<'0' || c>'9')){
            
            event.consume();}  
        if (txtdni.getText().length()== 8)
 
     event.consume();
    }
}
