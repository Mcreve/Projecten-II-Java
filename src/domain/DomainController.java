/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import persistence.GenericDaoJpa;

/**
 *
 * @author Ward Vanlerberghe
 */
public class DomainController {
    
    private GenericDaoJpa<LearningUtility> learningUtilities;
    private GenericDaoJpa<Company> companies;
    private GenericDaoJpa<FieldOfStudy> fieldsOfStudy;
    private GenericDaoJpa<TargetGroup> targetGroups;
    private GenericDaoJpa<Location> locations;
    
    public DomainController(){
        learningUtilities = new GenericDaoJpa<>(LearningUtility.class);
        companies = new GenericDaoJpa<>(Company.class);
        fieldsOfStudy = new GenericDaoJpa<>(FieldOfStudy.class);
        targetGroups = new GenericDaoJpa<>(TargetGroup.class);
        locations = new GenericDaoJpa<>(Location.class);
    }
    
    public void addLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image, 
            int amountInstock, int AmountUnavailable, int companyId, int locationId, int[] targetGroupId, int[] fieldOfStudyId){
        
        if(name.isEmpty() || amountInstock < 1)
            throw new IllegalArgumentException("De naam en het aantal beschikbaar dient ingevuld te zijn.");
        if(learningUtilities.findAll().stream().anyMatch(l -> l.getName().equals(name))){
            throw new IllegalArgumentException("Er bestaat al een artikel met de opgegeven naam.");
        }
        
        LearningUtility newItem = createLearningUtility(name, description, price, loanable, articleNumber, image, locationId, amountInstock, AmountUnavailable, companyId, targetGroupId, fieldOfStudyId);

        GenericDaoJpa.startTransaction();
        learningUtilities.insert(newItem);
        GenericDaoJpa.commitTransaction();
    }

    private LearningUtility createLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image, int locationId, int amountInstock, int AmountUnavailable, int companyId, int[] targetGroupId, int[] fieldOfStudyId) {
        LearningUtility newItem = new LearningUtility();
        newItem.setName(name);
        newItem.setDescription(description);
        newItem.setPrice(price);
        newItem.setLoanable(loanable);
        newItem.setArticleNumber(articleNumber);
        newItem.setPicture(image);
        newItem.setAmountInCatalog(amountInstock);
        newItem.setAmountUnavailable(AmountUnavailable);
        newItem.setCompanyId(companies.findBy(companyId));
        newItem.setLocationId(locations.findBy(locationId));
        List<TargetGroup> targetGroupsList = new ArrayList<>();        
        for(int id : targetGroupId){
            targetGroupsList.add(targetGroups.findBy(id));
        }
        newItem.setTargetGroupList(targetGroupsList);
        List<FieldOfStudy> fieldsOfStudyList = new ArrayList<>();        
        for(int id: fieldOfStudyId){
            fieldsOfStudyList.add(fieldsOfStudy.findBy(id));
        }
        newItem.setFieldOfStudyList(fieldsOfStudyList);
        return newItem;
    }
    
    public List<String[]> readCsvFile(String file){
        if(!file.endsWith(".csv"))
            throw new IllegalArgumentException("Ongeldige extentie. Het bestand moet van type \".csv\" zijn.");
        
        List<String[]> items = new ArrayList<>();
        
        try{
            Scanner sc = new Scanner(Paths.get(file));
            
            while(sc.hasNext()){
                String[] tokens = sc.nextLine().split(";");
                if(tokens.length != 12)
                    throw new IllegalStateException("Het opgegeven bestand is verkeerd ingedeeld. Per regel moeten er exact 12 waarden zijn gescheiden door een \";\".");
                items.add(tokens);         
            }
        } catch(IOException e){
            
        }
        
        return items;
    }
    
 //   public void registerItems(List<String[]> items){
 //       items.stream().forEach((String[] i) -> {
 //           String name = i[0];
 //           String description = i[1];
 //           BigDecimal price = BigDecimal.valueOf(Double.parseDouble(i[2]));
 //           Boolean loanable = (i[3] == "true" ? true:false);
 //           String image = i[4];
 //           Location location = persistenceController.findLocationById(Integer.parseInt(i[5]));
 //           int amount = Integer.parseInt(i[6]);
 //           int amountUnavailable = Integer.parseInt(i[7]);
 //           Company company = persistenceController.findCompanyById(Integer.parseInt(i[8]));
 //           List<TargetGroup> targetGroups = persistenceController.findAllTargetGroups().stream().filter(predicate)
 //           LearningUtility item = createLearningUtility()
 //       });
 //   }
}
