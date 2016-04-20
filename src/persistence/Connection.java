/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ward Vanlerberghe
 */
public class Connection {
    
    private static Connection connection;
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DidactischeLeermiddelenPU");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    private Connection(){}
    
    public static EntityManager entityManager(){
        if(connection == null){
            connection = new Connection();
        }        
        
        return entityManager;
    }
    
    public static void close(){
        entityManagerFactory.close();
        entityManager.close();
    }
    
    
}
