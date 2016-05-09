/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.catalogs;

import domain.interfaces.IAdvancedCatalog;
import domain.interfaces.ISearchableByName;
import java.util.NoSuchElementException;
import persistence.IGenericDao;

/**
 *  
 * @author Append
 * @param <T>
 * @deprecated same can be achieved with the {@link Catalog#getEntity(java.lang.Object) }
 * method. Make sure the entity has an overridden implementation of the {@link Object#equals(java.lang.Object) } method
 * that accepts a string as argument wich checks equality of the name.
 */
public class AdvancedCatalog<T extends ISearchableByName> extends Catalog<T> implements IAdvancedCatalog<T> {

    public AdvancedCatalog(Class<T> type) {
        super(type);
    }

    public AdvancedCatalog(IGenericDao<T> mock, Class<T> type) {

        super(mock, type);
    }

    @Override
    public T getByName(String name) {
        super.loadEntities();
        try {
            return entities.stream().filter(entity -> entity.getName().equals(name)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
