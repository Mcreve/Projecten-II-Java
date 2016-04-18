/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.Company;
import domain.FieldOfStudy;
import domain.LearningUtility;
import domain.Location;
import domain.TargetGroup;
import java.util.List;

/**
 *
 * @author Ward Vanlerberghe
 */
public class PersistenceController {
    
    private static PersistenceController instance;
    private LearningUtilityRepository learningUtilityRepository;
    private LocationRepository locationRepository;
    private UserRepository userRepository;
    private FieldOfStudyRepository fieldOfStudyRepository;
    private TargetGroupRepository targetGroupRepository;
    private CompanyRepository companyRepository;
    
    private PersistenceController(){
        instance = this;
    }
    
    public static PersistenceController getInstance(){
        if(instance != null){
            return instance;
        }
        return new PersistenceController();
    }
    
    public List<LearningUtility> findAllLearningUtilities (){
        loadLearningUtilityRepository();
        return learningUtilityRepository.findAll();
    }
    
    public LearningUtility findLearningUtilityById(int id){
        loadLearningUtilityRepository();
        return learningUtilityRepository.findBy(id);
    }
    
    public void AddLearningUtility(LearningUtility learningUtility){
        loadLearningUtilityRepository();
        learningUtilityRepository.add(learningUtility);
    }
    
    public List<Location> findAllLocations(){
        loadLocationRepository();
        return locationRepository.findAll();
    }
    
    public Location findLocationById(int id){
        loadLocationRepository();
        return locationRepository.findBy(id);
    }
    
    public void AddLocation(Location location){
        loadLocationRepository();
        locationRepository.add(location);
    }
    
    public List<FieldOfStudy> findAllFieldsOfStudy(){
        loadFieldOfStudyRepository();
        return fieldOfStudyRepository.findAll();
    }
    
    public FieldOfStudy findFieldOfStudyById(int id){
        loadFieldOfStudyRepository();
        return fieldOfStudyRepository.findBy(id);
    }
    
    public void AddFieldOfStudy(FieldOfStudy fieldOfStudy){
        loadFieldOfStudyRepository();
        fieldOfStudyRepository.add(fieldOfStudy);
    }
    
    public List<TargetGroup> findAllTargetGroups(){
        loadTargetGroupRepository();
        return targetGroupRepository.findAll();
    }
    
    public TargetGroup findTargetGroupById(int id){
        loadTargetGroupRepository();
        return targetGroupRepository.findBy(id);
    }
    
    public void AddTargetGroup(TargetGroup targetGroup){
        loadTargetGroupRepository();
        targetGroupRepository.add(targetGroup);
    }
    
    public List<Company> findAllCompanies(){
        loadCompanyRepository();
        return companyRepository.findAll();
    }
    
    public Company findCompanyById(int id){
        loadCompanyRepository();
        return companyRepository.findBy(id);
    }
    
    public void addCompany(Company company){
        loadCompanyRepository();
        companyRepository.add(company);
    }

    private void loadCompanyRepository() {
        if(companyRepository == null)
            companyRepository = CompanyRepository.getInstance();
    }

    private void loadTargetGroupRepository() {
        if(targetGroupRepository == null){
            targetGroupRepository = TargetGroupRepository.getInstance();
        }
    }

    private void loadFieldOfStudyRepository() {
        if(fieldOfStudyRepository == null){
            fieldOfStudyRepository = FieldOfStudyRepository.getInstance();
        }
    }

    private void loadLocationRepository() {
        if(locationRepository == null){
            locationRepository = LocationRepository.getInstance();
        }
    }

    private void loadLearningUtilityRepository() {
        if(learningUtilityRepository == null){
            learningUtilityRepository = LearningUtilityRepository.getInstance();
        }
    }
    
}
