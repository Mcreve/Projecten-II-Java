/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class LoginPanelController extends GridPane {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    private DomainController domainController;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnCancel;
    
    public LoginPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
    }
    
    private void initLoader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException e) 
        {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void login(ActionEvent event) {
        try{
            //Uncomment following line to enable login
            //domainController.login(txtUsername.getText(), txtPassword.getText());
            Scene scene = new Scene(new MainPanelController(domainController));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IllegalArgumentException e){
            lblMessage.setText(e.getMessage());
        } 
        
    }
    
    private void closeThisStage(){
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeThisStage();
    }
    
}
