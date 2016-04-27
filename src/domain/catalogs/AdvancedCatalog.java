/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.catalogs;

import domain.interfaces.IAdvancedCatalog;
import domain.interfaces.ISearchableByName;
import java.util.List;

/**
 *
 * @author Append
 * @param <T>
 */

public class AdvancedCatalog<T extends ISearchableByName> extends Catalog<T> implements IAdvancedCatalog<T> {

    public AdvancedCatalog(Class <T> type )
    {
        super(type);
    }
    
    @Override
    public T getByName(String name) {
        loadEntities();
        return entities.stream().filter(entity -> entity.getName().contains(name)).findFirst().get();    }
    
    
}
