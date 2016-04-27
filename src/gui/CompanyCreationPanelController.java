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
public class CompanyCreationPanelController extends GridPane {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtWebsite;
    @FXML
    private TextField txtContactPerson;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtEmail;
    private DomainController domainController;
    
    public CompanyCreationPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CompanyCreationPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void resetFields(ActionEvent event) {
        txtName.setText("");
        txtWebsite.setText("");
        txtContactPerson.setText("");
        txtEmail.setText("");
        lblMessage.setText("");
    }

    @FXML
    private void addCompany(ActionEvent event) {
        try{
            domainController.createCompany(txtName.getText().trim(), txtWebsite.getText().trim(), txtContactPerson.getText().trim(), txtEmail.getText().trim());
            Stage stage = (Stage) btnAdd.getScene().getWindow();
            stage.close();
        } catch(IllegalArgumentException e){
            lblMessage.setText(e.getMessage());
        }
    }
    
}
