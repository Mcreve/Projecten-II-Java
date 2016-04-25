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
public class CompanyCatalog extends Catalog<Company> implements ICompanyCatalog
{
    public CompanyCatalog() 
    {
        super(Company.class);
    }

    @Override
    public Company getByName(String name) {
        loadEntities();
        return entities.stream().filter(entity -> entity.getName().contains(name)).findFirst().get();
    }

}
