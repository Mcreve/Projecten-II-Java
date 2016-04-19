/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.LearningUtility;
import domain.Location;
import java.io.IOException;
import java.math.BigDecimal;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.TypedQuery;
import persistence.Connection;
import persistence.LearningUtilityRepository;

/**
 *
 * @author Ward Vanlerberghe
 */
public class StartUp extends Application {
    private Stage primaryStage;
  //  private BorderPane main;
    private MainController mainViewController;
    
    
    @Override
    public void start(Stage primaryStage) 
    {
        mainViewController = new MainController(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
}
