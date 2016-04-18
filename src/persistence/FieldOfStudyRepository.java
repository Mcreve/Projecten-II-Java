/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.FieldOfStudy;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ward Vanlerberghe
 */
public class FieldOfStudyRepository {
    
    private EntityManager entityManager = Connection.entityManager();
    
    public List<FieldOfStudy> findAll(){
        return entityManager.createNamedQuery("FieldOfStudy.findAll", FieldOfStudy.class).getResultList();
    }
    
    public FieldOfStudy findBy(int id){
        TypedQuery<FieldOfStudy> query = entityManager.createNamedQuery("FieldOfStudy.findById", FieldOfStudy.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    
    public void add(FieldOfStudy fieldOfStudy){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(fieldOfStudy);
            transaction.commit();
        }
        catch(Exception e){
            transaction.rollback();
            //should throw exception for gui handling
        }
    }
    
    public void update(FieldOfStudy fieldOfStudy){
        add(fieldOfStudy);
    }
    
}
