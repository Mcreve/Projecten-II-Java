/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import persistence.PersistenceController;

/**
 *
 * @author Ward Vanlerberghe
 */
public class DomainController {
    
    private PersistenceController persistenceController = PersistenceController.getInstance();
    private static DomainController instance;
    
    private DomainController(){
        instance = this;
    }
    
    public static DomainController getInstance(){
        if(instance != null)
            return instance;
        return new DomainController();
    }
    
    public void addLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image, 
            int amountInstock, int AmountUnavailable, int companyId, int locationId, int[] targetGroupId, int[] fieldOfStudyId){
        
        if(name.isEmpty() || amountInstock < 1)
            throw new IllegalArgumentException("De naam en het aantal beschikbaar dient ingevuld te zijn.");
        if(persistenceController.findAllLearningUtilities().stream().anyMatch(l -> l.getName().equals(name))){
            throw new IllegalArgumentException("Er bestaat al een artikel met de opgegeven naam.");
        }
        
        LearningUtility newItem = new LearningUtility();
        newItem.setName(name);
        newItem.setDescription(description);
        newItem.setPrice(price);
        newItem.setLoanable(loanable);
        newItem.setArticleNumber(articleNumber);
        newItem.setPicture(image);
        newItem.setAmountInCatalog(amountInstock);
        newItem.setAmountUnavailable(AmountUnavailable);
        newItem.setCompanyId(persistenceController.findCompanyById(companyId));
        newItem.setLocationId(persistenceController.findLocationById(companyId));
        
        List<TargetGroup> targetGroups = new ArrayList<>();        
        for(int id : targetGroupId){
            targetGroups.add(persistenceController.findTargetGroupById(id));
        }
        newItem.setTargetGroupList(targetGroups);
        
        List<FieldOfStudy> fieldsOfStudy = new ArrayList<>();        
        for(int id: fieldOfStudyId){
            fieldsOfStudy.add(persistenceController.findFieldOfStudyById(id));
        }
        newItem.setFieldOfStudyList(fieldsOfStudy);        
    }
    
}
