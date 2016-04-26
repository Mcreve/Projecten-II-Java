/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML
    private Label lblInfo;


    
    public LearningUtilityCreationPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        populateListViews();
        populateComboBoxes();
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
        //Set selectionmodes to multiple selection
        lstFieldsOfStudy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstTargetGroups.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        //Actually fill from database
        lstFieldsOfStudy.setItems(FXCollections.observableArrayList(domainController.getFieldsOfStudy()));
        lstTargetGroups.setItems(FXCollections.observableArrayList(domainController.getTargetGroups()));
    }
    private void populateComboBoxes()
    {
        //Actually fill from database
        cboLocations.setItems(FXCollections.observableArrayList(domainController.getLocations()));
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
        
        cboLocations.getSelectionModel().select(ZERO_INTEGER);
        lstFieldsOfStudy.getSelectionModel().select(ZERO_INTEGER);
        lstTargetGroups.getSelectionModel().select(ZERO_INTEGER);
        cboCompanies.getSelectionModel().select(ZERO_INTEGER);
        lblInfo.setText(EMPTY_STRING);
    }
    @FXML
    private void add(ActionEvent event) throws IllegalArgumentException 
    {
        String infoMessage = validateFields();
                
        if(infoMessage != EMPTY_STRING)
        {
           lblInfo.setText(infoMessage);
           return;
        }
        
        String name = txtName.getText();
        String description = txtDescription.getText();
        BigDecimal price = new BigDecimal(txtPrice.getText());
        Boolean loanable = chkLoanable.isSelected();
        String articleNumber = txtArticleNumber.getText();
        String imageUrl = txtImage.getText();
        Integer amountInStock = Integer.valueOf(txtAmountInStock.getText());
        Integer amountUnavailable = Integer.valueOf(txtAmountUnavailable.getText());
        String company = cboCompanies.getSelectionModel().getSelectedItem().toString();
        String location = cboLocations.getSelectionModel().getSelectedItem().toString();

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
        try
        {
            domainController.addLearningUtility(
                                            name,
                                            description,
                                            price,
                                            loanable,
                                            articleNumber,
                                            imageUrl,
                                            amountInStock,
                                            amountUnavailable,
                                            company,
                                            location,
                                            targetGroupsList,
                                            fieldsOfStudyList);   
            
        lblInfo.setText(name + " werd succesvol toegevoegd.");

        }catch(Exception e)
        {
            lblInfo.setText(e.getMessage());
        }
    }

    @FXML
    private void reset(ActionEvent event) {
        populateListViews();
        populateComboBoxes();
        setDefaults();
    }
    
    private String validateFields()
    {
        if(txtName.getText() == null ? EMPTY_STRING == null : txtName.getText().equals(EMPTY_STRING))
            return "Gelieve de Naam in te vullen.";
        
        if(!txtAmountInStock.getText().matches("[0-9]+?"))
            return "Aantal in stock moet een numerisch getal zijn.";
        
        if(Integer.valueOf(txtAmountInStock.getText()) < 1)
            return "Aantal in stock moet meer dan 1 zijn."; 
        
        if(!txtAmountUnavailable.getText().matches("[0-9]+?"))
            return "Aantal onbeschikbaar moet een numerisch getal zijn.";
        
        if(!txtPrice.getText().matches("[0-9]+(\\.[0-9][0-9]?)?"))
            return "De prijs moet een numerisch getal zijn, met een '.' voor decimalen."; 
        
        return EMPTY_STRING;
    }
}
