package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.StationDao;
import system.entity.Station;

import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of {@link StationDao} interface.
 */
@Slf4j
@Repository
public class StationDaoImpl extends JpaDao<Long, Station> implements StationDao {
    @Override
    public Station findByName(String stationName) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT s FROM Station s WHERE s.stationName = :stationname");
            q.setParameter("stationname", stationName);

            List results = q.getResultList();

            if (results.isEmpty()) {
                log.debug("Station {} is not founded", stationName);
                return null; // handle no-results case
            } else {
                return (Station) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by StationName Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by StationName Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by StationName Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Station> findAll() throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT s FROM Station s");
            return q.getResultList();
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find All Stations Failed: " + e.getMessage());
        }
    }
}
