/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import persistence.GenericDao;
import persistence.GenericDaoJpa;

/**
 *
 * @author Ward Vanlerberghe
 */
public class Catalog<T> {
    
    private List<T> entities;
    private GenericDao<T> repository;
    private final Class<T> type;
    
    public Catalog(Class<T> type){
        this.type = type;
        this.repository = new GenericDaoJpa<>(type);
    }
    
    public Catalog(GenericDao<T> mock, Class<T> type){
        this.type = type;
        this.repository = mock;
    }
    
    public void addEntity(T entity){
        loadEntities();
        this.entities.add(entity);
        GenericDaoJpa.startTransaction();
        repository.insert(entity);
        GenericDaoJpa.commitTransaction();
    }
    
    public <E> T getEntity(E id){
        loadEntities();
        return entities.stream().filter(entity -> entity.equals(id)).findAny().get();
    }
    
    public List<T> getEntities(){
        loadEntities();
        return entities;
    }

    private void loadEntities() {
        if(entities == null)
            entities = repository.findAll();
    }
    
}
