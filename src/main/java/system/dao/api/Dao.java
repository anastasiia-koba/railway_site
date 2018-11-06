package system.dao.api;

import system.DaoException;

public interface Dao<K, E> {
    void create(E entity) throws DaoException;
    void remove(E entity) throws DaoException;
    void update(E entity) throws DaoException;
    E findById(K id) throws DaoException;
}
