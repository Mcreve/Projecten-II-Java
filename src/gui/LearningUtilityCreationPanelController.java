/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import domain.learningUtility.TargetGroup;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Append
 */
public class LearningUtilityCreationPanelController extends GridPane {

    @FXML
    private TextField txtName;
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
    @FXML
    private ListView<String> lstTargetGroups;
    @FXML
    private ListView<String> lstFieldsOfStudy;
    @FXML
    private ComboBox<String> cboCompanies;
    @FXML
    private ComboBox<String> cboLocations;
    
    private DomainController domainController;
    private static final String EMPTY_STRING = "";
    private static final double ZERO_DOUBLE = 0.00;
    private static final int ZERO_INTEGER = 0;
    private static final String DEFAULT_HTTP = "Http://";


    
    public LearningUtilityCreationPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        populateListViews();
        setDefaults();
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
    private void populateListViews(){
        lstFieldsOfStudy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstTargetGroups.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        cboLocations.setItems(FXCollections.observableArrayList(domainController.getLocations()));
        lstFieldsOfStudy.setItems(FXCollections.observableArrayList(domainController.getFieldsOfStudy()));
        lstTargetGroups.setItems(FXCollections.observableArrayList(domainController.getTargetGroups()));
        cboCompanies.setItems(FXCollections.observableArrayList(domainController.getCompanies()));
    }
    
        private void setDefaults()
    {
        txtName.setText(EMPTY_STRING);
        txtArticleNumber.setText(EMPTY_STRING);
        txtAmountInStock.setText(String.valueOf(ZERO_INTEGER));
        txtAmountUnavailable.setText(String.valueOf(ZERO_INTEGER));
        txtDescription.setText(EMPTY_STRING);
        txtImage.setText(DEFAULT_HTTP); 
        chkLoanable.setSelected(true);
        txtPrice.setText(String.valueOf(ZERO_DOUBLE));
        
        cboLocations.getSelectionModel().clearSelection();
        lstFieldsOfStudy.getSelectionModel().clearSelection();
        lstTargetGroups.getSelectionModel().clearSelection();
        cboCompanies.getSelectionModel().clearSelection();
    }
    @FXML
    private void add(ActionEvent event) {
        List<String> targetGroupsList = new ArrayList<>();        
        List<String> fieldsOfStudyList = new ArrayList<>();        
        
        ObservableList<String> targetGroupsObservers = lstTargetGroups.getSelectionModel().getSelectedItems();
        ObservableList<String> fieldsOfStudyObservers = lstFieldsOfStudy.getSelectionModel().getSelectedItems();
        
        for(String item : targetGroupsObservers )
        {
            targetGroupsList.add(item);            
        }
        for(String item : fieldsOfStudyObservers )
        {
            fieldsOfStudyList.add(item);            
        }     
        try{
         domainController.addLearningUtility(txtName.getText(),
                                            txtDescription.getText(),
                                            new BigDecimal(txtPrice.getText()),
                                            chkLoanable.isSelected(),
                                            txtArticleNumber.getText(),
                                            txtImage.getText(),
                                            Integer.valueOf(txtAmountInStock.getText()),
                                            Integer.valueOf(txtAmountUnavailable.getText()),
                                            cboCompanies.getSelectionModel().getSelectedItem().toString(),
                                            cboLocations.getSelectionModel().getSelectedItem(),
                                            targetGroupsList,
                                            fieldsOfStudyList);           
        }catch(Exception e)
        {
            System.out.println(e);
        }

    }

    @FXML
    private void reset(ActionEvent event) {
        populateListViews();
        setDefaults();
    }
    
}
