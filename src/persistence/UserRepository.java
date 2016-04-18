/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.User;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Ward Vanlerberghe
 */
public class UserRepository {
    
    private EntityManager entityManager = Connection.entityManager();
    private static UserRepository instance;
    
    private UserRepository(){
        instance = this;
    }
    
    public static UserRepository getInstance(){
        if(instance != null)
            return instance;
        return new UserRepository();
    }
    
    public List<User> findAll(){
        return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    }
    
    public User findBy(String emailAddress){
        return entityManager.createNamedQuery("User.findByEmailAddress", User.class).setParameter("EmailAddress", emailAddress).getSingleResult();
    }
    
}
