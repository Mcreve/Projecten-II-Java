/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private String fileLocation;
    

public LearningUtilityImportPanelController(DomainController domainController)
{
        this.domainController = domainController;
        initLoader();
} 
    
private void initLoader() throws RuntimeException 
{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LearningUtilityImportPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException e) 
        {
            throw new RuntimeException(e);
        }
        try
        {
            fileLocation = getFileLocation();
        }catch(FileNotFoundException ex)
        {
            lblInfo.setText(ex.getMessage());
        }catch(Exception ex)
        {
            lblInfo.setText(ex.getMessage());
        }
            setLayoutForTableView();
    }  

    private String getFileLocation() throws FileNotFoundException 
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll
        (
            new FileChooser.ExtensionFilter("CSV", "*.CSV")
        );        
        
        File file = fileChooser.showOpenDialog(null);
        if(file != null || !file.exists())
        {
            throw new FileNotFoundException("Bestand niet gevonden");
        }
        
        return file.getPath();
    }

    private void setLayoutForTableView() {
        tableView.setEditable(true);
 
        TableColumn name = new TableColumn("Naam");
        TableColumn description = new TableColumn("Omschrijving");
        TableColumn loanable = new TableColumn("Uitleenbaar");
        TableColumn articleNumber = new TableColumn("Artikel Nummer");
        TableColumn picture = new TableColumn("Foto Url");
        TableColumn Price = new TableColumn("Prijs");
        tableView.getColumns().addAll
        (
                name, description, Price,
                loanable, articleNumber, picture
        );
       //     tableView.setItems(domainController.readCsvFile(fileLocation));

     }
    
}
