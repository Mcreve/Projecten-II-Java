/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.Catalogs;

import domain.Interfaces.ICatalog;
import java.util.List;
import persistence.GenericDaoJpa;
import persistence.IGenericDao;

/**
 *
 * @author Ward Vanlerberghe
 */
public class Catalog<T> implements ICatalog<T>{
    
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
        this.entities.add(entity);
        GenericDaoJpa.startTransaction();
        repository.insert(entity);
        GenericDaoJpa.commitTransaction();
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
