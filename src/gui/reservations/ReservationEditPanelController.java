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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class ReservationEditPanelController extends GridPane implements IObserver {

    @FXML
    private DatePicker datePickerPickupDate;
    @FXML
    private DatePicker datePickerReturnDate;
    @FXML
    private TextField txtLoanedOut;
    @FXML
    private TextField txtReturned;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    private DomainController domainController;
    
    public ReservationEditPanelController(DomainController domainController){
        this.domainController = domainController;
        domainController.addObserver(this);
        initLoader();
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationEditPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    

    @FXML
    private void cancel(ActionEvent event) {
        update();
    }

    @FXML
    private void edit(ActionEvent event) {
        //ToDo Add returndate when database is changed
        Date pickUpDate = Date.from(datePickerPickupDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        domainController.editReservation(null, pickUpDate,Integer.parseInt(txtLoanedOut.getText()));
    }

    @Override
    public void update() {
        Reservation r = domainController.getCurrentReservation();
        
        LocalDate pickUpDate = r.getDateWanted().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePickerPickupDate.setValue(pickUpDate);
        
        Calendar c = Calendar.getInstance();
        c.setTime(r.getDateWanted());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int daysTillFriday = Calendar.FRIDAY - dayOfWeek;
        
        LocalDate returnDate = pickUpDate.plusDays(daysTillFriday);
        
        datePickerReturnDate.setValue(returnDate);
        txtLoanedOut.setText(Integer.toString(r.getAmount()));
        //Todo update txtAmountReturned when implemented in database and application
    }
    
}
