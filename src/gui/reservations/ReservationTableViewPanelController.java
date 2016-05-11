/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservations;

import domain.DomainController;
import domain.interfaces.IObserver;
import domain.learningUtility.LearningUtility;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class ReservationTableViewPanelController extends AnchorPane implements IObserver {

    @FXML
    private TableView<?> tblReservations;
    private DomainController domainController;
    
    public ReservationTableViewPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        domainController.addObserverToCatalog(this, LearningUtility.class);
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationTableViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
