/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.catalogs;
import domain.interfaces.IFieldOfStudyCatalog;
import domain.learningUtility.FieldOfStudy;

/**
 *
 * @author Append
 */
public class FieldOfStudyCatalog extends Catalog<FieldOfStudy> implements IFieldOfStudyCatalog
{
    public FieldOfStudyCatalog() {
        super(FieldOfStudy.class);
    }

    @Override
    public FieldOfStudy getByName(String name) {
        loadEntities();
        return entities.stream().filter(entity -> entity.getName().contains(name)).findFirst().get();
    }

    
}
