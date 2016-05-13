/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.users;

import domain.DomainController;
import domain.interfaces.IObserver;
import domain.users.User;
import gui.reservations.UserTableViewPanelController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Maxim
 */
public class UserConfigurationMainPanelController extends GridPane implements IObserver {
    
  

    @FXML
    private TableView<User> tableView;

    private DomainController domainController;
    private UserConfigurationPanelController userConfigurationPanelController;
    private UserTablePanelController userTablePanelController;

    /**
     * Initializes the controller class.
     */
  
     public UserConfigurationMainPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        initGui();
        domainController.addObserverToCatalog(this, User.class);
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserConfigurationMainPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch(IOException ex){
            throw new RuntimeException(ex);
        }
        
       
    }

    private void initGui(){
        
        userConfigurationPanelController = new UserConfigurationPanelController(domainController);
        userTablePanelController = new UserTablePanelController(domainController);
        userTablePanelController.fillList();
        this.add(userConfigurationPanelController, 0, 1);
        this.add(userTablePanelController,0,0);
        
            
        }
    @Override
    public void update() {
        
    }
}
  
    
