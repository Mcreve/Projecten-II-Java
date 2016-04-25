package persistence;

import java.util.List;
import javax.persistence.EntityManager;

public class GenericDaoJpa<T> implements IGenericDao<T> {
    public static final EntityManager em = Connection.entityManager();
    private final Class<T> type;
    
    public GenericDaoJpa(Class<T> type) {
        this.type = type;
    }
    public static void closePersistency() {
        Connection.close();
    }
    public static void startTransaction() {
        em.getTransaction().begin();
    }
    public static void commitTransaction() {
        em.getTransaction().commit();
    }
    public static void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("select entity from " + type.getName() + " entity", type).getResultList();
    }

    @Override
    public <E> T findBy(E id) {
        T entity = em.find(type, id);
        return entity;
    }

    @Override
    public T update(T object) {
        return em.merge(object);
    }

    @Override
    public void delete(T object) {
        em.remove(em.merge(object));
    }

    @Override
    public void insert(T object) {
        em.persist(object);
    }

    @Override
    public <E> boolean exists(E id) {
        T entity = em.find(type, id);
        return entity != null;
    } 
    
}
