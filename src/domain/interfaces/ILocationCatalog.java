/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;
import domain.learningUtility.Location;

/**
 *
 * @author Append
 */
public interface ILocationCatalog extends ICatalog<Location> 
{
    public Location getByName(String name);

}
