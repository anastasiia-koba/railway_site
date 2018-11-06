package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.TrainDao;
import system.entity.Train;

import javax.persistence.Query;
import java.util.List;

/**
 *Implementation of {@link TrainDao} interface.
 */
@Repository
public class TrainDaoImpl extends JpaDao<Long, Train> implements TrainDao {
    @Override
    public Train findByName(String trainName) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Train t WHERE t.trainName = :trainname");
            q.setParameter("trainname", trainName);

            Train train = (Train) q.getSingleResult();
            return train;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Train by Name Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Train by Name Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Train by Name Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Train> findAll() throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Train t");
            return q.getResultList();
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find All Trains Failed: " + e.getMessage());
        }
    }
}
