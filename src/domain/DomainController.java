/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.catalogs.*;
import domain.interfaces.ICatalog;
import domain.interfaces.IObservable;
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
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
public class DomainController implements IObservable {

    private ICatalog<LearningUtility> learningUtilityCatalog;
    private ICatalog<Company> companyCatalog;
    private ICatalog<FieldOfStudy> fieldOfStudyCatalog;
    private ICatalog<TargetGroup> targetGroupCatalog;
    private ICatalog<Location> locationCatalog;
    private ICatalog<User> userCatalog;
    private ICatalog<Reservation> reservationCatalog;

    private List<IObserver> observers;

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
    private Reservation currentReservation;
    private User currentUser;
    private User currentUserAdminPanel;
    
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
        learningUtilityCatalog = new Catalog<>(LearningUtility.class);
        companyCatalog = new Catalog<>(Company.class);
        fieldOfStudyCatalog = new Catalog<>(FieldOfStudy.class);
        targetGroupCatalog = new Catalog<>(TargetGroup.class);
        locationCatalog = new Catalog<>(Location.class);
        userCatalog = new Catalog<>(User.class);
        reservationCatalog = new Catalog<>(Reservation.class);
        observers = new ArrayList<>();
        
        //learningUtilityList = FXCollections.observableArrayList(learningUtilityCatalog.getEntities());
        //filteredLearningUtilityList = new FilteredList<>(learningUtilityList, p -> true);
    }

    /**
     * Returns a list with all the reservations
     *
     * @return A list with all the reservations
     */
    private FilteredList<Reservation> getReservations() {
        return reservationCatalog.getEntities();
    }
    
     private FilteredList<User> getUsers() {
        return userCatalog.getEntities();
    }
    
    /**
     * Creates an {@link ObservableMap} with {@link User} as key and a 
     * {@link List} of {@link Reservation} instances as value 
     * @return the {@link ObservableMap} with {@link User} as key and a 
     * {@link List} of {@link Reservation} instances as value
     */
    private ObservableMap<User, List<Reservation>> getReservationsByUser(){
        return FXCollections.observableMap(getReservations()
                .stream()
                .collect(Collectors.groupingBy(Reservation::getUserEmailAddress))
        );
    }
    
    /**
     * Returns a {@link FilteredList} with all the {@link User users} that have a
     * {@link Reservation}
     * @return a {@link FilteredList} with all the {@link User users} that have a
     * {@link Reservation}
     */
    public FilteredList<User> getUsersWithReservations(){
        return new FilteredList<>(FXCollections.observableList(getReservationsByUser().keySet().stream().collect(Collectors.toList())).sorted());
    }
    
    
    public FilteredList<User> getAdmins(){
        return new FilteredList<>(FXCollections.observableList(getUsers().stream().collect(Collectors.toList())));
       //Juiste code hieronder
//return new FilteredList<>(FXCollections.observableList(getUsers().stream().filter(u -> u instanceof Manager).collect(Collectors.toList())));
        
    }
    
    /**
     * Finds all {@link Reservation reservations} from the {@link User} currently marked as selected and returns them as a {@link FilteredList}
     * @return a {@link FilteredList} with all {@link Reservation reservations} from the {@link User} currently marked as selected
     */
    public FilteredList<Reservation> getReservationsFromUser(){
        return new FilteredList<>(FXCollections.observableList(getReservationsByUser().get(currentUser)));
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
    public void setFieldsOfStudy(ICatalog<FieldOfStudy> fieldOfStudyCatalog) {
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
    public void setTargetGroups(ICatalog<TargetGroup> targetGroupCatalog) {
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
    public void setLocations(ICatalog<Location> locationCatalog) {
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
    public void setCompanies(ICatalog<Company> companyCatalog) {
        this.companyCatalog = companyCatalog;
    }

    /**
     * Returns a list with all known {@link LearningUtility} instances.
     *
     * @return a list with all known {@link LearningUtility} instances.
     */
    public FilteredList<LearningUtility> getLearningUtilities() {
        return learningUtilityCatalog.getEntities();
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
     * Gets the {@link Reservation} that is currently marked as selected
     *
     * @return the {@link Reservation} that is currently marked as selected
     */
    public Reservation getCurrentReservation() {
        return currentReservation;
    }
    public User getCurrentUser(){
        return this.currentUser;
    }
    public User getCurrentUserAdminPanel(){
        return this.currentUserAdminPanel;
    }
    public boolean userIsSet(){
        return currentUser != null;
    }
    public boolean CurrentUserIsAdminPanelSet(){
        return currentUserAdminPanel != null;
    }
    

    /**
     * Gets the {@link LearningUtility} that is marked as selected
     *
     * @return
     */
    public LearningUtility getSelectedLearningUtility() {
        return this.selectedLearningUtility;
    }

    //Setters
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
     * Sets the {@link Reservation} as selected
     *
     * @param reservation the {@link Reservation} that needs to be marked as
     * selected
     */
    public void setCurrentReservation(Reservation reservation) {
        this.currentReservation = reservation;
        notifyObservers();
    }
    
    /**
     * Sets the {@link User} that should be marked as selected
     * @param user the {@link User} that should be marked as selected
     */
    public void setCurrentUser(User user){
        this.currentUser = user;
        notifyObservers();
    }

    /**
     *
     * @param learningUtilityCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setUtilities(ICatalog<LearningUtility> learningUtilityCatalog) {
        this.learningUtilityCatalog = learningUtilityCatalog;
    }

    /**
     *
     * @param userCatalog
     * @deprecated please use the method {@link #setCatalog(ICatalog) }
     */
    public void setUsers(ICatalog<User> userCatalog) {
        this.userCatalog = userCatalog;
    }
    
    public User createAdmin(String email, String firstName, String LastName){
        Manager newManager = new Manager();
        newManager.setEmailAddress(email);
        newManager.setFirstName(firstName);
        newManager.setLastName(LastName);
        userCatalog.addEntity(newManager);
        return newManager;
    }
    public User makeAdmin(User user){
        
        if(user instanceof Lector){
        userCatalog.deleteEntity(user);
        user = new Manager(user);
        userCatalog.addEntity(user);
        }
        if(user instanceof Manager){
            userCatalog.updateEntity(user);
        }
        notifyObservers();
        return user;
        
    }
    public User removeAdmin(User user){
        
        userCatalog.deleteEntity(user);
        Lector downGradedUser = new Lector();
        downGradedUser.setEmailAddress(user.getEmailAddress());
        downGradedUser.setFirstName(user.getFirstName());
        downGradedUser.setLastName(user.getLastName());
        userCatalog.addEntity(downGradedUser);
        return downGradedUser;
        
        
}
    public User deleteAdmin(User user){
        userCatalog.deleteEntity(user);
        return null;
        
    }

    
    /**
     * This method edits the reservation marked as currently selected with the      {@link #setCurrentReservation(domain.learningUtility.Reservation) 
     * setCurrentReservation(Reservation)} with the parameters provided
     *
     * @param daysBlocked The days that the {@link LearningUtility} is blocked
     * @param returnDate The date the {@link LearningUtility} should be returned
     * @param amount The amount of items that are reserved for this reservation
     */
    public void editReservation(String daysBlocked, Date returnDate, int amount) {
        currentReservation.setDateWanted(returnDate);
        currentReservation.setAmount(amount);
        currentReservation.setDaysBlocked(daysBlocked);
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

        checkAmountInStock(amountInstock);
        checkName(name);

        LearningUtility newItem = createLearningUtility(name, description, price, loanable, articleNumber, image, locationName, amountInstock, amountUnavailable, companyName, targetGroups, fieldsOfStudy);

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

        String currentName = learningUtility.getName();
        if (!currentName.equals(name)) {
            checkName(name);
        }
        learningUtility.setName(name);
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
        learningUtility.setCompanyId(companyCatalog.getEntity(companyName));
        learningUtility.setLocationId(locationCatalog.getEntity(locationName));
        List<TargetGroup> targetGroupsList = new ArrayList<>();

        for (String targetGroupName : targetGroups) {

            targetGroupsList.add(targetGroupCatalog.getEntity(targetGroupName));

        }
        learningUtility.setTargetGroupList(targetGroupsList);

        List<FieldOfStudy> fieldOfStudyList = new ArrayList<>();

        for (String fieldOfStudyName : fieldsOfStudy) {

            fieldOfStudyList.add(fieldOfStudyCatalog.getEntity(fieldOfStudyName));

        }
        learningUtility.setFieldOfStudyList(fieldOfStudyList);
        learningUtilityCatalog.updateEntity(learningUtility);
    }

    /**
     * Adds the learningutility to the catalog and persistance layer after
     * checks
     *
     * @param lu The {@link LearningUtility} that needs to be added
     * @author Benjamin Vertonghen
     */
    public void registerLearningUtilityFromImport(LearningUtility lu) {
        checkAmountInStock(lu.getAmountInCatalog());
        checkName(lu.getName());
        learningUtilityCatalog.addEntity(lu);
    }

    /**
     * Method checks if the amount in stock is greater than 0
     *
     * @param amountInStock Quantity
     * @author Benjamin Vertonghen
     */
    private void checkAmountInStock(int amountInstock) throws IllegalArgumentException {
        if (amountInstock < 1) {
            throw new IllegalArgumentException("Het Aantal in stock moet meer zijn dan één.");
        }
    }

    /**
     * Method checks if the name of the learningutility is filled in and if it
     * already exists
     *
     * @param name name of the utility
     * @author Benjamin Vertonghen
     */
    private void checkName(String name) throws IllegalArgumentException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Gelieve een naam voor het leermiddel op te geven.");
        }
        if (learningUtilityCatalog.getEntities().stream().anyMatch(l -> l.getName().equals(name))) {
            throw new IllegalArgumentException("Er bestaat al een leermiddel met de opgegeven naam: " + name + ".");
        }
    }

    public void removeLearningUtility(LearningUtility learningUtility) {
        learningUtilityCatalog.deleteEntity(learningUtility);
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

        targetGroupsList = Arrays.asList(tokens[IX_TargetGroups_Column].split(","));
        fieldsOfStudyList = Arrays.asList(tokens[IX_FieldsOfStudy_Column].split(","));

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

        newItem.setCompanyId(companyCatalog.getEntity(companyName));

        if (newItem.getCompanyId() == null) {
            newItem.setCompanyId(createCompany(companyName, "Onbekend", "Onbekend", "Onbekend"));
        }

        newItem.setLocationId(locationCatalog.getEntity(locationName));

        if (newItem.getLocationId() == null) {
            newItem.setLocationId(createLocation(locationName));
        }

        List<TargetGroup> targetGroupsList = new ArrayList<>();

        for (String targetGroupName : targetGroups) {
            TargetGroup targetGroup = targetGroupCatalog.getEntity(targetGroupName);

            if (targetGroup == null) {
                targetGroup = createTargetGroup(targetGroupName);
            }
            targetGroupsList.add(targetGroup);

        }
        newItem.setTargetGroupList(targetGroupsList);

        List<FieldOfStudy> fieldOfStudyList = new ArrayList<>();

        for (String fieldOfStudyName : fieldsOfStudy) {

            FieldOfStudy fieldOfStudy = fieldOfStudyCatalog.getEntity(fieldOfStudyName);

            if (fieldOfStudy == null) {
                fieldOfStudy = createFieldOfStudy(fieldOfStudyName);
            }
            fieldOfStudyList.add(fieldOfStudy);
        }
        newItem.setFieldOfStudyList(fieldOfStudyList);
        return newItem;
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
    public Company createCompany(String name, String website, String contactPerson, String email) {

        if (name.isEmpty() || website.isEmpty() || contactPerson.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Alle velden moeten ingevuld worden.");
        }

        Company company = companyCatalog.getEntity(name);

        if (company != null) {
            throw new IllegalArgumentException("Opgegeven bedrijf met deze naam bestaat reeds.");
        }

        company = new Company();
        company.setName(name);
        company.setWebsite(website);
        company.setContactPersonName(contactPerson);
        company.setEmailAddress(email);
        companyCatalog.addEntity(company);

        return company;

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
    public Location createLocation(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Gelieve een naam op te geven voor de nieuwe locatie");
        }

        Location loc = locationCatalog.getEntity(name);

        if (loc != null) {
            throw new IllegalArgumentException("Er bestaat reeds een locatie met opgegeven naam");
        }

        loc = new Location();
        loc.setName(name);
        locationCatalog.addEntity(loc);

        return loc;

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
    public FieldOfStudy createFieldOfStudy(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Gelieve een naam op te geven voor het nieuwe leergebied.");
        }

        FieldOfStudy fieldOfStudy = fieldOfStudyCatalog.getEntity(name);

        if (fieldOfStudy != null) {
            throw new IllegalArgumentException("Er bestaat reeds een leergebied met opgegeven naam");
        }

        fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setName(name);

        fieldOfStudyCatalog.addEntity(fieldOfStudy);

        return fieldOfStudy;

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
    public TargetGroup createTargetGroup(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Gelieve een naam op te geven voor de nieuwe doelgroep.");
        }

        TargetGroup targetGroup = targetGroupCatalog.getEntity(name);

        if (targetGroup != null) {
            throw new IllegalArgumentException("Er bestaat reeds een doelgroep met opgegeven naam");
        }

        targetGroup = new TargetGroup();
        targetGroup.setName(name);

        targetGroupCatalog.addEntity(targetGroup);

        return targetGroup;

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
        filteredLearningUtilityList = getLearningUtilities();
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
                this.learningUtilityCatalog = (ICatalog<LearningUtility>) catalogMock;
                learningUtilityList = FXCollections.observableArrayList(learningUtilityCatalog.getEntities());
                filteredLearningUtilityList = new FilteredList<>(learningUtilityList, p -> true);
                break;
            case "company":
                this.companyCatalog = (ICatalog<Company>) catalogMock;
                break;
            case "fieldofstudy":
                this.fieldOfStudyCatalog = (ICatalog<FieldOfStudy>) catalogMock;
                break;
            case "targetgroup":
                this.targetGroupCatalog = (ICatalog<TargetGroup>) catalogMock;
                break;
            case "location":
                this.locationCatalog = (ICatalog<Location>) catalogMock;
                break;
            case "user":
                this.userCatalog = (ICatalog<User>) catalogMock;
        }
    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach(o -> o.update());
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    /**
     * This method closes the connection with the database
     */
    public void closeConnection() {
        Connection.close();
    }

    public void setCurrentUserAdminPanel(User user) {
       this.currentUserAdminPanel= user ;
       notifyObservers();
    }
}
