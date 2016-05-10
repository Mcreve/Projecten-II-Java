/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import domain.DomainController;
import domain.interfaces.ICatalog;
import domain.learningUtility.Company;
import domain.learningUtility.FieldOfStudy;
import domain.learningUtility.LearningUtility;
import domain.learningUtility.Location;
import domain.learningUtility.TargetGroup;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Ward Vanlerberghe
 */
public class DomainControllerTests {
    
    public DomainControllerTests() {
    }
    
    private DomainController domainController;
    private LearningUtility learningUtility1;
    private LearningUtility learningUtility3;
    private LearningUtility learningUtility2;
    private List<LearningUtility> learningUtilityList;
    private ObservableList<LearningUtility> observableLearningUtilityList;
    private FilteredList<LearningUtility> filteredLearningUtilityList;
    private final String TESTSTRING = "CHANGED";
    private final String TESTSTRING2 = "ALSO CHANGED";
    
    @Mock
    private ICatalog<LearningUtility> learningUtilityCatalogMock;
    @Mock
    private ICatalog<Company> companyCatalogMock;
    @Mock
    private ICatalog<FieldOfStudy> fieldOfStudyCatalogMock;
    @Mock
    private ICatalog<TargetGroup> targetGroupCatalogMock;
    @Mock
    private ICatalog<Location> locationCatalogMock;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);       
        learningUtilityList = new ArrayList<>();
       
        learningUtility1 = new LearningUtility(0,"Wereldbol","Beschrijving van een wereldbol",BigDecimal.TEN,true,10,1,"Artkl.001");
        learningUtility1.setCompanyId(new Company());
        learningUtility1.setFieldOfStudyList(new ArrayList<>());
        learningUtility1.setLocationId(new Location());
        learningUtility1.setPicture("");
        learningUtility1.setTargetGroupList(new ArrayList<>());
        learningUtilityList.add(learningUtility1);
        
        learningUtility2 = new LearningUtility(1,"Gradenboog", "Een gradenboog om hoeken te meten",BigDecimal.ONE,true,100,0,"Artkl.002");
        learningUtilityList.add(learningUtility2);
        
        learningUtility3 = new LearningUtility(2, "test", "Een microscoop", BigDecimal.ONE, true, 5, 0,"Artkl.003");
        learningUtilityList.add(learningUtility3); 
        
       
        observableLearningUtilityList = FXCollections.observableList(learningUtilityList);
        filteredLearningUtilityList = new FilteredList(observableLearningUtilityList);
        
        Mockito.when(learningUtilityCatalogMock.getType()).thenReturn(LearningUtility.class);
        Mockito.when(learningUtilityCatalogMock.getEntities()).thenReturn(filteredLearningUtilityList);
        domainController = new DomainController(learningUtilityCatalogMock);
        
        Mockito.when(companyCatalogMock.getType()).thenReturn(Company.class);
        domainController.setCatalog(companyCatalogMock);
        
        Mockito.when(fieldOfStudyCatalogMock.getType()).thenReturn(FieldOfStudy.class);
        domainController.setCatalog(fieldOfStudyCatalogMock);
        
        Mockito.when(targetGroupCatalogMock.getType()).thenReturn(TargetGroup.class);
        domainController.setCatalog(targetGroupCatalogMock);
        
        Mockito.when(locationCatalogMock.getType()).thenReturn(Location.class);
        domainController.setCatalog(locationCatalogMock);
        
    }
    
    
    
    // Adding learningUtility
    
     @Test
    public void addLearningUtilityTest(){
        
        
        Mockito.when(companyCatalogMock.getEntity(TESTSTRING)).thenReturn(new Company(0,TESTSTRING, TESTSTRING,TESTSTRING,TESTSTRING));
        Mockito.when(fieldOfStudyCatalogMock.getEntity(TESTSTRING)).thenReturn(new FieldOfStudy(0,TESTSTRING));
        Mockito.when(fieldOfStudyCatalogMock.getEntity(TESTSTRING2)).thenReturn(new FieldOfStudy(1, TESTSTRING2));
        Mockito.when(targetGroupCatalogMock.getEntity(TESTSTRING)).thenReturn(new TargetGroup(0,TESTSTRING));
        Mockito.when(targetGroupCatalogMock.getEntity(TESTSTRING2)).thenReturn(new TargetGroup(1, TESTSTRING2));
        Mockito.when(locationCatalogMock.getEntity(TESTSTRING2)).thenReturn(new Location(0,TESTSTRING2));
        List<String> list = new ArrayList<>();
        list.add(TESTSTRING);
        list.add(TESTSTRING2);
       
       domainController.addLearningUtility( TESTSTRING, 
                TESTSTRING2, BigDecimal.ONE, 
                false, TESTSTRING, 
                TESTSTRING2, Integer.MAX_VALUE, 
                Integer.MAX_VALUE, TESTSTRING, 
                TESTSTRING2, 
                list, 
                list);
       
        List<TargetGroup> targetGroupsList = new ArrayList<>();
        targetGroupsList.add(new TargetGroup(0,TESTSTRING));
        targetGroupsList.add(new TargetGroup(1,TESTSTRING2));
        
        List<FieldOfStudy> fieldsOfStudyList = new ArrayList<>();
        fieldsOfStudyList.add(new FieldOfStudy(0,TESTSTRING));
        fieldsOfStudyList.add(new FieldOfStudy(1,TESTSTRING2));
        
       
       LearningUtility learningUtility = new LearningUtility();
       learningUtility.setName(TESTSTRING);
       learningUtility.setDescription(TESTSTRING2);
       learningUtility.setPrice(BigDecimal.ONE);
       learningUtility.setLoanable(false);
       learningUtility.setArticleNumber(TESTSTRING);
       learningUtility.setPicture(TESTSTRING2);
       learningUtility.setAmountInCatalog(Integer.MAX_VALUE);
       learningUtility.setAmountUnavailable(Integer.MAX_VALUE);
       learningUtility.setCompanyId(new Company(0,TESTSTRING, TESTSTRING,TESTSTRING,TESTSTRING));
       learningUtility.setLocationId(new Location(0,TESTSTRING2));
       learningUtility.setTargetGroupList(targetGroupsList);
       learningUtility.setFieldOfStudyList(fieldsOfStudyList);
       

      
       Mockito.verify(learningUtilityCatalogMock).addEntity(learningUtility);


 }
    
     
    @Test (expected = IllegalArgumentException.class)
    public void addLearningUtilityWithNoAmountInStock(){
        
           
        domainController.addLearningUtility("testName", null , null, true, null, null, 0, 0, null, null, null, null);
    }
    @Test (expected = IllegalArgumentException.class)
     public void addLearningUtilityWithExistingName(){
        
           
        domainController.addLearningUtility("Wereldbol", null , null, true, null, null, 2, 0, null, null, null, null);
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
    // Modify learningUtility tests
    
    @Test (expected = IllegalArgumentException.class)
    public void changingNameToExcistingNameThrowsException(){
        Mockito.when(companyCatalogMock.getEntity(null)).thenReturn(new Company());
        Mockito.when(fieldOfStudyCatalogMock.getEntity(null)).thenReturn(new FieldOfStudy());
        Mockito.when(targetGroupCatalogMock.getEntity(null)).thenReturn(new TargetGroup());
        Mockito.when(locationCatalogMock.getEntity(null)).thenReturn(new Location());
        
        domainController.editLearningUtility(learningUtility1, "test", 
                learningUtility1.getDescription(), learningUtility1.getPrice(), 
                learningUtility1.getLoanable(), learningUtility1.getArticleNumber(), 
                learningUtility1.getPicture(), learningUtility1.getAmountInCatalog(), 
                learningUtility1.getAmountUnavailable(), learningUtility1.getCompanyId().getName(), 
                learningUtility1.getLocationId().getName(), 
                learningUtility1.getTargetGroupList().stream().map(targetgroup -> targetgroup.getName()).collect(Collectors.toList()), 
                learningUtility1.getFieldOfStudyList().stream().map(fieldOfStudy -> fieldOfStudy.getName()).collect(Collectors.toList()));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void changingNameToEmptyStringThrowsException(){
        Mockito.when(companyCatalogMock.getEntity(null)).thenReturn(new Company());
        Mockito.when(fieldOfStudyCatalogMock.getEntity(null)).thenReturn(new FieldOfStudy());
        Mockito.when(targetGroupCatalogMock.getEntity(null)).thenReturn(new TargetGroup());
        Mockito.when(locationCatalogMock.getEntity(null)).thenReturn(new Location());
        
        domainController.editLearningUtility(learningUtility1, "", 
                learningUtility1.getDescription(), learningUtility1.getPrice(), 
                learningUtility1.getLoanable(), learningUtility1.getArticleNumber(), 
                learningUtility1.getPicture(), learningUtility1.getAmountInCatalog(), 
                learningUtility1.getAmountUnavailable(), learningUtility1.getCompanyId().getName(), 
                learningUtility1.getLocationId().getName(), 
                learningUtility1.getTargetGroupList().stream().map(targetgroup -> targetgroup.getName()).collect(Collectors.toList()), 
                learningUtility1.getFieldOfStudyList().stream().map(fieldOfStudy -> fieldOfStudy.getName()).collect(Collectors.toList()));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void changingPriceToNegativeThrowsException(){
        Mockito.when(companyCatalogMock.getEntity(null)).thenReturn(new Company());
        Mockito.when(fieldOfStudyCatalogMock.getEntity(null)).thenReturn(new FieldOfStudy());
        Mockito.when(targetGroupCatalogMock.getEntity(null)).thenReturn(new TargetGroup());
        Mockito.when(locationCatalogMock.getEntity(null)).thenReturn(new Location());
        
        domainController.editLearningUtility(learningUtility1, learningUtility1.getName(), 
                learningUtility1.getDescription(), BigDecimal.valueOf(-0.1), 
                learningUtility1.getLoanable(), learningUtility1.getArticleNumber(), 
                learningUtility1.getPicture(), learningUtility1.getAmountInCatalog(), 
                learningUtility1.getAmountUnavailable(), learningUtility1.getCompanyId().getName(), 
                learningUtility1.getLocationId().getName(), 
                learningUtility1.getTargetGroupList().stream().map(targetgroup -> targetgroup.getName()).collect(Collectors.toList()), 
                learningUtility1.getFieldOfStudyList().stream().map(fieldOfStudy -> fieldOfStudy.getName()).collect(Collectors.toList()));
    }
    
    @Test
    public void changingAttributesAllOk(){
        Mockito.when(companyCatalogMock.getEntity(TESTSTRING)).thenReturn(new Company(0,TESTSTRING, TESTSTRING,TESTSTRING,TESTSTRING));
        Mockito.when(fieldOfStudyCatalogMock.getEntity(TESTSTRING)).thenReturn(new FieldOfStudy(0,TESTSTRING));
        Mockito.when(fieldOfStudyCatalogMock.getEntity(TESTSTRING2)).thenReturn(new FieldOfStudy(1, TESTSTRING2));
        Mockito.when(targetGroupCatalogMock.getEntity(TESTSTRING)).thenReturn(new TargetGroup(0,TESTSTRING));
        Mockito.when(targetGroupCatalogMock.getEntity(TESTSTRING2)).thenReturn(new TargetGroup(1, TESTSTRING2));
        Mockito.when(locationCatalogMock.getEntity(TESTSTRING2)).thenReturn(new Location(0,TESTSTRING2));
        List<String> list = new ArrayList<>();
        list.add(TESTSTRING);
        list.add(TESTSTRING2);
        
        domainController.editLearningUtility(learningUtility1, TESTSTRING, 
                TESTSTRING2, BigDecimal.ONE, 
                false, TESTSTRING, 
                TESTSTRING2, Integer.MAX_VALUE, 
                Integer.MAX_VALUE, TESTSTRING, 
                TESTSTRING2, 
                list, 
                list);
        
        assertEquals(learningUtility1.getName(), TESTSTRING);
        assertEquals(learningUtility1.getDescription(), TESTSTRING2);
        assertEquals(learningUtility1.getPrice(), BigDecimal.ONE);
        assertEquals(learningUtility1.getLoanable(), false);
        assertEquals(learningUtility1.getArticleNumber(), TESTSTRING);
        assertEquals(learningUtility1.getPicture(), TESTSTRING2);
        assertEquals(learningUtility1.getAmountInCatalog(), Integer.MAX_VALUE);
        assertEquals(learningUtility1.getAmountUnavailable(), Integer.MAX_VALUE);
        assertEquals(learningUtility1.getCompanyId().getName(), TESTSTRING);
        assertEquals(learningUtility1.getLocationId().getName(), TESTSTRING2);
        assertArrayEquals(learningUtility1.getFieldOfStudyList().stream().map(fieldOfStudy -> fieldOfStudy.getName()).collect(Collectors.toList()).toArray(), list.toArray());
        assertArrayEquals(learningUtility1.getTargetGroupList().stream().map(tg -> tg.getName()).collect(Collectors.toList()).toArray(), list.toArray());
        
        Mockito.verify(companyCatalogMock).getEntity(TESTSTRING);
        Mockito.verify(fieldOfStudyCatalogMock).getEntity(TESTSTRING);
        Mockito.verify(fieldOfStudyCatalogMock).getEntity(TESTSTRING2);
        Mockito.verify(targetGroupCatalogMock).getEntity(TESTSTRING);
        Mockito.verify(targetGroupCatalogMock).getEntity(TESTSTRING2);
        Mockito.verify(locationCatalogMock).getEntity(TESTSTRING2);        
    }
    
    
     // Filter tests
    
    @Test
    public void changeFilterAllCapitalsFiltersList(){          
        domainController.changeFilter("GRADENBOOG");
        
        assertEquals(domainController.getFilteredLearningUtilityList().size(),1);
    }
    
    @Test
    public void changeFilterAllLowerCaseFiltersList(){
        domainController.changeFilter("gradenboog");
        
        assertEquals(domainController.getFilteredLearningUtilityList().size(),1);
    }
    
    @Test
    public void noMatchReturnsEmptyList(){
        domainController.changeFilter("HAHA");
        
        assertEquals(domainController.getFilteredLearningUtilityList().size(),0);
    }
    
    @Test
    public void emptyStringReturnsAllLearningUtilities(){
        domainController.changeFilter("");
        
        assertEquals(domainController.getFilteredLearningUtilityList().size(), 3);
    }
    
    @After
    public void verifyMockCalls(){        
        
        Mockito.verify(learningUtilityCatalogMock).getType();
        Mockito.verify(companyCatalogMock).getType();
        Mockito.verify(fieldOfStudyCatalogMock).getType();
        Mockito.verify(targetGroupCatalogMock).getType();
        Mockito.verify(locationCatalogMock).getType();
    }
}
