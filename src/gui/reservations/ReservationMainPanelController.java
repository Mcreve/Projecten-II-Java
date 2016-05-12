/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservations;

import domain.DomainController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class ReservationMainPanelController extends GridPane {
    
    private DomainController domainController;

    public ReservationMainPanelController(DomainController domainController){
        this.domainController = domainController;
        initLoader();
        this.add(new UserTableViewPanelController(domainController), 0, 0, 1, 2);
        this.add(new ReservationTableViewController(domainController), 1, 0);
        this.add(new ReservationEditPanelController(domainController), 1, 1);
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
    }
    
}
