/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.DomainController;
import gui.LoginPanelController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ward Vanlerberghe
 */
public class StartUp extends Application {

    private DomainController domainController;
    
   @Override
    public void start(Stage primaryStage) {
        
        this.domainController = new DomainController();
        
        Scene scene = new Scene(new LoginPanelController(domainController));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Didactische Leermiddelen - Beheer");
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void stop(){
        domainController.closeConnection();
        System.out.println("DB connection closed");
    }
}
