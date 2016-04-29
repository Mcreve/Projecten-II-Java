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
public class LocationCreationPanelController extends GridPane {

    @FXML
    private TextField txtLocation;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancel;
    private DomainController domainController;
    @FXML
    private Label lblMessage;
    
    public LocationCreationPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
    }
    
    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationCreationPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addLocation(ActionEvent event) {
        try{
            domainController.createLocation(txtLocation.getText().trim());
            closeThisStage();
        } catch(IllegalArgumentException e){
            lblMessage.setText(e.getMessage());
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeThisStage();
    }
    
    private void closeThisStage(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
}
