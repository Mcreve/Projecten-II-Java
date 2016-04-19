/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Append
 */
public class AddLearningUtilityController  {

    @FXML
    public AnchorPane view;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private CheckBox chkLoanable;
    @FXML
    private TextField txtAmountInStock;
    @FXML
    private TextField txtAmountUnavailable;
    @FXML
    private ComboBox<?> cboCompany;
    @FXML
    private ComboBox<?> cboLocation;
    @FXML
    private TextField txtArticleNumber;
    @FXML
    private ComboBox<?> cboFieldOfStudy;
    @FXML
    private ComboBox<?> cboTargetGroup;
    @FXML
    private Button btnAdd;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnReset;
   private final DomainController domainController;

    public AddLearningUtilityController(DomainController domainController) throws IOException
    {
        this.domainController = domainController;
        initView();
       
    }
    private void initView() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartUp.class.getResource("AddLearningUtility.fxml"));
        try {
            view = (AnchorPane) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddLearningUtilityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
