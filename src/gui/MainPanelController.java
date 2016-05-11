/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.learningUtilities.LearningUtilityImportPanelController;
import gui.learningUtilities.LearningUtilityEditPanelController;
import gui.learningUtilities.LearningUtilityCreationPanelController;
import domain.DomainController;
import gui.reservations.ReservationMainPanelController;
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
    private static String BRANCH_LEARNINGUTILIES = "Didactische Leermiddelen";
    private static String BRANCH_RESERVATIONS = "Reservaties";
    private static String BRANCH_USERS = "Gebruikers";
    private static String LEAF_MANAGERS = "Beheerders";
    private static String LEAF_SHOW = "Weergeven";
    private static String LEAF_ADD = "Toevoegen";
    private static String LEAF_EDIT = "Aanpassen";
    private static String LEAF_IMPORT = "Importeren";


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
        TreeItem<String> rootLearningUtilities = new TreeItem<>(BRANCH_LEARNINGUTILIES);
        TreeItem<String> branchAddLearningUtilities = new TreeItem<>(LEAF_ADD);
        children.add(branchAddLearningUtilities);
        TreeItem<String> branchEditLearningUtilities = new TreeItem<>(LEAF_EDIT);
        children.add(branchEditLearningUtilities);
        TreeItem<String> branchMassImport = new TreeItem<>(LEAF_IMPORT);
        children.add(branchMassImport);
        rootLearningUtilities.getChildren().addAll(children);
        rootChildren.add(rootLearningUtilities);
        
        //populate treeview for Reservation operations
        children = new ArrayList<>();
        TreeItem<String> rootReservations = new TreeItem<>(BRANCH_RESERVATIONS);
        TreeItem<String> branchEditReservation = new TreeItem<>(LEAF_EDIT);
        children.add(branchEditReservation);
        TreeItem<String> branchViewReservation = new TreeItem<>(LEAF_SHOW);
        children.add(branchViewReservation);
        rootReservations.getChildren().addAll(children);
        rootChildren.add(rootReservations);
        
        //populate treeview for manager operations
        children = new ArrayList<>();
        TreeItem<String> rootUsers = new TreeItem<>(BRANCH_USERS);
        TreeItem<String> branchManagers = new TreeItem<>(LEAF_MANAGERS);
        children.add(branchManagers);
        rootUsers.getChildren().addAll(children);
        rootChildren.add(rootUsers);
        
        //adding rootChildren to root
        root.getChildren().addAll(rootChildren);        
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
        treeView.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> 
                setRightScreen(newValue.getParent().getValue(),newValue.getValue()));
        
    }
    
    private void setInitialScene(){

    }

    @FXML
    private void logout(ActionEvent event) {
    }

    @FXML
    private void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void updateTreeview(ActionEvent event) 
    {
    }

    @FXML
    private void showAbout(ActionEvent event) {
    }

    private void setRightScreen(String branch, String leaf) {

        if(branch == BRANCH_LEARNINGUTILIES && leaf == LEAF_ADD)
        {
        LearningUtilityCreationPanelController creationPanel = new LearningUtilityCreationPanelController(domainController);
        this.setCenter(creationPanel);
        }
        
        if(branch == BRANCH_LEARNINGUTILIES && leaf == LEAF_IMPORT)
        {
        LearningUtilityImportPanelController importPanel = new LearningUtilityImportPanelController(domainController);
        this.setCenter(importPanel);
        }    
         if(branch == BRANCH_LEARNINGUTILIES && leaf == LEAF_EDIT)
        {
        LearningUtilityEditPanelController editPanel = new LearningUtilityEditPanelController(domainController);
        this.setCenter(editPanel);
        }    
        
        if(branch.equals(BRANCH_RESERVATIONS)){
            if(leaf.equals(LEAF_EDIT)){
                this.setCenter(new ReservationMainPanelController(domainController));
            }
        }
        
    }
    
}
