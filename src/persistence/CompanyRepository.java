/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.Company;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ward Vanlerberghe
 */
public class CompanyRepository {
    
    private EntityManager entityManager = Connection.entityManager();
    private static CompanyRepository instance;
    
    private CompanyRepository(){
        instance = this;
    }

    public static CompanyRepository getInstance() {
        if(instance !=null)
            return instance;
        return new CompanyRepository();
    }
    
    public List<Company> findAll(){
        return entityManager.createNamedQuery("Company.findAll", Company.class).getResultList();
    }
    
    public Company findBy(int id){
        TypedQuery<Company> query = entityManager.createNamedQuery("Company.findById", Company.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    
    public void add(Company company){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(company);
            transaction.commit();
        }
        catch(Exception e){
            transaction.rollback();
            //should throw exception for gui handling
        }
    }
    
    public void update(Company company){
        add(company);
    }
    
}