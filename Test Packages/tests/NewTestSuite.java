/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import tests.domain2.LocationTest;
import tests.domain2.CompanyTest;
import tests.domain2.FieldOfStudyTest;
import tests.domain2.LearningUtilityTest;
import tests.domain2.TargetGroupTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Ward Vanlerberghe
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({DomainControllerTests.class, CompanyTest.class, FieldOfStudyTest.class, LearningUtilityTest.class, LocationTest.class,TargetGroupTest.class})
public class NewTestSuite {
        
}
