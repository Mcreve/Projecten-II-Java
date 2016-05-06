/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import domain.DomainController;
import domain.interfaces.IAdvancedCatalog;
import domain.learningUtility.Company;
import domain.learningUtility.FieldOfStudy;
import domain.learningUtility.LearningUtility;
import domain.learningUtility.Location;
import domain.learningUtility.TargetGroup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Ward Vanlerberghe
 */
public class DomainControllerLearningUtilityManipulationsTests {
    
    public DomainControllerLearningUtilityManipulationsTests() {
    }
    
    private DomainController domainController;
    private LearningUtility learningUtility1;
    private LearningUtility learningUtility3;
    private LearningUtility learningUtility2;
    private List<LearningUtility> learningUtilityList;
    private final String TESTSTRING = "CHANGED";
    private final String TESTSTRING2 = "ALSO CHANGED";
    @Mock
    private IAdvancedCatalog<LearningUtility> learningUtilityCatalogMock;
    @Mock
    private IAdvancedCatalog<Company> companyCatalogMock;
    @Mock
    private IAdvancedCatalog<FieldOfStudy> fieldOfStudyCatalogMock;
    @Mock
    private IAdvancedCatalog<TargetGroup> targetGroupCatalogMock;
    @Mock
    private IAdvancedCatalog<Location> locationCatalogMock;
    
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
        
        learningUtility3 = new LearningUtility(2, TESTSTRING, "Een microscoop", BigDecimal.ONE, true, 5, 0,"Artkl.003");
        learningUtilityList.add(learningUtility3); 
        
        Mockito.when(learningUtilityCatalogMock.getType()).thenReturn(LearningUtility.class);
        Mockito.when(learningUtilityCatalogMock.getEntities()).thenReturn(learningUtilityList);
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
    
    @Test (expected = IllegalArgumentException.class)
    public void changingNameToExcistingNameThrowsException(){
        Mockito.when(companyCatalogMock.getByName(null)).thenReturn(new Company());
        Mockito.when(fieldOfStudyCatalogMock.getByName(null)).thenReturn(new FieldOfStudy());
        Mockito.when(targetGroupCatalogMock.getByName(null)).thenReturn(new TargetGroup());
        Mockito.when(locationCatalogMock.getByName(null)).thenReturn(new Location());
        
        domainController.editLearningUtility(learningUtility1, TESTSTRING, 
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
        Mockito.when(companyCatalogMock.getByName(null)).thenReturn(new Company());
        Mockito.when(fieldOfStudyCatalogMock.getByName(null)).thenReturn(new FieldOfStudy());
        Mockito.when(targetGroupCatalogMock.getByName(null)).thenReturn(new TargetGroup());
        Mockito.when(locationCatalogMock.getByName(null)).thenReturn(new Location());
        
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
        Mockito.when(companyCatalogMock.getByName(null)).thenReturn(new Company());
        Mockito.when(fieldOfStudyCatalogMock.getByName(null)).thenReturn(new FieldOfStudy());
        Mockito.when(targetGroupCatalogMock.getByName(null)).thenReturn(new TargetGroup());
        Mockito.when(locationCatalogMock.getByName(null)).thenReturn(new Location());
        
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
        Mockito.when(companyCatalogMock.getByName(TESTSTRING)).thenReturn(new Company(0,TESTSTRING, TESTSTRING,TESTSTRING,TESTSTRING));
        Mockito.when(fieldOfStudyCatalogMock.getByName(TESTSTRING)).thenReturn(new FieldOfStudy(0,TESTSTRING));
        Mockito.when(fieldOfStudyCatalogMock.getByName(TESTSTRING2)).thenReturn(new FieldOfStudy(1, TESTSTRING2));
        Mockito.when(targetGroupCatalogMock.getByName(TESTSTRING)).thenReturn(new TargetGroup(0,TESTSTRING));
        Mockito.when(targetGroupCatalogMock.getByName(TESTSTRING2)).thenReturn(new TargetGroup(1, TESTSTRING2));
        Mockito.when(locationCatalogMock.getByName(TESTSTRING2)).thenReturn(new Location(0,TESTSTRING2));
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
        
        Mockito.verify(companyCatalogMock).getByName(TESTSTRING);
        Mockito.verify(fieldOfStudyCatalogMock).getByName(TESTSTRING);
        Mockito.verify(fieldOfStudyCatalogMock).getByName(TESTSTRING2);
        Mockito.verify(targetGroupCatalogMock).getByName(TESTSTRING);
        Mockito.verify(targetGroupCatalogMock).getByName(TESTSTRING2);
        Mockito.verify(locationCatalogMock).getByName(TESTSTRING2);        
    }
    
    @After
    public void verifyMockCalls(){        
        Mockito.verify(learningUtilityCatalogMock).getEntities();
        Mockito.verify(learningUtilityCatalogMock).getType();
        Mockito.verify(companyCatalogMock).getType();
        Mockito.verify(fieldOfStudyCatalogMock).getType();
        Mockito.verify(targetGroupCatalogMock).getType();
        Mockito.verify(locationCatalogMock).getType();
    }
}
