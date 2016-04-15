import domain.LearningUtility;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ward Vanlerberghe
 */
public class DatabaseTest {
    
    public static void main (String [] args){
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("DidactischeLeermiddelenPU");
        EntityManager manager = managerFactory.createEntityManager();
    
        TypedQuery<LearningUtility> findAllUsers = manager.createNamedQuery("LearningUtility.findAll", LearningUtility.class);
        
        findAllUsers.getResultList().stream().forEach((user) -> {
            System.out.println(user.getName());
        });
    
    }
    
}
