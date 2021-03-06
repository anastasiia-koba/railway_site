package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.Dao;

import javax.persistence.*;
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

    @Override
    public void create(E entity) throws DaoException {
        try {
            entityManager.persist(entity);
        } catch (EntityExistsException e) {
            throw new DaoException(DaoException.FAIL_TO_INSERT, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.FAIL_TO_INSERT, e.getMessage());
        } catch (TransactionRequiredException e) {
            throw new DaoException(DaoException.FAIL_TO_INSERT, e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.FAIL_TO_INSERT, e.getMessage());
        }
    }

    @Override
    public void remove(E entity) throws DaoException {
        try {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.FAIL_TO_DELETE, e.getMessage());
        } catch (TransactionRequiredException e) {
            throw new DaoException(DaoException.FAIL_TO_DELETE, e.getMessage());
        }
    }

    @Override
    public void update(E entity) throws DaoException {
        try {
            entityManager.merge(entity);
        } catch (TransactionRequiredException e) {
            throw new DaoException(DaoException.UPDATE_FAILED, e.getMessage());
        } catch (PersistenceException e) {
            throw new DaoException(DaoException.UPDATE_FAILED, e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.UPDATE_FAILED, e.getMessage());
        }
    }

    @Override
    public E findById(K id) throws DaoException {
        try {
            return entityManager.find(entityClass, id);
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, e.getMessage());
        }
    }
}
