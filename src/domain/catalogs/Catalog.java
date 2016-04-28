/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.catalogs;

import domain.interfaces.ICatalog;
import java.util.List;
import java.util.Observable;
import persistence.GenericDaoJpa;
import persistence.IGenericDao;

/**
 *
 * @author Ward Vanlerberghe
 */
public class Catalog<T> extends Observable implements ICatalog<T>{
    
    protected List<T> entities;
    protected IGenericDao<T> repository;
    protected final Class<T> type;
    
    public Catalog(Class<T> type){
        this.type = type;
        this.repository = new GenericDaoJpa<>(type);
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
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    @Override
    public <E> T getEntity(E id){
        loadEntities();
        return entities.stream().filter(entity -> entity.equals(id)).findAny().get();
    }
    @Override
    public List<T> getEntities(){
        loadEntities();
        return entities;
    }
    
    
    protected void loadEntities() {
        if(entities == null)
            entities = repository.findAll();
    }
    
}
