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
import gui.users.UserConfigurationMainPanelController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ward Vanlerberghe
 */
public class MainPanelController extends BorderPane {
    private final String BRANCH_LEARNINGUTILIES = "Didactische Leermiddelen";
    private final String BRANCH_RESERVATIONS = "Reservaties";
    private final String BRANCH_USERS = "Gebruikers";
    private final String LEAF_MANAGERS = "Beheerders";
    private final String LEAF_SHOW = "Weergeven";
    private final String LEAF_ADD = "Toevoegen";
    private final String LEAF_EDIT = "Aanpassen";
    private final String LEAF_IMPORT = "Importeren";
    private final String LEAF_OUTGOING = "Beheren";
    private final String LEAF_INCOMING = "Binnenkomend";


    @FXML
    private MenuItem menuLogout;
    @FXML
    private MenuItem menuClose;
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
        TreeItem<String> branchEditReservation = new TreeItem<>(LEAF_OUTGOING);
        children.add(branchEditReservation);
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
        Stage stage = (Stage) treeView.getScene().getWindow();
        stage.setScene(new Scene(new LoginPanelController(domainController)));
        stage.show();
    }

    @FXML
    private void close(ActionEvent event) {
        Platform.exit();
    }


    private void setRightScreen(String branch, String leaf) {

        if(branch.equals(BRANCH_LEARNINGUTILIES) && leaf.equals(LEAF_ADD))
        {
        LearningUtilityCreationPanelController creationPanel = new LearningUtilityCreationPanelController(domainController);
        this.setCenter(creationPanel);
        }
        
        if(branch.equals(BRANCH_LEARNINGUTILIES) && leaf.equals(LEAF_IMPORT))
        {
        LearningUtilityImportPanelController importPanel = new LearningUtilityImportPanelController(domainController);
        this.setCenter(importPanel);
        }    
         if(branch.equals(BRANCH_LEARNINGUTILIES) && leaf.equals(LEAF_EDIT))
        {
        LearningUtilityEditPanelController editPanel = new LearningUtilityEditPanelController(domainController);
        this.setCenter(editPanel);
        }    
        if(branch.equals(BRANCH_USERS) && leaf.equals(LEAF_MANAGERS))
        {
            UserConfigurationMainPanelController userConfigurationMainPanelController  = new UserConfigurationMainPanelController(domainController);
            this.setCenter(userConfigurationMainPanelController);
        }    
        
        
        if(branch.equals(BRANCH_RESERVATIONS)){
            if(leaf.equals(LEAF_OUTGOING)){
                this.setCenter(new ReservationMainPanelController(domainController));
            }
        }
        
    }
    
}
