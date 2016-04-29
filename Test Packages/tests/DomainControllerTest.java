/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import persistence.IGenericDao;

/**
 *
 * @author Maxim
 */
public class DomainControllerTest {
    
 @Mock
 private IGenericDao DoaMock;
 
  @Before
          public void setup(){
  MockitoAnnotations.initMocks(this);
          }
       

    }
    

