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
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Append
 */
public final class MainController{
    private final Stage primaryStage;
    private DomainController domainController;
    private AddLearningUtilityController addLearningUtilityController;
    
    @FXML
    public BorderPane mainView;

    MainController(Stage primaryStage) 
    {
        this.primaryStage  = primaryStage;
        this.primaryStage.setTitle("Didachtische Leermiddelen");
        initMain();  
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initMain()
    {
        try
        {
            domainController = new DomainController();
            createTreeNavigation();
            loadInitialScreen();
            Scene scene = new Scene(mainView);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) 
            {
                System.out.println("Exception: " + e.getLocalizedMessage());
            }
    }

    private void createTreeNavigation() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartUp.class.getResource("Main.fxml"));
        mainView = (BorderPane) loader.load();
        TreeItem<String> treeRoot = new TreeItem<> ("Leermiddelen");
        
        TreeItem<String> branchReservations = new TreeItem<>("Reservaties");
        TreeItem<String> leafReservationsList = new TreeItem<>("Lijst");
        branchReservations.getChildren().addAll(leafReservationsList);

        TreeItem<String> branchCatalog = new TreeItem<>("Catalogus");
        TreeItem<String> leafCatalogList = new TreeItem<>("Lijst");
        TreeItem<String> leafCatalogAdd = new TreeItem<>("Toevoegen");
        branchCatalog.getChildren().addAll(leafCatalogList, leafCatalogAdd);
        branchCatalog.setExpanded(true);
        
        treeRoot.getChildren().addAll(branchCatalog, branchReservations);
        treeRoot.setExpanded(true);
        
        TreeView<String> treeView = new TreeView<>(treeRoot);
        treeView.getSelectionModel().select(leafCatalogAdd);
        StackPane root = new StackPane();
        root.getChildren().add(treeView);
        mainView.setLeft(root);}

    private void loadInitialScreen() 
    {
        try {
            addLearningUtilityController = new AddLearningUtilityController(domainController);
            mainView.setCenter(addLearningUtilityController.view);
        } catch (IOException e) 
            {
                System.out.println("Error:loadInitialScreen -  exception: " + e.getMessage());
            }    
    }

}
