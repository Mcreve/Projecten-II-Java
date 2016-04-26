/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.catalogs.Catalog;
import domain.catalogs.FieldOfStudyCatalog;
import domain.catalogs.TargetGroupCatalog;
import domain.catalogs.CompanyCatalog;
import domain.catalogs.LocationCatalog;
import domain.learningUtility.Company;
import domain.learningUtility.LearningUtility;
import domain.learningUtility.FieldOfStudy;
import domain.learningUtility.Location;
import domain.learningUtility.TargetGroup;
import domain.users.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import persistence.Connection;

/**
 *
 * @author Ward Vanlerberghe
 */
public class DomainController {
    
    private Catalog<LearningUtility> learningUtilityCatalog;
    private CompanyCatalog companyCatalog;
    private FieldOfStudyCatalog fieldOfStudyCatalog;
    private TargetGroupCatalog targetGroupCatalog;
    private LocationCatalog locationCatalog;
    private Catalog<User> userCatalog;
    
    
    public DomainController(){
        learningUtilityCatalog = new Catalog<>(LearningUtility.class);
        companyCatalog = new CompanyCatalog();
        fieldOfStudyCatalog = new FieldOfStudyCatalog();
        targetGroupCatalog = new TargetGroupCatalog();
        locationCatalog = new LocationCatalog();
        userCatalog = new Catalog<>(User.class);
    }
    
    
    public List<String> getFieldsOfStudy(){
        return fieldOfStudyCatalog.getEntities().stream().map(FieldOfStudy::getName).collect(Collectors.toList());
    }
    
    public List<String> getTargetGroups(){
        return targetGroupCatalog.getEntities().stream().map(TargetGroup::getName).collect(Collectors.toList());
    }
    
    public List<String> getLocations(){
        return locationCatalog.getEntities().stream().map(Location::getName).collect(Collectors.toList());
    }
    public List<String> getCompanies(){
        return companyCatalog.getEntities().stream().map(Company::getName).collect(Collectors.toList());
    }
    
    public List<LearningUtility> getUtilities(){
        return learningUtilityCatalog.getEntities();
    }
    
    public void addLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image, 
            int amountInstock, int AmountUnavailable, String companyName, String locationName, List<String> targetGroups, List<String> fieldsOfStudy){
        
        if(amountInstock < 1)
            throw new IllegalArgumentException("Aantal in stock moet meer zijn dan één.");
        if(learningUtilityCatalog.getEntities().stream().anyMatch(l -> l.getName().equals(name))){
            throw new IllegalArgumentException("Er bestaat al een artikel met de opgegeven naam.");
        }
        
        LearningUtility newItem = createLearningUtility(name, description, price, loanable, articleNumber, image, locationName, amountInstock, AmountUnavailable, companyName, targetGroups, fieldsOfStudy);
        learningUtilityCatalog.addEntity(newItem);
    }

    private LearningUtility createLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image, String locationName, int amountInstock, int AmountUnavailable, String companyName, List<String> targetGroups, List<String> fieldsOfStudy) {
        LearningUtility newItem = new LearningUtility();
        newItem.setName(name);
        newItem.setDescription(description);
        newItem.setPrice(price);
        newItem.setLoanable(loanable);
        newItem.setArticleNumber(articleNumber);
        newItem.setPicture(image);
        newItem.setAmountInCatalog(amountInstock);
        newItem.setAmountUnavailable(AmountUnavailable);
        newItem.setCompanyId(companyCatalog.getByName(companyName));
        newItem.setLocationId(locationCatalog.getByName(locationName));
        
        List<TargetGroup> targetGroupsList = new ArrayList<>();        

        for(String targetGroupName : targetGroups)
        {
            
            targetGroupsList.add(targetGroupCatalog.getByName(targetGroupName));
            
        }
        newItem.setTargetGroupList(targetGroupsList);
        
        List<FieldOfStudy> fieldOfStudyList = new ArrayList<>();        

        for(String fieldOfStudyName : fieldsOfStudy)
        {
            
            fieldOfStudyList.add(fieldOfStudyCatalog.getByName(fieldOfStudyName));
            
        }
        newItem.setFieldOfStudyList(fieldOfStudyList);      
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
    
    public void registerItems(List<String[]> items){
        items.stream().forEach((String[] i) -> {
            String name = i[0];
            String description = i[1];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(i[2]));
            Boolean loanable = (i[3] == "true" ? true:false);
            String image = i[4];
            Location location = locationCatalog.getByName(i[5]);
            int amount = Integer.parseInt(i[6]);
            int amountUnavailable = Integer.parseInt(i[7]);
            Company company = companyCatalog.getByName(i[8]);
            List<TargetGroup> targetGroups = targetGroupCatalog.getEntities().stream().filter(entity -> entity.getName().contains(i[9])).collect(Collectors.toList());
            //LearningUtility item = createLearningUtility()
        });
    }

    public void closeConnection() {
        Connection.close();
    }
}
