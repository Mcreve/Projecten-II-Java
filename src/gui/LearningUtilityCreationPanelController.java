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
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Append
 */
public class LearningUtilityCreationPanelController extends GridPane implements Observer {

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
    private static final String UNKNOWN = "Onbekend";
    
    @FXML
    private Label lblInfo;
    @FXML
    private Button btnNewTargetGroup;
    @FXML
    private Button btnNewFieldOfStudy;
    @FXML
    private Button btnNewLocation;
    @FXML
    private Button btnNewCompany;


    
    public LearningUtilityCreationPanelController(DomainController domainController){
        this.domainController = domainController;
        registerAsObserver();
        initLoader();
        populateListViews();
        populateComboBoxes();
        setDefaults();
    } 

    private void registerAsObserver() {
        domainController.addCompanyObserver(this);
        domainController.addFieldOfStudyObserver(this);
        domainController.addTargetGroupObserver(this);
        domainController.addLocationObserver(this);
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
        
        cboLocations.getSelectionModel().clearSelection();
        lstFieldsOfStudy.getSelectionModel().clearSelection();
        lstTargetGroups.getSelectionModel().clearSelection();
        cboCompanies.getSelectionModel().clearSelection();
        lblInfo.setText(EMPTY_STRING);
    }
    @FXML
    private void add(ActionEvent event) throws IllegalArgumentException 
    {
        String infoMessage = validateFields();
                
        if(!infoMessage.equals(EMPTY_STRING))
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
        String company = cboCompanies.getSelectionModel().getSelectedIndex()  == -1 ? UNKNOWN  : cboCompanies.getSelectionModel().getSelectedItem();
        String location = cboLocations.getSelectionModel().getSelectedIndex() == -1 ? UNKNOWN : cboLocations.getSelectionModel().getSelectedItem();
        
        if(description.isEmpty())
            description = UNKNOWN;  
        
        if(articleNumber.isEmpty())
            articleNumber = UNKNOWN;  
        
         if(imageUrl.isEmpty())
            imageUrl = UNKNOWN;        
  
        
        List<String> targetGroupsList = new ArrayList<>();        
        List<String> fieldsOfStudyList = new ArrayList<>();        
        if (lstTargetGroups.getSelectionModel().getSelectedIndex()== -1)
        {
            targetGroupsList.add(UNKNOWN);
        }else
        {
            lstTargetGroups.getSelectionModel().getSelectedItems().stream().forEach((item) -> {
                targetGroupsList.add(item);
            });
        }
        
        if(lstFieldsOfStudy.getSelectionModel().getSelectedIndex()== -1)
        {
            fieldsOfStudyList.add(UNKNOWN);
        }else
        {
            lstFieldsOfStudy.getSelectionModel().getSelectedItems().stream().forEach((item) -> {
                fieldsOfStudyList.add(item);
            });
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

    @FXML
    private void showNewTargetGroupDialog(ActionEvent event) {        
        createNewStageAndShow("Doelgroep toevoegen", new Scene(new TargetGroupCreationPanelController(domainController)));
    }

    @FXML
    private void showNewFieldOfStudyDialog(ActionEvent event) {
        createNewStageAndShow("Doelgroep toevoegen", new Scene(new FieldOfStudyCreationPanelController(domainController)));
    }

    @FXML
    private void showNewLocationDialog(ActionEvent event) {
        createNewStageAndShow("Locatie toevoegen", new Scene(new LocationCreationPanelController(domainController)));
    }

    @FXML
    private void showNewCompanyDialog(ActionEvent event) {        
        createNewStageAndShow("Bedrijf toevoegen", new Scene(new CompanyCreationPanelController(domainController)));        
    }

    @Override
    public void update(Observable o, Object arg) {
        populateComboBoxes();
        populateListViews();
    }
    
    private void createNewStageAndShow(String title, Scene scene){
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
