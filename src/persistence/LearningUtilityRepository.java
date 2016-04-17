/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.LearningUtility;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Ward Vanlerberghe
 */
public class LearningUtilityRepository {
    
    EntityManager entityManager = Connection.entityManager();
    
    public List<LearningUtility> findAll(){
        return entityManager.createNamedQuery("LearningUtility.findAll", LearningUtility.class).getResultList();
    }
    
    public LearningUtility findBy(int id){
        return entityManager.createNamedQuery("LearningUtility.findById", LearningUtility.class).getSingleResult();
    }
    
    public void add(LearningUtility learningUtility){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(learningUtility);
            transaction.commit();
        }
        catch(Exception e){
            transaction.rollback();
            //should throw exception for gui handling
        }
    }
    
    public void update(LearningUtility learningUtility){
        add(learningUtility);
    }
    
}