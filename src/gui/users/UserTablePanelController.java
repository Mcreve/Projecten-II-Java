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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class UserTablePanelController extends UserTableViewPanelController implements IObserver{

    private DomainController domainController;
   

   
    
    public UserTablePanelController(DomainController domainController){
        super(domainController);
        this.domainController = domainController;
        this.domainController.setCurrentUserAdminPanel(null);
        domainController.addObserver(this);
        domainController.addObserverToCatalog(this, User.class);
        
       
    }
    
    @Override
    protected void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("UserTableViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     */
    @Override
   public void fillList(){
         super.getTableView().setItems(domainController.getAdmins());
    }
  
    @FXML
    private void selectActiveUser(MouseEvent event) {
        
        domainController.setCurrentUserAdminPanel(super.getTableView().getSelectionModel().getSelectedItem());
        
        
    }   
    @Override
    public void update() {
        fillList();
        super.getTableView().refresh();
    }
}

   

    
       

