/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.catalogs;

import domain.interfaces.ICatalog;
import domain.interfaces.IObserver;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import persistence.GenericDaoJpa;
import persistence.IGenericDao;

/**
 *
 * @author Ward Vanlerberghe
 */
public class Catalog<T> implements ICatalog<T>{
    
    protected ObservableList<T> entities;
    protected FilteredList<T> filteredData;
    protected IGenericDao<T> repository;
    protected final Class<T> type;
    protected List<IObserver> observers;
    
    public Catalog(Class<T> type){
        this.type = type;
        this.repository = new GenericDaoJpa<>(type);
        this.observers = new ArrayList<>();
    }
    
    public Catalog(IGenericDao<T> mock, Class<T> type){
        this.type = type;
        this.repository = mock;
    }
    
    @Override
    public void addEntity(T entity){
        loadEntities();
        GenericDaoJpa.startTransaction();
        repository.insert(entity);
        GenericDaoJpa.commitTransaction();
        this.entities.add(entity);
        notifyObservers();
    }
    
    @Override
    public <E> T getEntity(E id){
        loadEntities();
        return entities.stream().filter(entity -> entity.equals(id)).findFirst().get();
    }
    @Override
    public FilteredList<T> getEntities(){
        loadEntities();
        return filteredData;
    }
    
    protected void loadEntities() {
        if(entities == null){
            entities = FXCollections.observableList(repository.findAll());
            filteredData = new FilteredList<>(entities, p -> true);
        }
    }
    
    @Override
    public Class getType(){
        return type;
    }
    
    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void updateEntity(T entity){
        loadEntities();
        GenericDaoJpa.startTransaction();
        repository.update(entity);
        GenericDaoJpa.commitTransaction();
        notifyObservers();
    }

    @Override
    public void deleteEntity(T entity) {
        loadEntities();
        GenericDaoJpa.startTransaction();
        repository.delete(entity);        
        GenericDaoJpa.commitTransaction();
        this.entities.remove(entity);
        notifyObservers();
    }
    
}
