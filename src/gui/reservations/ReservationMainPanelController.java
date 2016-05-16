/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservations;

import domain.DomainController;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class ReservationMainPanelController extends GridPane {
    
    private DomainController domainController;
    private UserTableViewPanelController userTableViewPanelController;
    @FXML
    private DatePicker datePicker;
    public ReservationMainPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        userTableViewPanelController = new UserTableViewPanelController(domainController);
        userTableViewPanelController.fillList();
        this.add(userTableViewPanelController, 0, 1, 1, 2);
        ReservationTableViewController reservationTableViewController =  new ReservationTableViewController(domainController);
        ReservationEditPanelController reservationEditPanelController = new ReservationEditPanelController(domainController,reservationTableViewController);
        this.add(reservationTableViewController, 1, 1);
        this.add(reservationEditPanelController , 1, 2);
        reservationTableViewController.setEditPanel(reservationEditPanelController);

        setNextPickupDate();
    }

    private void setNextPickupDate() {
        int daysTillNextPickupMoment = 0;
        if(LocalDate.now().getDayOfWeek().getValue() != 1)
            daysTillNextPickupMoment = 8 - LocalDate.now().getDayOfWeek().getValue();
        datePicker.setValue(LocalDate.now().plusDays(daysTillNextPickupMoment));
        domainController.setCurrentDate(datePicker.getValue());
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationMainPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        datePicker.setOnAction(event -> {
            domainController.setCurrentDate(datePicker.getValue());
        });
    }
    
}
