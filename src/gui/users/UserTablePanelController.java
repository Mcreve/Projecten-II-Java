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
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class UserTablePanelController extends UserTableViewPanelController implements IObserver {

    private DomainController domainController;

    public UserTablePanelController(DomainController domainController) {
        super(domainController);
        this.domainController = domainController;
        domainController.addObserver(this);
        domainController.addObserverToCatalog(this, User.class);
       // TableColumn<User, String> colEmailAddress = new TableColumn<>("Admin");
       // colEmailAddress.setCellValueFactory((CellDataFeatures<User, String> p) -> p.getValue().getClass().getName()
       // );
 
       // colEmailAddress.setMinWidth(400);
       
    }

    /**
     *
     */
    @Override
    public void fillList() {
        getTableView().setItems(domainController.getAdmins());
    }
    
    @Override
    protected void initLoader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/reservations/UserTableViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void selectActiveUser(MouseEvent event) {

        domainController.setCurrentUser(getTableView().getSelectionModel().getSelectedItem());
        

    }

    @Override
    public void update() {
        fillList();
        getTableView().refresh();
    }
}
