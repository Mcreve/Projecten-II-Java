/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.DomainController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class MainPanelController extends BorderPane {

    @FXML
    private MenuItem menuLogout;
    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuLearningUtilities;
    @FXML
    private MenuItem menuReservations;
    @FXML
    private MenuItem menuManagers;
    @FXML
    private MenuItem menuAbout;
    @FXML
    private TreeView<String> treeView;
    
    private DomainController domainController;
    
    public MainPanelController(DomainController domainController){
        
        this.domainController = domainController;
        initLoader();
        populateTreeView();
        setInitialScene();
        
    }

    private void initLoader() throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void populateTreeView(){
        
        //set root
        TreeItem<String> root = new TreeItem<>("root");
        Collection<TreeItem<String>> rootChildren = new ArrayList<>();
        Collection<TreeItem<String>> children = new ArrayList<>();
        
        //populate treeview for LearningUtility operations
        TreeItem<String> rootLearningUtilities = new TreeItem<>("Didactische Leermiddelen");
        TreeItem<String> branchAddLearningUtilities = new TreeItem<>("Toevoegen");
        children.add(branchAddLearningUtilities);
        TreeItem<String> branchEditLearningUtilities = new TreeItem<>("Aanpassen");
        children.add(branchEditLearningUtilities);
        rootLearningUtilities.getChildren().addAll(children);
        rootChildren.add(rootLearningUtilities);
        
        //populate treeview for Reservation operations
        children = new ArrayList<>();
        TreeItem<String> rootReservations = new TreeItem<>("Reservaties");
        TreeItem<String> branchEditReservation = new TreeItem<>("Aanpassen");
        children.add(branchEditReservation);
        TreeItem<String> branchViewReservation = new TreeItem<>("Weergeven");
        children.add(branchViewReservation);
        rootReservations.getChildren().addAll(children);
        rootChildren.add(rootReservations);
        
        //populate treeview for manager operations
        children = new ArrayList<>();
        TreeItem<String> rootUsers = new TreeItem<>("Gebruikers");
        TreeItem<String> branchManagers = new TreeItem<>("Beheerders");
        children.add(branchManagers);
        rootUsers.getChildren().addAll(children);
        rootChildren.add(rootUsers);
        
        //adding rootChildren to root
        root.getChildren().addAll(rootChildren);        
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
    }
    
    private void setInitialScene(){
        LearningUtilityCreationPanelController creationPanel = new LearningUtilityCreationPanelController(domainController);
        this.setCenter(creationPanel);
    }

    @FXML
    private void logout(ActionEvent event) {
    }

    @FXML
    private void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void updateTreeview(ActionEvent event) {
    }

    @FXML
    private void showAbout(ActionEvent event) {
    }
    
}
