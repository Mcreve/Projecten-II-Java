/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.domain2;



import domain.learningUtility.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim
 */
public class LocationTest {
    

    @Test
     public void LocationDefaultConstructorCreatesALocation()
        {

           Location location = new Location();

            assertNotNull(location);
            assertTrue(location instanceof Location);

        }
    
     @Test
      public void LocationParameterConstructorCreatesALocation()
        {

            String locationName = "Test";
         
            Location location = new Location(1,locationName);
          

  
            assertEquals(locationName, location.getName());

        }
}