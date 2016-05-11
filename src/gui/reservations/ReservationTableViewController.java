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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class ReservationTableViewController extends AnchorPane implements IObserver {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableView<Reservation> tableView;
    private DomainController domainController;

    public ReservationTableViewController(DomainController domainController){
        this.domainController = domainController;
        domainController.addObserver(this);
        initLoader();
        
        TableColumn<Reservation, String> colLearningUtility = new TableColumn<>("Leermiddel");
        colLearningUtility.setCellValueFactory(new PropertyValueFactory<>("learningUtilityId"));
        colLearningUtility.setMinWidth(300);
        
        TableColumn<Reservation, Date> colDate = new TableColumn<>("Afhaaldatum");
        colDate.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
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
    
}
