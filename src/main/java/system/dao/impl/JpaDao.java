package system.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import system.dao.api.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

@Repository
public abstract class JpaDao<K, E> implements Dao<K, E> {
    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public JpaDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Transactional
    @Override
    public void create(E entity) { entityManager.persist(entity); }

    @Transactional
    @Override
    public void remove(E entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Transactional
    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public E findById(K id) { return entityManager.find(entityClass, id); }
}
