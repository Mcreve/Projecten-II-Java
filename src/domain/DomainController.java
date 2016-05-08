/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.catalogs.*;
import domain.interfaces.IAdvancedCatalog;
import domain.interfaces.ICatalog;
import domain.interfaces.IObserver;
import domain.learningUtility.*;
import domain.users.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import persistence.Connection;

/**
 * Interface for GUI -> Domain interaction. This class is the only domain class
 * that should be instantiated in the GUI layer, it hides the complexity from
 * the domain from the GUI layer. Only the DomainControllers methods should be
 * called from the GUI layer.
 *
 * @author Ward Vanlerberghe
 */
public class DomainController {

    private IAdvancedCatalog<LearningUtility> learningUtilityCatalog;
    private IAdvancedCatalog<Company> companyCatalog;
    private IAdvancedCatalog<FieldOfStudy> fieldOfStudyCatalog;
    private IAdvancedCatalog<TargetGroup> targetGroupCatalog;
    private IAdvancedCatalog<Location> locationCatalog;
    private IAdvancedCatalog<User> userCatalog;

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
    private ObservableList<LearningUtility> learningUtilityList;
    private FilteredList<LearningUtility> filteredLearningUtilityList;
    private LearningUtility selectedLearningUtility;

    /**
     * @param test useless information
     * @deprecated Faulty use of setterInjection, use
     * {@link #DomainController(ICatalog) DomainController(ICatalog)} or use the
     * defaultConstructor and then set the catalog mocks with the
     * {@link #setCatalog(ICatalog) setCatalog(ICatalog)} method.
     */
    public DomainController(String test) {

    }

    /**
     * Constructor that takes one parameter, this constructor is used for
     * testing purposes.
     *
     * @param <E> The type of the catalog, eg:
     * {@link LearningUtility}, {@link Company} or any other entity class
     * @param catalogMock The actual {@link ICatalog} instance that should be
     * injected for mocking purpose.
     */
    public <E> DomainController(ICatalog<E> catalogMock) {
        setCatalog(catalogMock);
    }

    /**
     * Default constructor. Should be used during application setup. The
     * constructor provides all instantiations of the classes needed to run the
     * domain.
     */
    public DomainController() {
        learningUtilityCatalog = new AdvancedCatalog<>(LearningUtility.class);
        companyCatalog = new AdvancedCatalog<>(Company.class);
        fieldOfStudyCatalog = new AdvancedCatalog<>(FieldOfStudy.class);
        targetGroupCatalog = new AdvancedCatalog<>(TargetGroup.class);
        locationCatalog = new AdvancedCatalog<>(Location.class);
        userCatalog = new AdvancedCatalog<>(User.class);
        learningUtilityList = FXCollections.observableArrayList(learningUtilityCatalog.getEntities());
        filteredLearningUtilityList = new FilteredList<>(learningUtilityList, p -> true);
    }

    /**
     * Returns a list with the name field values of all known
     * {@link FieldOfStudy} instances.
     *
     * @return a list with the name field values of all known
     * {@link FieldOfStudy} instances.
     */
    public List<String> getFieldsOfStudy() {
        return fieldOfStudyCatalog.getEntities().stream().map(FieldOfStudy::getName).sorted().collect(Collectors.toList());
    }

    /**
     * @param fieldOfStudyCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setFieldsOfStudy(IAdvancedCatalog<FieldOfStudy> fieldOfStudyCatalog) {
        this.fieldOfStudyCatalog = fieldOfStudyCatalog;
    }

    /**
     * Returns a list with the name field values of all known
     * {@link TargetGroup} instances.
     *
     * @return a list with the name field values of all known
     * {@link TargetGroup} instances.
     */
    public List<String> getTargetGroups() {
        return targetGroupCatalog.getEntities().stream().map(TargetGroup::getName).sorted().collect(Collectors.toList());
    }

    /**
     *
     * @param targetGroupCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setTargetGroups(IAdvancedCatalog<TargetGroup> targetGroupCatalog) {
        this.targetGroupCatalog = targetGroupCatalog;
    }

    /**
     * Returns a list with the name field values of all known {@link Location}
     * instances.
     *
     * @return a list with the name field values of all known {@link Location}
     * instances.
     */
    public List<String> getLocations() {
        return locationCatalog.getEntities().stream().map(Location::getName).sorted().collect(Collectors.toList());
    }

    /**
     *
     * @param locationCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setLocations(IAdvancedCatalog<Location> locationCatalog) {
        this.locationCatalog = locationCatalog;
    }

    /**
     * Returns a list with the name field values of all known {@link Company}
     * instances.
     *
     * @return a list with the name field values of all known {@link Company}
     * instances.
     */
    public List<String> getCompanies() {
        return companyCatalog.getEntities().stream().map(Company::getName).sorted().collect(Collectors.toList());
    }

    /**
     *
     * @param companyCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setCompanies(IAdvancedCatalog<Company> companyCatalog) {
        this.companyCatalog = companyCatalog;
    }

    /**
     * Returns a list with all known {@link LearningUtility} instances.
     *
     * @return a list with all known {@link LearningUtility} instances.
     */
    public List<LearningUtility> getLearningUtilities() {
        return learningUtilityCatalog.getEntities();
    }

    /**
     * Returns an observable list with all known {@link LearningUtility}
     * instances.
     *
     * @return an observable list with all known {@link LearningUtility}
     * instances.
     */
    public ObservableList<LearningUtility> getObservableLearningUtilityList() {
        return FXCollections.unmodifiableObservableList(learningUtilityList);
    }

    /**
     * Returns the filtered list of {@link LearningUtility} instances.
     *
     * @return the filtered list of {@link LearningUtility} instances.
     */
    public FilteredList<LearningUtility> getFilteredLearningUtilityList() {
        return filteredLearningUtilityList;
    }

    /**
     *
     * @param learningUtilityCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setUtilities(IAdvancedCatalog<LearningUtility> learningUtilityCatalog) {
        this.learningUtilityCatalog = learningUtilityCatalog;
    }

    /**
     *
     * @param userCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setUsers(IAdvancedCatalog<User> userCatalog) {
        this.userCatalog = userCatalog;
    }

    /**
     * Sets the {@link LearningUtility} as selected.
     *
     * @param learningUtility The {@link LearningUtility} that needs to be
     * marked as selected
     */
    public void setSelectedLearningUtility(LearningUtility learningUtility) {
        this.selectedLearningUtility = learningUtility;
    }

    /**
     * Gets the {@link LearningUtility} that is marked as selected
     *
     * @return
     */
    public LearningUtility getSelectedLearningUtility() {
        return this.selectedLearningUtility;
    }

    /**
     * This method checks if all paramaters are correct and then creates a new
     * instance of {@link LearningUtility} with provided parameters. Following
     * on creation, the instance is added to the {@link #learningUtilityCatalog}
     * for persistence.
     *
     * @param name The name of the item
     * @param description The description of the item
     * @param price The price of the item
     * @param loanable Indicates wether the item is loanable or not
     * @param articleNumber The article number of the item
     * @param image The url to the image of the item
     * @param amountInstock The amount that is in stock of this item
     * @param amountUnavailable The amount of items that aren't available
     * @param companyName The name of the company that distributes the item
     * @param locationName The location of wich the item is in
     * @param targetGroups A list with the names of the target groups
     * @param fieldsOfStudy A list with the names of the fields of study
     */
    public void addLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image,
            int amountInstock, int amountUnavailable, String companyName, String locationName, List<String> targetGroups, List<String> fieldsOfStudy) {

        if (amountInstock < 1) {
            throw new IllegalArgumentException("Aantal in stock moet meer zijn dan één.");
        }
        if (name == null || name == "") {
            throw new IllegalArgumentException("Gelieve een naam op te geven.");
        }
        if (learningUtilityCatalog.getEntities().stream().anyMatch(l -> l.getName().equals(name))) {
            throw new IllegalArgumentException("Er bestaat al een artikel met de opgegeven naam.");
        }

        LearningUtility newItem = createLearningUtility(name, description, price, loanable, articleNumber, image, locationName, amountInstock, amountUnavailable, companyName, targetGroups, fieldsOfStudy);
        System.out.println(newItem.toString());

        learningUtilityCatalog.addEntity(newItem);

    }

    /**
     * This method updates the fields of an {@link LearningUtility} instance and
     * then persists the changes made.
     *
     * @param learningUtility The {@link LearningUtility} that needs to be
     * updated
     * @param name The name of the item
     * @param description The description of the item
     * @param price The price of the item
     * @param loanable Indicates wether the item is loanable or not
     * @param articleNumber The article number of the item
     * @param image The url to the image of the item
     * @param amountInStock The amount that is in stock of this item
     * @param amountUnavailable The amount of items that aren't available
     * @param companyName The name of the company that distributes the item
     * @param locationName The location of wich the item is in
     * @param targetGroups A list with the names of the target groups
     * @param fieldsOfStudy A list with the names of the fields of study
     */
    public void editLearningUtility(LearningUtility learningUtility, String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image,
            int amountInStock, int amountUnavailable, String companyName, String locationName, List<String> targetGroups, List<String> fieldsOfStudy) {

        if (name == null || name == "" || learningUtilityCatalog.getEntities().stream().anyMatch(l -> l.getName().equals(name))) {
            throw new IllegalArgumentException("Er bestaat reeds een item met deze naam");
        } else {
            learningUtility.setName(name);
        }
        learningUtility.setDescription(description);
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("De prijs kan niet negatief zijn");

        } else {
            learningUtility.setPrice(price);
        }
        learningUtility.setLoanable(loanable);
        learningUtility.setArticleNumber(articleNumber);
        learningUtility.setPicture(image);
        learningUtility.setAmountInCatalog(amountInStock);
        learningUtility.setAmountUnavailable(amountUnavailable);
        learningUtility.setCompanyId(companyCatalog.getByName(companyName));
        learningUtility.setLocationId(locationCatalog.getByName(locationName));
        List<TargetGroup> targetGroupsList = new ArrayList<>();

        for (String targetGroupName : targetGroups) {

            targetGroupsList.add(targetGroupCatalog.getByName(targetGroupName));

        }
        learningUtility.setTargetGroupList(targetGroupsList);

        List<FieldOfStudy> fieldOfStudyList = new ArrayList<>();

        for (String fieldOfStudyName : fieldsOfStudy) {

            fieldOfStudyList.add(fieldOfStudyCatalog.getByName(fieldOfStudyName));

        }
        learningUtility.setFieldOfStudyList(fieldOfStudyList);
        learningUtilityCatalog.updateEntity(learningUtility);

        //learningUtilityCatalog.notifyAll();
    }
    
    public void removeLearningUtility(LearningUtility learningUtility)
    {
        learningUtilityCatalog.deleteEntity(learningUtility);
    }

    /**
     * This method creates a new {@link LearningUtility} instance and sets its
     * fields with the parameters provided. Then returns the instance.
     *
     * @param name The name of the item
     * @param description The description of the item
     * @param price The price of the item
     * @param loanable Indicates wether the item is loanable or not
     * @param articleNumber The article number of the item
     * @param image The url to the image of the item
     * @param amountInStock The amount that is in stock of this item
     * @param amountUnavailable The amount of items that aren't available
     * @param companyName The name of the company that distributes the item
     * @param locationName The location of wich the item is in
     * @param targetGroups A list with the names of the target groups
     * @param fieldsOfStudy A list with the names of the fields of study
     * @return A new instance of {@link LearningUtility} with its fields set
     * with the provided parameters
     */
    private LearningUtility createLearningUtility(String name, String description, BigDecimal price, boolean loanable, String articleNumber, String image, String locationName, int amountInstock, int amountUnavailable, String companyName, List<String> targetGroups, List<String> fieldsOfStudy) {
        LearningUtility newItem = new LearningUtility();
        newItem.setName(name);
        newItem.setDescription(description);
        newItem.setPrice(price);
        newItem.setLoanable(loanable);
        newItem.setArticleNumber(articleNumber);
        newItem.setPicture(image);
        newItem.setAmountInCatalog(amountInstock);
        newItem.setAmountUnavailable(amountUnavailable);
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

    /**
     * This method takes string as a parameter. The string represents a file.
     * The method checks if the file has the right extention (.csv) and checks
     * if the file is correctly formatted. If all conditions are met, the method
     * parses the file and returns the {@link LearningUtility} instances parsed
     * from the file.
     *
     * @param file the location to a .csv file
     * @return an observable list with the parsed {@link LearningUtility}
     * instances
     * @throws IOException Thrown when the file was not found at given location
     */
    public ObservableList<LearningUtility> readCsvFile(String file) throws IOException {
        if (!file.endsWith(".csv")) {
            throw new IllegalArgumentException("Ongeldige extentie. Het bestand moet van type \".csv\" zijn.");
        }

        ObservableList<LearningUtility> items = FXCollections.observableArrayList();

        try {
            Scanner sc = new Scanner(Paths.get(file));
            header = sc.nextLine().split(";");
            ArrayList learningUtilityNames = new ArrayList();
            while (sc.hasNext()) {
                String[] tokens = sc.nextLine().split(";");
                if (tokens.length != 12) {
                    throw new IllegalStateException("Het opgegeven bestand is verkeerd ingedeeld. Per regel moeten er exact 12 waarden zijn gescheiden door een \";\".");
                }
                if ((learningUtilityNames.contains(tokens[IX_Name_Column]))) {
                    throw new IllegalArgumentException("Het bestand bevat lijnen die geen unieke naam hebben");
                }
                if (!(tokens[IX_AmountInStock_Column].equals(""))) {
                    if (Integer.parseInt(tokens[IX_AmountInStock_Column]) < 0) {

                        throw new IllegalArgumentException("Het bestand bevat een lijn met een negatief aantal beschikbaar");
                    }
                }
                if (!(tokens[IX_AmountUnavailable_Column].equals(""))) {
                    if (Integer.parseInt(tokens[IX_AmountUnavailable_Column]) < 0) {

                        throw new IllegalArgumentException("Het bestand bevat een lijn met een negatief aantal onbeschikbaar");
                    }
                }
                if (!(tokens[IX_Price_Column].equals(""))) {
                    if (Integer.parseInt(tokens[IX_Price_Column]) < 0) {
                        throw new IllegalArgumentException("Het bestand bevat een lijn met een negatieve prijs");
                    }
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

    /**
     * To be defined, method not complete
     *
     * @param items
     */
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

    /**
     * This method creates a new {@link Company} instance. The method checks if
     * the strings are not empty. If all the parameters are correct the method
     * checks for duplicate names. If no duplicates are found a new instance is
     * created and the fields are set with the parameters provided. Finally the
     * new instance is added to the catalog for persistence.
     *
     * @param name The name of the company
     * @param website The website of the company
     * @param contactPerson The contactpersons name of the company
     * @param email The email address from the contactperson
     */
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

    /**
     * This method creates a new {@link Location} instance. The method checks if
     * the strings are not empty. If all the parameters are correct the method
     * checks for duplicate names. If no duplicates are found a new instance is
     * created and the fields are set with the parameters provided. Finally the
     * new instance is added to the catalog for persistence.
     *
     * @param name The location name
     */
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

    /**
     * This method creates a new {@link FieldOfStudy} instance. The method
     * checks if the strings are not empty. If all the parameters are correct
     * the method checks for duplicate names. If no duplicates are found a new
     * instance is created and the fields are set with the parameters provided.
     * Finally the new instance is added to the catalog for persistence.
     *
     * @param name The field of study name
     */
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

    /**
     * This method creates a new {@link TargetGroup} instance. The method checks
     * if the strings are not empty. If all the parameters are correct the
     * method checks for duplicate names. If no duplicates are found a new
     * instance is created and the fields are set with the parameters provided.
     * Finally the new instance is added to the catalog for persistence.
     *
     * @param name The targetgroup´s name
     */
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

    /**
     * This method adds an {@link IObserver} to the {@link ICatalog} of the
     * given type. The method checks to wich catalog the observer needs to be
     * added by the type parameter.
     *
     * @param observer the instance that implements the {@link IObserver}
     * interface
     * @param type the {@link Class} wich is hold by the {@link ICatalog}
     */
    public void addObserverToCatalog(IObserver observer, Class type) {
        switch (type.getSimpleName().toLowerCase()) {
            case "learningutility":
                learningUtilityCatalog.addObserver(observer);
                break;
            case "company":
                companyCatalog.addObserver(observer);
                break;
            case "fieldofstudy":
                fieldOfStudyCatalog.addObserver(observer);
                break;
            case "targetgroup":
                targetGroupCatalog.addObserver(observer);
                break;
            case "location":
                locationCatalog.addObserver(observer);
                break;
            case "user":
                userCatalog.addObserver(observer);
                break;
        }
    }

    /**
     *
     * @param o The observer that should be added to the catalog
     * @deprecated use the generic method
     * {@link #addObserverToCatalog(IObserver, Class) addObserverToCatalog(IObserver, Class)}
     * instead.
     */
    public void addCompanyObserver(IObserver o) {
        companyCatalog.addObserver(o);
    }

    /**
     *
     * @param o The observer that should be added to the catalog
     * @deprecated use the generic method
     * {@link #addObserverToCatalog(IObserver, Class) addObserverToCatalog(IObserver, Class)}
     * instead.
     */
    public void addFieldOfStudyObserver(IObserver o) {
        fieldOfStudyCatalog.addObserver(o);
    }

    /**
     *
     * @param o The observer that should be added to the catalog
     * @deprecated use the generic method
     * {@link #addObserverToCatalog(IObserver, Class) addObserverToCatalog(IObserver, Class)}
     * instead.
     */
    public void addTargetGroupObserver(IObserver o) {
        targetGroupCatalog.addObserver(o);
    }

    /**
     *
     * @param o The observer that should be added to the catalog
     * @deprecated use the generic method
     * {@link #addObserverToCatalog(IObserver, Class) addObserverToCatalog(IObserver, Class)}
     * instead.
     */
    public void addLocationObserver(IObserver o) {
        locationCatalog.addObserver(o);
    }

    /**
     * This method closes the connection with the database
     */
    public void closeConnection() {
        Connection.close();
    }

    /**
     * This method parses a line of the .csv file read by the
     * {@link #readCsvFile(String) readCsvFile(String)} to a
     * {@link LearningUtility} instance.
     *
     * @param tokens An array of strings containing the field values of the csv
     * line
     * @return a new instance of {@link LearningUtility}
     */
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

    /**
     * This method logs the user in. The method hashes the password to SHA-256.
     * Then the method sends a request to the HoGent rest login service,
     * provided the username and the hashed password. Then uses the JsonReader
     * to read the returned Json file. If login succeeded the application
     * continues, else an exception is thrown, indicating the login failed.
     *
     * @param username The username
     * @param password The password
     */
    public void login(String username, String password) {

        MessageDigest digest;
        byte[] hash;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            URL url = new URL("https://studservice.hogent.be/auth/" + username + "/" + String.format("%064x", new java.math.BigInteger(1, hash)));

            try (InputStream stream = url.openStream();
                    JsonReader r = Json.createReader(stream)) {

                JsonObject o = r.readObject();
                if (o.getString("TYPE").equals("personeel")) {
                    User user = userCatalog.getEntity(o.getString("EMAIL"));
                    if (user instanceof Manager) {
                        return;
                    }
                    throw new IllegalArgumentException("Aanmelden mislukt, probeert u het opnieuw.");
                }

                throw new IllegalArgumentException("Aanmelden mislukt, probeert u het opnieuw.");

            } catch (IOException ex) {
                throw new IllegalArgumentException("Aanmelden mislukt, probeert u het opnieuw.");
            }
        } catch (NoSuchAlgorithmException | MalformedURLException ex) {
            throw new IllegalArgumentException("Aanmelden mislukt, probeert u het opnieuw.");
        }
    }

    /**
     * This method filters the {@link #filteredLearningUtilityList}. The filter
     * is applied to the
     * {@link LearningUtility#name}, {@link LearningUtility#description} and
     * {@link LearningUtility#articleNumber} fields.
     *
     * @param filterValue The string on wich the list should be filtered
     */
    public void changeFilter(String filterValue) {
        filteredLearningUtilityList.setPredicate(learningUtility -> {
            // If filter text is empty, display all LearningUtilities.
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }
            // Compare the attributes of every learningUtility per with filter text.
            String lowerCaseFilter = filterValue.toLowerCase();
            if (learningUtility.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches name.
            } else if (learningUtility.getArticleNumber().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches articlenumber.
            } else if (learningUtility.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                return true; //Filter matches description 

            }

            return false; // Does not match.
        });
    }

    /**
     * This method is used for setting the catalogMocks. This method should be
     * used for testing purposes only.
     *
     * @param <E> The Class the {@link ICatalog} holds.
     * @param catalogMock The actual {@link ICatalog} mocking object
     */
    public <E> void setCatalog(ICatalog<E> catalogMock) {
        Class type = catalogMock.getType();
        switch (type.getSimpleName().toLowerCase()) {
            case "learningutility":
                this.learningUtilityCatalog = (IAdvancedCatalog<LearningUtility>) catalogMock;
                learningUtilityList = FXCollections.observableArrayList(learningUtilityCatalog.getEntities());
                filteredLearningUtilityList = new FilteredList<>(learningUtilityList, p -> true);
                break;
            case "company":
                this.companyCatalog = (IAdvancedCatalog<Company>) catalogMock;
                break;
            case "fieldofstudy":
                this.fieldOfStudyCatalog = (IAdvancedCatalog<FieldOfStudy>) catalogMock;
                break;
            case "targetgroup":
                this.targetGroupCatalog = (IAdvancedCatalog<TargetGroup>) catalogMock;
                break;
            case "location":
                this.locationCatalog = (IAdvancedCatalog<Location>) catalogMock;
        }
    }
}
