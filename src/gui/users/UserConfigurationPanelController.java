/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.users;

import domain.DomainController;
import domain.interfaces.IObserver;
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
    private Button btnDellete;
    @FXML
    private Button btnEdit;
    @FXML
    private CheckBox adminCheckBox;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnListLector;

    private DomainController domainController;

    /**
     * Initializes the controller class.
     */
    public UserConfigurationPanelController(DomainController domainController) {
        this.domainController = domainController;
        initLoader();
        domainController.addObserver(this);
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
        
        domainController.deleteAdmin(domainController.getCurrentUserAdminPanel());
        domainController.setCurrentUserAdminPanel(null);
    }

    @FXML
    private void edit(ActionEvent event) {
       
       User user = domainController.getCurrentUserAdminPanel();
       user.setFirstName(txtFirstName.getText());
       user.setLastName(txtName.getText());
       user.setEmailAddress(txtEmail.getText());
       if(adminCheckBox.isSelected())
       {
           domainController.makeAdmin(user);
       }
       else{
           domainController.removeAdmin(user);
       }
       
       
    }


    @FXML
    private void add(ActionEvent event) {
        
        domainController.createAdmin(txtEmail.getText(),txtFirstName.getText(), txtName.getText());
    }

    @FXML
    private void showLectors(ActionEvent event) {
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
        else{
            txtEmail.clear();
            txtFirstName.clear();
            txtName.clear();
            adminCheckBox.setSelected(false);
        }
            
    }

}
