/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import domain.learningUtility.LearningUtility;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
 * @author Maxim
 */
public class LearningUtilityEditDetailsPanelController extends GridPane implements Observer {

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
    private Button btnEdit;
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
    @FXML
    private Button btnDelete;
    
    private DomainController domainController;
    private static final String DEFAULT_HTTP = "Http://";
    private static final String UNKNOWN = "Onbekend";

    
       public LearningUtilityEditDetailsPanelController (DomainController domainController){
        this.domainController = domainController;
        registerAsObserver();
        initLoader();
        populateListViews();
        populateComboBoxes();
        loadLearningUtilityDetails();
    } 
        private void registerAsObserver() {
        domainController.addCompanyObserver(this);
        domainController.addFieldOfStudyObserver(this);
        domainController.addTargetGroupObserver(this);
        domainController.addLocationObserver(this);
    }
        
         private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LearningUtilityEditDetailsPanel.fxml"));
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
        private void loadLearningUtilityDetails()
        {
        LearningUtility selectedLearningUtility = domainController.getSelectedLearningUtility();
        txtName.setText(selectedLearningUtility.getName());
        txtArticleNumber.setText(selectedLearningUtility.getArticleNumber());
        txtAmountInStock.setText(String.valueOf(selectedLearningUtility.getAmountInCatalog()));
        txtAmountUnavailable.setText(String.valueOf(selectedLearningUtility.getAmountUnavailable()));
        txtDescription.setText(selectedLearningUtility.getDescription());
        txtImage.setText(selectedLearningUtility.getPicture()); 
        chkLoanable.setSelected(selectedLearningUtility.getLoanable());
        txtPrice.setText(String.valueOf(selectedLearningUtility.getPrice()));
        
        cboLocations.getSelectionModel().clearSelection();
        lstFieldsOfStudy.getSelectionModel().clearSelection();
        lstTargetGroups.getSelectionModel().clearSelection();
        cboCompanies.getSelectionModel().clearSelection();
  
        }   

    @FXML
    private void edit(ActionEvent event) {
        
        LearningUtility currentLearningUtility = domainController.getSelectedLearningUtility();
        
        String infoMessage = validateFields();
                
        if(!infoMessage.equals(""))
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
            
                domainController.editLearningUtility(currentLearningUtility, name,
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
  
            
        lblInfo.setText(name + " werd succesvol gewijzigd.");

        }catch(Exception e)
        {
            lblInfo.setText(e.getMessage());
        }
    }

    private String validateFields()
    {
        if(txtName.getText() == null ? "" == null : txtName.getText().equals(""))
            return "Gelieve de Naam in te vullen.";
        
        if(!txtAmountInStock.getText().matches("[0-9]+?"))
            return "Aantal in stock moet een numerisch getal zijn.";
        
        if(Integer.valueOf(txtAmountInStock.getText()) < 1)
            return "Aantal in stock moet meer dan 1 zijn."; 
        
        if(!txtAmountUnavailable.getText().matches("[0-9]+?"))
            return "Aantal onbeschikbaar moet een numerisch getal zijn.";
        
        if(!txtPrice.getText().matches("[0-9]+(\\.[0-9][0-9]?)?"))
            return "De prijs moet een numerisch getal zijn, met een '.' voor decimalen."; 
        
        return "";
    }
    @FXML
    private void reset(ActionEvent event) {
    
        loadLearningUtilityDetails();
}

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    

    @FXML
    private void delete(ActionEvent event) {
    }

    @FXML
    private void showNewTargetGroupDialog(ActionEvent event) {
    }

    @FXML
    private void showNewFieldOfStudyDialog(ActionEvent event) {
    }

    @FXML
    private void showNewLocationDialog(ActionEvent event) {
    }

    @FXML
    private void showNewCompanyDialog(ActionEvent event) {
    }

}
