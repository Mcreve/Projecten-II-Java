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
import javax.persistence.TypedQuery;

/**
 *
 * @author Ward Vanlerberghe
 */
public class LearningUtilityRepository {
    
    private EntityManager entityManager = Connection.entityManager();
    private static LearningUtilityRepository instance;
    
    public LearningUtilityRepository(){
        instance = this;
    }
    
    public static LearningUtilityRepository getInstance(){
        if(instance != null){
            return instance;
        }
        return new LearningUtilityRepository();
    }
    
    public List<LearningUtility> findAll(){
        return entityManager.createNamedQuery("LearningUtility.findAll", LearningUtility.class).getResultList();
    }
    
    public LearningUtility findBy(int id){
        TypedQuery<LearningUtility> query = entityManager.createNamedQuery("LearningUtility.findById", LearningUtility.class);
        query.setParameter("id", id);
        return query.getSingleResult();
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