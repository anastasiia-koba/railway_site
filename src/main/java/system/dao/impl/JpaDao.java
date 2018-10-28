package system.dao.impl;

import org.hibernate.HibernateException;
import system.dao.api.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

public abstract class JpaDao<K, E> implements Dao<K, E> {
    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public JpaDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public void create(E entity) { entityManager.persist(entity); }

    @Override
    public void remove(E entity) { entityManager.remove(entity); }

    @Override
    public E findById(K id) { return entityManager.find(entityClass, id); }
}
