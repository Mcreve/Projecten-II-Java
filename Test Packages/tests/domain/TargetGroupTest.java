/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.domain;




import domain.learningUtility.TargetGroup;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim
 */
public class TargetGroupTest {
    

    @Test
     public void LocationDefaultConstructorCreatesALocation()
        {

           TargetGroup targetGroup = new TargetGroup();

            assertNotNull(targetGroup);
            assertTrue(targetGroup instanceof TargetGroup);

        }
    
     @Test
      public void LocationParameterConstructorCreatesALocation()
        {

            String name = "Test";
         
            TargetGroup targetGroup = new TargetGroup(1, name);
          

  
            assertEquals(name, targetGroup.getName());

        }
}