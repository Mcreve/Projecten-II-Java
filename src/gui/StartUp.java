/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.LearningUtility;
import domain.Location;
import java.math.BigDecimal;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    @Override
    public void start(Stage primaryStage) {
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
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
