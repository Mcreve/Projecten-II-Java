/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.domain;


import domain.learningUtility.FieldOfStudy;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim
 */
public class FieldOfStudyTest {
    

    @Test
     public void FieldOfStudyDefaultConstructorCreatesAFieldOfStudy()
        {

           FieldOfStudy fieldOfStudy = new FieldOfStudy();

            assertNotNull(fieldOfStudy);
            assertTrue(fieldOfStudy instanceof FieldOfStudy);

        }
    
     @Test
      public void FieldOfStudyParameterConstructorCreatesAFieldOfStudy()
        {

            String fieldOfStudyName = "Test";
         
            FieldOfStudy fieldOfStudy = new FieldOfStudy(1,fieldOfStudyName);
          

  
            assertEquals(fieldOfStudyName, fieldOfStudy.getName());

        }
}