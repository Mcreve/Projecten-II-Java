/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class LearningUtilityCreationPanelController extends GridPane {

    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> cmbLocation;
    @FXML
    private ComboBox<String> cmbTargetGroups;
    @FXML
    private ComboBox<String> cmbFieldsOfStudy;
    @FXML
    private CheckBox chkLoanable;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtArticleNumber;
    @FXML
    private TextField txtAmountInStock;
    @FXML
    private TextField txtAmountUnavailable;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtImage;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnReset;
    
    private DomainController domainController;
    
    public LearningUtilityCreationPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        populateComboboxes();
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LearningUtilityCreationPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void populateComboboxes(){
  
        cmbLocation.setItems(FXCollections.observableArrayList(domainController.getLocations()));
        cmbFieldsOfStudy.setItems(FXCollections.observableArrayList(domainController.getFieldsOfStudy()));
        cmbTargetGroups.setItems(FXCollections.observableArrayList(domainController.getTargetGroups()));
        
    }
    
    @FXML
    private void add(ActionEvent event) {
        int[] targetGroups = {1};
        int[] fieldsOfStudy = {1};
        domainController.addLearningUtility("Wereldbol Demo item", "Item voor het demonstreren van het domain en persistentie", BigDecimal.ZERO, true, "tst0001", "/Images/wereldbol.jpg", 5, 1, 1, 1, targetGroups, fieldsOfStudy);
    }

    @FXML
    private void reset(ActionEvent event) {
    }
    
}
