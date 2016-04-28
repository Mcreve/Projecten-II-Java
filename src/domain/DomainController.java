/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.catalogs.*;
import domain.learningUtility.*;
import domain.users.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.Connection;

/**
 *
 * @author Ward Vanlerberghe
 */
public class DomainController {
    
    private AdvancedCatalog<LearningUtility>    learningUtilityCatalog;
    private AdvancedCatalog<Company>            companyCatalog;
    private AdvancedCatalog<FieldOfStudy>       fieldOfStudyCatalog;
    private AdvancedCatalog<TargetGroup>        targetGroupCatalog;
    private AdvancedCatalog<Location>           locationCatalog;
    private AdvancedCatalog<User>               userCatalog;
    
    
    public DomainController(){
        learningUtilityCatalog = new AdvancedCatalog<>(LearningUtility.class);    
        companyCatalog         = new AdvancedCatalog<>(Company.class);
        fieldOfStudyCatalog    = new AdvancedCatalog<>(FieldOfStudy.class);
        targetGroupCatalog     = new AdvancedCatalog<>(TargetGroup.class);
        locationCatalog        = new AdvancedCatalog<>(Location.class);
        userCatalog            = new AdvancedCatalog<>(User.class);
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
        System.out.println(newItem.toString());
        
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
    
    public List<String[]> readCsvFile(String file) throws IOException{
        if(!file.endsWith(".csv"))
            throw new IllegalArgumentException("Ongeldige extentie. Het bestand moet van type \".csv\" zijn.");

        ObservableList<String[]> items = FXCollections.observableArrayList();

        
        try{
            Scanner sc = new Scanner(Paths.get(file));
            while(sc.hasNext()){
                String[] tokens = sc.nextLine().split(";");               
                if(tokens.length != 12)
                    throw new IllegalStateException("Het opgegeven bestand is verkeerd ingedeeld. Per regel moeten er exact 12 waarden zijn gescheiden door een \";\".");
                items.add(tokens);         
            }
        } catch(IOException e){
            throw new IOException("Fout bij het lezen van bestand.");
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
    
    public void createCompany(String name, String website, String contactPerson, String email){
        if(name.isEmpty() || website.isEmpty()|| contactPerson.isEmpty() || email.isEmpty())
            throw new IllegalArgumentException("Alle velden moeten ingevuld worden.");
        Company c = companyCatalog.getByName(name);
        if(c != null)
            throw new IllegalArgumentException("Dit bedrijf bestaat reeds in het systeem.");
        
        c = new Company();
        c.setName(name);
        c.setWebsite(website);
        c.setContactPersonName(contactPerson);
        c.setEmailAddress(email);
        companyCatalog.addEntity(c);
    }
    
    public void createLocation(String name){
        if(name.isEmpty())
            throw new IllegalArgumentException("Gelieve een naam op te geven voor de nieuwe locatie");
        Location l = locationCatalog.getByName(name);
        if(l != null)
            throw new IllegalArgumentException("De opgegeven locatie bestaat reeds in het systeem");
        
        l = new Location();
        l.setName(name);
        locationCatalog.addEntity(l);
    }
    
    public void createFieldOfStudy(String name){
        if(name.isEmpty())
            throw new IllegalArgumentException("Gelieve een naam op te geven voor het nieuwe leergebied.");
        FieldOfStudy f = fieldOfStudyCatalog.getByName(name);
        if(f != null)
            throw new IllegalArgumentException("Het opgegeven leergebied bestaat reeds in het systeem.");
        
        f = new FieldOfStudy();
        f.setName(name);
        fieldOfStudyCatalog.addEntity(f);
    }
    
    public void createTargetGroup(String name) {
        if(name.isEmpty())
            throw new IllegalArgumentException("Gelieve een naam op te geven voor de nieuwe doelgroep.");
        TargetGroup t = targetGroupCatalog.getByName(name);
        if(t != null)
            throw new IllegalArgumentException("De opgegeven doelgroep bestaat reeds in het systeem");
        
        t = new TargetGroup();
        t.setName(name);
        targetGroupCatalog.addEntity(t);
    }
    
    public void addCompanyObserver(Observer o){
        companyCatalog.addObserver(o);
    }
    
    public void addFieldOfStudyObserver(Observer o){
        fieldOfStudyCatalog.addObserver(o);
    }
    
    public void addTargetGroupObserver(Observer o){
        targetGroupCatalog.addObserver(o);
    }
    
    public void addLocationObserver(Observer o){
        locationCatalog.addObserver(o);
    }

    public void closeConnection() {
        Connection.close();
    }
}
