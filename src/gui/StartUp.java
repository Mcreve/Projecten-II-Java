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
    private BorderPane main;
    
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage  = primaryStage;
        this.primaryStage.setTitle("Didachtische Leermiddelen");
        
        initMain();
        showAddLearningUtilityView();

             
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                
                LearningUtilityRepository repo = new LearningUtilityRepository();
                repo.findAll().forEach((utility) -> System.out.println(utility.getName()));
                System.out.println(repo.findBy(1).getName());
                
                LearningUtility newUtil = new LearningUtility();
                newUtil.setAmountInCatalog(5);
                newUtil.setAmountUnavailable(0);
                newUtil.setName("Testobject");
                newUtil.setDescription("Nieuw leermiddel om te testen");
                newUtil.setPrice(BigDecimal.valueOf(0));
                
                TypedQuery<Location> query = Connection.entityManager().createNamedQuery("Location.findById", Location.class);
                query.setParameter("id", 1);
                newUtil.setLocationId(query.getSingleResult());
                
                repo.add(newUtil);
            }
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void initMain(){
    try
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartUp.class.getResource("Main.fxml"));
      
        main = (BorderPane) loader.load();
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
        main.setLeft(root);
        // Show the scene containing the root layout.
        Scene scene = new Scene(main);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) 
    {
        System.out.println("Exception: " + e.getLocalizedMessage());
    }
    }
    
    public void showAddLearningUtilityView() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartUp.class.getResource("AddLearningUtility.fxml"));
            AnchorPane AddLearningUtilityView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            main.setCenter(AddLearningUtilityView);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("exception: " + e.getMessage());
        }
    } public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}
