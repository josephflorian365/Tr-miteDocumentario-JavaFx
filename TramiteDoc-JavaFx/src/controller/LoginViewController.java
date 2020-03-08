/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.dao.EmpleadoDAO;
import model.dominio.Empleado;

/**
 *
 * @author Julio
 */
public class LoginViewController implements Initializable {

    private Label label;
    @FXML
    private TabPane tabPaneLogin;
    @FXML
    private Tab tabAdmin;
    @FXML
    private Tab tabUser;
    @FXML
    private Pane slidingPane;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblAdmin;
    @FXML
    private Label lblUser;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField adminUser;
    @FXML
    private JFXPasswordField adminPass;

    EmpleadoDAO empdao = new EmpleadoDAO();
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnuser;
    @FXML
    private JFXTextField txtusuario;
    @FXML
    private JFXPasswordField txtpasswordusu;

    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    @FXML
    private void openAdminTab(MouseEvent event) {
        //Animación de translacion de tabadmin 

        TranslateTransition toRightAnimation = new TranslateTransition(new Duration(500), lblStatus);

        //Arreglo
        toRightAnimation.setToX(slidingPane.getTranslateX());
        toRightAnimation.play();
        toRightAnimation.setOnFinished((ActionEvent event2) -> {
            lblStatus.setText("ADMINISTRATOR");
        });
        tabPaneLogin.getSelectionModel().select(tabAdmin);
    }

    @FXML
    private void openUserTab(MouseEvent event) {
        //Animación de translacion al dar clic en el label

        TranslateTransition toRightAnimation = new TranslateTransition(new Duration(500), lblStatus);

        //Arreglo
        toRightAnimation.setToX(slidingPane.getTranslateX() + (slidingPane.getPrefWidth() - lblStatus.getPrefWidth()));
        toRightAnimation.play();
        toRightAnimation.setOnFinished((ActionEvent event1) -> {
            lblStatus.setText("USER");
        });
        tabPaneLogin.getSelectionModel().select(tabUser);
    }

   

    @FXML
    private void openLogin(MouseEvent event1) throws IOException {

 String user = adminUser.getText();
      String pass = adminPass.getText();
        System.out.println(user);
        System.out.println(pass);
        Empleado emp = new Empleado();
        emp = empdao.validarEmpleado(user, pass);
        if (emp.getUsuario()!= null && emp.getPassword()!= null) {

            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/view/PrincipalView.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) event1.getSource()).getScene().getWindow();
            app_stage.hide(); //optional
            app_stage.setScene(home_page_scene);
            app_stage.show();
//Y ahora dentro del Stage me metes la escena que anteriormente hemos leído y metido en root1

// Y ahora le digo que me muestres el stage
        }

    }

    @FXML
    private void openUser(MouseEvent event) throws IOException {
        String user = txtusuario.getText();
      String pass = txtpasswordusu.getText();
        System.out.println(user);
        System.out.println(pass);
        Empleado emp = new Empleado();
        emp = empdao.validarUsuario(user, pass);
        if (emp.getUsuario()!= null && emp.getPassword()!= null) {

            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/view/PrincipalViewUsuario.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide(); //optional
            app_stage.setScene(home_page_scene);
            app_stage.show();
//Y ahora dentro del Stage me metes la escena que anteriormente hemos leído y metido en root1

// Y ahora le digo que me muestres el stage
        }
        
        
        
    }

}