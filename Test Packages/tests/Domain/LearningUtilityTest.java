/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.Domain;

import domain.learningUtility.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maxim
 */
public class LearningUtilityTest {

    private LearningUtility initialLearningUtility;
    private Location initialLocation;
    private FieldOfStudy initialFieldOfStudy0;
    private FieldOfStudy initialFieldOfStudy1;
    private List <FieldOfStudy> initialFieldOfStudyList;
    private List <TargetGroup> initialTargetGroups;
    private TargetGroup initialTargetGroup;
    private Company initialCompany;

    @Before
    public void before() {
        initialLearningUtility = new LearningUtility();
        initialLocation = new Location(1, "GELDE 1.001");
        initialFieldOfStudy0 = new FieldOfStudy(1, "Geschiedenis");
        initialFieldOfStudy1 = new FieldOfStudy(2, "Biologie");
        initialFieldOfStudyList = new ArrayList<>();
        initialFieldOfStudyList.add(initialFieldOfStudy0);
        initialFieldOfStudyList.add(initialFieldOfStudy1);
        initialTargetGroup = new TargetGroup(1, "1e leerjaar");
        initialTargetGroups = new ArrayList<>();
        initialTargetGroups.add(initialTargetGroup);
        initialCompany = new Company(1, "factom", "www.factom.org", "Paul Snow", "Paul.Snow@factom.org");

    }

    @Test
    public void LearningUtilityDefaultConstructorAnCreatesAnObject() {
;
        assertTrue(initialLearningUtility instanceof LearningUtility);
        assertNotNull(initialLearningUtility);

    }

    @Test
    public void LearningUtilityParameterConstructorCreatesAnObjectAndList() {

        String name = "Wereldbol";
        String description = "Een wereldbol voor de lessen Aardrijkskunde!";

        LearningUtility learningUtility2 = new LearningUtility(2, name, description, BigDecimal.ZERO, true, 5, 1);

        assertTrue(learningUtility2 instanceof LearningUtility);
        assertTrue(learningUtility2.getLoanable());
        assertEquals(name, learningUtility2.getName());
        assertEquals(description, learningUtility2.getDescription());
        assertEquals(BigDecimal.ZERO, learningUtility2.getPrice());

    }
    
    @Test
        public void LearningUtilityLocationSetsIt()
        {

            initialLearningUtility.setLocationId(initialLocation);

      
          assertEquals(initialLocation.getName(),initialLearningUtility.getLocationId().getName());
           
        }
        
         @Test
        public void LearningUtilityCompanySetsIt()
        {

            initialLearningUtility.setCompanyId(initialCompany);

      
          assertEquals(initialCompany.getName(),initialLearningUtility.getCompanyId().getName());
           
        }

        @Test
        public void LearningUtilityFieldOfStudyListSetsIt()
        {

            initialLearningUtility.setFieldOfStudyList(initialFieldOfStudyList);
            
      
          assertEquals(initialFieldOfStudyList.size(),initialLearningUtility.getFieldOfStudyList().size());
           
        }
        @Test
        public void LearningUtilityTargetGroupListSetsIt()
        {

            initialLearningUtility.setTargetGroupList(initialTargetGroups);
            
      
          assertEquals(initialTargetGroups.size(),initialLearningUtility.getTargetGroupList().size());
           
        }
}
