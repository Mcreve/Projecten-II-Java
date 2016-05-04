/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import domain.*;
import domain.catalogs.*;
import domain.learningUtility.*;
import domain.users.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import persistence.IGenericDao;

/**
 *
 * @author Maxim
 */
public class DomainControllerTest {

    private DomainController domainController;
    private AdvancedCatalog<LearningUtility> learningUtilityCatalog;
    private AdvancedCatalog<Company> companyCatalog;
    private AdvancedCatalog<FieldOfStudy> fieldOfStudyCatalog;
    private AdvancedCatalog<TargetGroup> targetGroupCatalog;
    private AdvancedCatalog<Location> locationCatalog;
    private AdvancedCatalog<User> userCatalog;
    private List<String> testTargetGroups;
    private List<String> testFieldsOfStudy;

   
    @Mock
    private IGenericDao daoMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        domainController = new DomainController("string");
        userCatalog = new AdvancedCatalog(daoMock, User.class);
        locationCatalog = new AdvancedCatalog(daoMock, Location.class);
        companyCatalog = new AdvancedCatalog<>(daoMock, Company.class);
        fieldOfStudyCatalog = new AdvancedCatalog<>(daoMock, FieldOfStudy.class);
        targetGroupCatalog = new AdvancedCatalog<>(daoMock, TargetGroup.class);
        learningUtilityCatalog = new AdvancedCatalog<>(daoMock, LearningUtility.class);
        domainController.setCompanies(companyCatalog);
        domainController.setFieldsOfStudy(fieldOfStudyCatalog);
        domainController.setLocations(locationCatalog);
        domainController.setTargetGroups(targetGroupCatalog);
        domainController.setUtilities(learningUtilityCatalog);
        domainController.setUsers(userCatalog);
        testTargetGroups = new ArrayList<>();
        testTargetGroups.add("TargetGroup1");
        testTargetGroups.add("TargetGroup2");
        testFieldsOfStudy = new ArrayList<>();
        testFieldsOfStudy.add("Fos1");
        testFieldsOfStudy.add("Fos2");
         LearningUtility testUtility = new LearningUtility(2, "name", "description", BigDecimal.ONE, true, 2, 0);
       learningUtilityCatalog.addEntity(testUtility);
            Mockito.when(
            daoMock.findBy(testUtility.getId())).thenReturn(testUtility);
  
    }

    // Adding Single Item
    @Test
    public void addLearningUtilityTest(){


        domainController.addLearningUtility("testName", "description", BigDecimal.ONE, true, "5", "", 3, 0, "companyName", "locationName", testTargetGroups, testFieldsOfStudy);
        LearningUtility LearningUtility1 = learningUtilityCatalog.getByName("testName");
        
       // Mockito.verify(daoMock).findBy(LearningUtility1.getId());

        assertEquals("description", LearningUtility1.getDescription());

 }
    
    @Test (expected = IllegalArgumentException.class)
    public void addLearningUtilityWithNoAmountInStock(){
        
           
        domainController.addLearningUtility("testName", null , null, true, null, null, 0, 0, null, null, null, null);
    }
    
     @Test (expected = IllegalArgumentException.class)
    public void addLearningUtilityWithExistingName(){
        
           
        domainController.addLearningUtility("name", null , null, true, null, null, 2, 0, null, null, null, null);
    }
    
     @Test (expected = IllegalArgumentException.class)
    public void addLearningUtilityWithNameNull(){
        
           
        domainController.addLearningUtility(null, null , null, true, null, null, 2, 0, null, null, null, null);
    }
    
    // Adding in Bulk
    
    @Test (expected = IllegalArgumentException.class)
    public void AddFileWithWrongExtension() throws IOException{
        
        domainController.readCsvFile("test.exe");
    }
    @Test
    public void readFile() throws IOException{
       
        ObservableList<LearningUtility> TestList = domainController.readCsvFile("leermiddelen.csv");
        
        assertEquals(10,TestList.get(0).getAmountInCatalog());
        assertEquals(20,TestList.get(1).getAmountInCatalog());
     
        
    }
    @Test (expected = IllegalArgumentException.class)
    public void readFileDuplicateName() throws IOException{
       
        domainController.readCsvFile("leermiddelenDuplicateName.csv");
    
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void readFileNegativeAmountAvailable() throws IOException{
       
         domainController.readCsvFile("leermiddelenNegativeAmountAvailable.csv");
    
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void readFileNegativeAmountUnavailable() throws IOException{
       
         domainController.readCsvFile("leermiddelenNegativeAmountUnavailable.csv");
    
    }
    
     @Test (expected = IllegalArgumentException.class)
    public void readFileNegativePrice() throws IOException{
       
        domainController.readCsvFile("leermiddelenNegativePrice.csv");
    
    }
}
    

