package persistence;

import java.util.List;

public interface IGenericDao<T> {
    public List<T> findAll();  
    public <E> T findBy(E id);
    public T update(T object);
    public void delete(T object);
    public void insert(T object);
    public <E> boolean exists(E id);
}