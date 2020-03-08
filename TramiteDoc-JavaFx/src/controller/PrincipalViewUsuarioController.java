/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class PrincipalViewUsuarioController implements Initializable {
    
    @FXML
    private JFXButton btnArea;
    @FXML
    private JFXButton btnEmpleado;
    @FXML
    private JFXButton btnDocumento;
    @FXML
    private JFXButton btnTipDoc;
    @FXML
    private JFXButton btnEnvio;
    @FXML
    private JFXButton btnExpediente;
    @FXML
    public BorderPane anchorPane;
    @FXML
    private Circle img;


    @FXML
    private Label lblusu;
 

    /**
     * Initializes the controller class.
     */

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    
    
    public void initialize(URL url, ResourceBundle rb) {
  retornararImg();

    }    

    

    @FXML
    private void onDocumento(MouseEvent event) {
    loadUI("/view/DocumentoA");
    }

    @FXML
    private void onTipDoc(MouseEvent event) {
        loadUI("/view/TipoDocA");
    }

    @FXML
    private void onEnvio(MouseEvent event) {
        loadUI("/view/EnviosA");
    }

    @FXML
    private void onExpediente(MouseEvent event) {
        loadUI("/view/ExpedienteA");
    }
    
    
    
    
    public void loadUI(String ui)throws AbstractMethodError{
    Parent root = null;
    
        try {
            root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        } catch (IOException e) {
            Logger.getLogger(PrincipalViewController.class.getName()).log(Level.SEVERE, null, e);
        }
        
         anchorPane.setCenter(root);
    
    }
    
    public void retornararImg(){
     img.setStroke(Color.TRANSPARENT);
        Image im = new Image("/images/doqinter.png",false);
        img.setFill(new ImagePattern(im));
        img.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    
    
    
    }


}
