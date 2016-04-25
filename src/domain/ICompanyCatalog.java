/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
import domain.learningUtility.Company;

/**
 *
 * @author Append
 */
public interface ICompanyCatalog extends ICatalog<Company> {
    public Company getByName(String name);

}
