/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.Location;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ward Vanlerberghe
 */
public class LocationRepository {


    
    private EntityManager entityManager = Connection.entityManager();
    private static LocationRepository instance;
    
    private LocationRepository(){
        instance = this;
    }
    
    public static LocationRepository getInstance() {
        if(instance != null){
            return instance;
        }        
        return new LocationRepository();
    }
    
    public List<Location> findAll(){
        return entityManager.createNamedQuery("Location.findAll", Location.class).getResultList();
    }
    
    public Location findBy(int id){
        TypedQuery<Location> query = entityManager.createNamedQuery("Location.findById", Location.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    
    public void add(Location location){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(location);
            transaction.commit();
        }
        catch(Exception e){
            transaction.rollback();
            //should throw exception for gui handling
        }
    }
    
    public void update(Location location){
        add(location);
    } 
    
}
