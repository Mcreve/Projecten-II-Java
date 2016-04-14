/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ward Vanlerberghe
 */
public class Connection {
    
    private static Connection connection;
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Leermiddelen");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    public static EntityManager entityManager(){
        if(connection == null){
            connection = new Connection();
        }        
        
        return entityManager;
    }
    
    
}
