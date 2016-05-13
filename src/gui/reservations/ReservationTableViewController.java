/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservations;

import domain.DomainController;
import domain.interfaces.IObserver;
import domain.learningUtility.Reservation;
import java.io.IOException;
import java.util.Date;
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
public class ReservationTableViewController extends GridPane implements IObserver {

    @FXML
    private TableView<Reservation> tableView;
    private DomainController domainController;

    public ReservationTableViewController(DomainController domainController){
        this.domainController = domainController;
        domainController.addObserver(this);
        domainController.setCurrentReservation(null);
        initLoader();
        
        TableColumn<Reservation, String> colLearningUtility = new TableColumn<>("Leermiddel");
        colLearningUtility.setCellValueFactory(new PropertyValueFactory<>("learningUtilityId"));
        colLearningUtility.setMinWidth(300);
        
        TableColumn<Reservation, Date> colDate = new TableColumn<>("Afhaaldatum");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateWanted"));
        colDate.setMinWidth(50);
        
        tableView.getColumns().addAll(colLearningUtility, colDate);
    }   

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationTableView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }   

    @Override
    public void update() {
        if(domainController.userIsSet())
            tableView.setItems(domainController.getReservationsFromUser());
    }

    @FXML
    private void selectReservation(MouseEvent event) {
        domainController.setCurrentReservation(tableView.getSelectionModel().getSelectedItem());
    }
    
}
