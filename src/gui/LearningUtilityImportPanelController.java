/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Append
 */
public class LearningUtilityImportPanelController extends GridPane {

    @FXML
    private Label lblInfo;
    @FXML
    private TableView<?> tableView;
    private DomainController domainController;
    

public LearningUtilityImportPanelController(DomainController domainController)
{
        this.domainController = domainController;
        initLoader();
        lblInfo.setText("test");
} 
    
private void initLoader() throws RuntimeException 
{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LearningUtilityImportPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }  
    
}
