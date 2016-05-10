/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.domain;

import domain.learningUtility.Company;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim
 */
public class CompanyTest {
    

    @Test
     public void CompanyDefaultConstructorCreatesACompany()
        {

           Company company = new Company();

            assertNotNull(company);
            assertTrue(company instanceof Company);

        }
    
     @Test
     public void CompanyParameterConstructorCreatesACompany()
        {
           
           String companyName = "Test";
           Company company = new Company(1,companyName, null,null,null);

            assertEquals(company.getName(), companyName);

        }
    }

