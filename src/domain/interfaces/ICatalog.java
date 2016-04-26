/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import java.util.List;

/**
 *
 * @author Benjamin Vertonghen
 */
public interface ICatalog<T> {
    public void addEntity(T entity);
    public <E> T getEntity(E id);
    public List<T> getEntities();
}
