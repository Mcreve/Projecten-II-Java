/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import domain.LearningUtility;
import domain.Location;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.TypedQuery;
import persistence.Connection;
import persistence.LearningUtilityRepository;

/**
 * FXML Controller class
 *
 * @author Append
 */
public class AddLearningUtilityController{
    private DomainController domainController;
    
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

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        try
        {
                    
            domainController.addLearningUtility(txtName.getText(), txtDescription.getText(), new BigDecimal(txtPrice.getText()), chkLoanable.isSelected(), txtArticleNumber.getText(), null, 1, 2, 4, 2,null,null );

        }catch(Exception e)
        {
            System.out.println("error:" + e.toString());
        }
    }
    
    public void AddLearningUtilityController(DomainController domainController)
    {
        this.domainController = domainController;
                            btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
    }
    
}
