/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.users;

import domain.DomainController;
import domain.interfaces.IObserver;
import domain.users.Lector;
import domain.users.Manager;
import domain.users.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Maxim
 */
public class UserConfigurationPanelController extends GridPane implements IObserver {

    @FXML
    private TextField txtName;
    @FXML
    private Label lblInfo;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private CheckBox adminCheckBox;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnListLector;
    @FXML
    private Button btnReset;

    private DomainController domainController;

    /**
     * Initializes the controller class.
     */
    public UserConfigurationPanelController(DomainController domainController) {
        this.domainController = domainController;
        initLoader();
        domainController.addObserver(this);
        domainController.addObserverToCatalog(this, User.class);
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserConfigurationPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        
        User deletedUser = domainController.deleteAdmin(domainController.getCurrentUser());
        updateLabels(deletedUser);
        
    }
    @FXML
    private void reset(ActionEvent event){
        updateLabels(null);
        domainController.setCurrentUser(null);
    }

    @FXML
    private void edit(ActionEvent event) {
       
       User user = domainController.getCurrentUser();
       user.setFirstName(txtFirstName.getText());
       user.setLastName(txtName.getText());
       user.setEmailAddress(txtEmail.getText());
       if(adminCheckBox.isSelected())
       {
          Manager upgradedManager = (Manager) domainController.makeAdmin(user);
           updateLabels(upgradedManager);
           
           
       }
       else{
         Lector downgradedManager =   (Lector) domainController.removeAdmin(user);
           updateLabels(downgradedManager);
       }
       
       
    }


    @FXML
    private void add(ActionEvent event) {
        
      
        Manager manager = (Manager) domainController.createAdmin(txtEmail.getText(),txtFirstName.getText(), txtName.getText());
        updateLabels(manager);
       
        
    }

    @Override
    public void update() {
        
        if(domainController.userIsSet()){
            User user = domainController.getCurrentUser();
            txtName.setText(user.getLastName());
            txtFirstName.setText(user.getFirstName());
            txtEmail.setText(user.getEmailAddress());
            adminCheckBox.setSelected(user instanceof Manager);
        }

    }  
        public void updateLabels(User user){
            if(user == null)
            {
            txtEmail.clear();
            txtFirstName.clear();
            txtName.clear();
            adminCheckBox.setSelected(false);
            domainController.setCurrentUser(null);
            }
            else{
           txtFirstName.setText(user.getFirstName());
           txtFirstName.setText(user.getFirstName());
           adminCheckBox.setSelected(user instanceof Manager);
           domainController.setCurrentUser(user);
            }
          
}   
    }


