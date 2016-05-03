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
import java.util.Arrays;
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

    private AdvancedCatalog<LearningUtility> learningUtilityCatalog;
    private AdvancedCatalog<Company> companyCatalog;
    private AdvancedCatalog<FieldOfStudy> fieldOfStudyCatalog;
    private AdvancedCatalog<TargetGroup> targetGroupCatalog;
    private AdvancedCatalog<Location> locationCatalog;
    private AdvancedCatalog<User> userCatalog;

    private static int IX_Name_Column = 0;
    private static int IX_Description_Column = 1;
    private static int IX_Loanable_Column = 2;
    private static int IX_Article_No_Column = 3;
    private static int IX_Photo_URL_Column = 4;
    private static int IX_Price_Column = 5;
    private static int IX_AmountInStock_Column = 6;
    private static int IX_AmountUnavailable_Column = 7;
    private static int IX_Location_Column = 8;
    private static int IX_Company_Column = 9;
    private static int IX_TargetGroups_Column = 10;
    private static int IX_FieldsOfStudy_Column = 11;
    private String[] header;

    public DomainController(String test) {

    }

    public DomainController() {
        learningUtilityCatalog = new AdvancedCatalog<>(LearningUtility.class);
        companyCatalog = new AdvancedCatalog<>(Company.class);
        fieldOfStudyCatalog = new AdvancedCatalog<>(FieldOfStudy.class);
        targetGroupCatalog = new AdvancedCatalog<>(TargetGroup.class);
        locationCatalog = new AdvancedCatalog<>(Location.class);
        userCatalog = new AdvancedCatalog<>(User.class);
    }

    public List<String> getFieldsOfStudy() {
        return fieldOfStudyCatalog.getEntities().stream().map(FieldOfStudy::getName).sorted().collect(Collectors.toList());
    }

    public void setFieldsOfStudy(AdvancedCatalog<FieldOfStudy> fieldOfStudyCatalog) {
        this.fieldOfStudyCatalog = fieldOfStudyCatalog;
    }

    public List<String> getTargetGroups() {
        return targetGroupCatalog.getEntities().stream().map(TargetGroup::getName).sorted().collect(Collectors.toList());
    }

    public void setTargetGroups(AdvancedCatalog<TargetGroup> targetGroupCatalog) {
        this.targetGroupCatalog = targetGroupCatalog;
    }

    public List<String> getLocations() {
        return locationCatalog.getEntities().stream().map(Location::getName).sorted().collect(Collectors.toList());
    }

    public void setLocations(AdvancedCatalog<Location> locationCatalog) {
        this.locationCatalog = locationCatalog;
    }

    public List<String> getCompanies() {
        return companyCatalog.getEntities().stream().map(Company::getName).sorted().collect(Collectors.toList());
    }

    public void setCompanies(AdvancedCatalog<Company> companyCatalog) {
        this.companyCatalog = companyCatalog;
    }

    public List<LearningUtility> getUtilities() {
        return learningUtilityCatalog.getEntities();
    }

    public void setUtilities(AdvancedCatalog<LearningUtility> learningUtilityCatalog) {
        this.learningUtilityCatalog = learningUtilityCatalog;
    }

    public void setUsers(AdvancedCatalog<User> userCatalog) {
        this.userCatalog = userCatalog;
    }

    public void addLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image,
            int amountInstock, int AmountUnavailable, String companyName, String locationName, List<String> targetGroups, List<String> fieldsOfStudy) {

        if (amountInstock < 1) {
            throw new IllegalArgumentException("Aantal in stock moet meer zijn dan één.");
        }
        if (name == null || name == "") {
            throw new IllegalArgumentException("Gelieve een naam op te geven.");
        }
        if (learningUtilityCatalog.getEntities().stream().anyMatch(l -> l.getName().equals(name))) {
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

        for (String targetGroupName : targetGroups) {

            targetGroupsList.add(targetGroupCatalog.getByName(targetGroupName));

        }
        newItem.setTargetGroupList(targetGroupsList);

        List<FieldOfStudy> fieldOfStudyList = new ArrayList<>();

        for (String fieldOfStudyName : fieldsOfStudy) {

            fieldOfStudyList.add(fieldOfStudyCatalog.getByName(fieldOfStudyName));

        }
        newItem.setFieldOfStudyList(fieldOfStudyList);
        return newItem;
    }

    public ObservableList<LearningUtility> readCsvFile(String file) throws IOException {
        if (!file.endsWith(".csv")) {
            throw new IllegalArgumentException("Ongeldige extentie. Het bestand moet van type \".csv\" zijn.");
        }

        ObservableList<LearningUtility> items = FXCollections.observableArrayList();

        try {
            Scanner sc = new Scanner(Paths.get(file));
            //header = sc.nextLine().split(";");
            ArrayList learningUtilityNames = new ArrayList();
            while (sc.hasNext()) {
                String[] tokens = sc.nextLine().split(";");
                if (tokens.length != 12) {
                    throw new IllegalStateException("Het opgegeven bestand is verkeerd ingedeeld. Per regel moeten er exact 12 waarden zijn gescheiden door een \";\".");
                }
                if ((learningUtilityNames.contains(tokens[IX_Name_Column]))) {
                    throw new IllegalArgumentException("Het bestand bevat lijnen die geen unieke naam hebben");
                }
                if ((!tokens[IX_AmountInStock_Column].equals("")) && Integer.parseInt(tokens[IX_AmountInStock_Column]) < 0) {
                    throw new IllegalArgumentException("Het bestand bevat een lijn met een negatief aantal beschikbaar");
                }
                if ((!tokens[IX_AmountUnavailable_Column].equals("")) && Integer.parseInt(tokens[IX_AmountUnavailable_Column]) <= 0) {
                    throw new IllegalArgumentException("Het bestand bevat een lijn met een negatief aantal onbeschikbaar");
                }
                if ((!tokens[IX_Price_Column].equals("")) && Integer.parseInt(tokens[IX_Price_Column]) < 0) {
                    throw new IllegalArgumentException("Het bestand bevat een lijn met een negatieve prijs");
                }
                LearningUtility learningUtility = parseCsvLine(tokens);
                items.add(learningUtility);
                learningUtilityNames.add(tokens[IX_Name_Column]);

            }
        } catch (IOException e) {
            throw new IOException("Fout bij het lezen van bestand.");
        }

        return items;
    }

    public void registerItems(List<String[]> items) {
        items.stream().forEach((String[] i) -> {
            String name = i[0];
            String description = i[1];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(i[2]));
            Boolean loanable = (i[3] == "true" ? true : false);
            String image = i[4];
            Location location = locationCatalog.getByName(i[5]);
            int amount = Integer.parseInt(i[6]);
            int amountUnavailable = Integer.parseInt(i[7]);
            Company company = companyCatalog.getByName(i[8]);
            List<TargetGroup> targetGroups = targetGroupCatalog.getEntities().stream().filter(entity -> entity.getName().contains(i[9])).collect(Collectors.toList());
            //LearningUtility item = createLearningUtility()
        });
    }

    public void createCompany(String name, String website, String contactPerson, String email) {
        if (name.isEmpty() || website.isEmpty() || contactPerson.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Alle velden moeten ingevuld worden.");
        }
        Company c = companyCatalog.getByName(name);
        if (c != null) {
            throw new IllegalArgumentException("Dit bedrijf bestaat reeds in het systeem.");
        }

        c = new Company();
        c.setName(name);
        c.setWebsite(website);
        c.setContactPersonName(contactPerson);
        c.setEmailAddress(email);
        companyCatalog.addEntity(c);
    }

    public void createLocation(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Gelieve een naam op te geven voor de nieuwe locatie");
        }
        Location l = locationCatalog.getByName(name);
        if (l != null) {
            throw new IllegalArgumentException("De opgegeven locatie bestaat reeds in het systeem");
        }

        l = new Location();
        l.setName(name);
        locationCatalog.addEntity(l);
    }

    public void createFieldOfStudy(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Gelieve een naam op te geven voor het nieuwe leergebied.");
        }
        FieldOfStudy f = fieldOfStudyCatalog.getByName(name);
        if (f != null) {
            throw new IllegalArgumentException("Het opgegeven leergebied bestaat reeds in het systeem.");
        }

        f = new FieldOfStudy();
        f.setName(name);
        fieldOfStudyCatalog.addEntity(f);
    }

    public void createTargetGroup(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Gelieve een naam op te geven voor de nieuwe doelgroep.");
        }
        TargetGroup t = targetGroupCatalog.getByName(name);
        if (t != null) {
            throw new IllegalArgumentException("De opgegeven doelgroep bestaat reeds in het systeem");
        }

        t = new TargetGroup();
        t.setName(name);
        targetGroupCatalog.addEntity(t);
    }

    public void addCompanyObserver(Observer o) {
        companyCatalog.addObserver(o);
    }

    public void addFieldOfStudyObserver(Observer o) {
        fieldOfStudyCatalog.addObserver(o);
    }

    public void addTargetGroupObserver(Observer o) {
        targetGroupCatalog.addObserver(o);
    }

    public void addLocationObserver(Observer o) {
        locationCatalog.addObserver(o);
    }

    public void closeConnection() {
        Connection.close();
    }

    private LearningUtility parseCsvLine(String[] tokens) {
        Double price;
        int amountUnavailable;

        String name = tokens[IX_Name_Column];
        String description = tokens[IX_Description_Column];
        if (!(tokens[IX_Price_Column].equals(""))) {
            price = Double.parseDouble(tokens[IX_Price_Column]);
        } else {
            price = 0.0;
        }

        String loanableString = tokens[IX_Loanable_Column];
        Boolean loanable = loanableString.equalsIgnoreCase("Ja");
        String articleNumber = tokens[IX_Article_No_Column];
        String image = tokens[IX_Photo_URL_Column];
        String locationName = tokens[IX_Location_Column];
        int amountInstock = Integer.parseInt(tokens[IX_AmountInStock_Column]);
        if (!(tokens[IX_AmountUnavailable_Column].equals(""))) {
            amountUnavailable = Integer.parseInt(tokens[IX_AmountUnavailable_Column]);
        } else {
            amountUnavailable = 0;
        }
        String companyName = tokens[IX_Company_Column];
        List<String> targetGroupsList = new ArrayList<>();
        List<String> fieldsOfStudyList = new ArrayList<>();

        targetGroupsList = Arrays.asList(tokens[IX_TargetGroups_Column].split(";"));
        fieldsOfStudyList = Arrays.asList(tokens[IX_FieldsOfStudy_Column].split(";"));

        return createLearningUtility(name,
                description,
                BigDecimal.valueOf(price),
                loanable,
                articleNumber,
                image,
                locationName,
                amountInstock,
                amountUnavailable,
                companyName,
                targetGroupsList, fieldsOfStudyList);

    }
}
