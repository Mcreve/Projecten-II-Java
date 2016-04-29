/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.creationPanels;

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
public class TargetGroupCreationPanelController extends GridPane {

    @FXML
    private TextField txtTargetGroup;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblMessage;
    private DomainController domainController;
    
    public TargetGroupCreationPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
    }
    
    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TargetGroupCreationPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeThisStage();
    }

    @FXML
    private void addTargetGroup(ActionEvent event) {
        try{
            domainController.createTargetGroup(txtTargetGroup.getText().trim());
            closeThisStage();
        } catch(IllegalArgumentException e){
            lblMessage.setText(e.getMessage());
        }
    }

    private void closeThisStage() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
}
