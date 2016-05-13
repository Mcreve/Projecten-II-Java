/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.users;

import javax.persistence.Entity;

/**
 *
 * @author Ward Vanlerberghe
 */
@Entity
public class Manager extends Lector{
    
    public Manager(){
        super();
    }
    
    public Manager(User user){
        this.setFirstName(user.getFirstName());
        this.setEmailAddress(user.getEmailAddress());
        this.setLastName(user.getLastName());
        
    }
    @Override
    public String toString(){
        return super.toString() + " " + this.getClass().getSimpleName();
    }
    
}
