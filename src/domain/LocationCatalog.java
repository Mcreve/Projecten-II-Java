/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
import domain.learningUtility.Location;

/**
 *
 * @author Append
 */
public class LocationCatalog extends Catalog<Location> implements ILocationCatalog  {
    public LocationCatalog() 
    {
        super(Location.class);
    }

    @Override
    public Location getByName(String name) {
        loadEntities();
        return entities.stream().filter(entity -> entity.getName().contains(name)).findFirst().get();
    }
}
