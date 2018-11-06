package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.StationDao;
import system.entity.Station;

import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of {@link StationDao} interface.
 */
@Repository
public class StationDaoImpl extends JpaDao<Long, Station> implements StationDao {
    @Override
    public Station findByName(String stationName) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT s FROM Station s WHERE s.stationName = :stationname");
            q.setParameter("stationname", stationName);

            Station station = (Station) q.getSingleResult();
            return station;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by StationName Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by StationName Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by StationName Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Station> findAll() throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT s FROM Station s");
            return q.getResultList();
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find All Stations Failed: " + e.getMessage());
        }
    }
}
