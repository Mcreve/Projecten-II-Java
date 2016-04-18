/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.TargetGroup;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ward Vanlerberghe
 */
public class TargetGroupRepository {
    
    private EntityManager entityManager = Connection.entityManager();
    private static TargetGroupRepository instance;
    
    private TargetGroupRepository(){
        instance = this;
    }

    public static TargetGroupRepository getInstance() {
        if(instance != null)
            return instance;
        return new TargetGroupRepository();
    }
    
    public List<TargetGroup> findAll(){
        return entityManager.createNamedQuery("TargetGroup.findAll", TargetGroup.class).getResultList();
    }
    
    public TargetGroup findBy(int id){
        TypedQuery<TargetGroup> query = entityManager.createNamedQuery("TargetGroup.findById", TargetGroup.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    
    public void add(TargetGroup targetGroup){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(targetGroup);
            transaction.commit();
        }
        catch(Exception e){
            transaction.rollback();
            //should throw exception for gui handling
        }
    }
    
    public void update(TargetGroup targetGroup){
        add(targetGroup);
    }
    
}
