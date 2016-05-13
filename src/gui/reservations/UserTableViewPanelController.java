/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservations;

import domain.DomainController;
import domain.interfaces.IObserver;
import domain.users.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class UserTableViewPanelController extends GridPane implements IObserver {

    private DomainController domainController;
    @FXML
    private TableView<User> tableView;
    
    public UserTableViewPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        
        TableColumn<User, String> colLastName = new TableColumn<>("Familienaam");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colLastName.setMinWidth(100);
        
        TableColumn<User, String> colFirstName = new TableColumn<>("Voornaam");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colFirstName.setMinWidth(100);
        
        TableColumn<User, String> colEmailAddress = new TableColumn<>("Emailadres");
        colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        colEmailAddress.setMinWidth(400);
       
        tableView.getColumns().addAll(colLastName, colFirstName, colEmailAddress);
        
    }
    
    public void fillList(){
        tableView.setItems(domainController.getUsersWithReservations());
       
    }

    protected void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserTableViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public TableView<User> getTableView(){
        return this.tableView;
    }
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void selectUser(MouseEvent event) {
        domainController.setCurrentUser(tableView.getSelectionModel().getSelectedItem());
    }   
       
    
}
