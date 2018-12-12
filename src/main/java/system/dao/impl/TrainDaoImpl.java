package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.TrainDao;
import system.entity.Train;

import javax.persistence.Query;
import java.util.List;

/**
 *Implementation of {@link TrainDao} interface.
 */
@Slf4j
@Repository
public class TrainDaoImpl extends JpaDao<Long, Train> implements TrainDao {
    @Override
    public Train findByName(String trainName) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Train t WHERE t.trainName = :trainname");
            q.setParameter("trainname", trainName);

            List results = q.getResultList();
            if (results.isEmpty()) {
                log.info("Train {} is not founded", trainName);
                return null; // handle no-results case
            } else {
                return (Train) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Train by Name Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Train by Name Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Train by Name Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Train> findAll() throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Train t");
            return q.getResultList();
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find All Trains Failed: " + e.getMessage());
        }
    }
}
