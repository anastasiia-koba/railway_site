package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.RoutSectionDao;
import system.entity.RoutSection;
import system.entity.Station;

import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of {@link RoutSectionDao} interface.
 */
@Repository
public class RoutSectionDaoImpl extends JpaDao<Long, RoutSection> implements RoutSectionDao {
    @Override
    public List<RoutSection> findByDeparture(Station departure) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM RoutSection r WHERE r.departure = :station");
            q.setParameter("station", departure);

            return (List<RoutSection>) q.getResultList();
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Departure Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Departure Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Departure Failed: " + e.getMessage());
        }
    }

    @Override
    public List<RoutSection> findByDestination(Station destination) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM RoutSection r WHERE r.destination = :station");
            q.setParameter("station", destination);

            return (List<RoutSection>) q.getResultList();
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Destination Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Destination Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Destination Failed: " + e.getMessage());
        }
    }

    @Override
    public List<RoutSection> findByDepartureAndDestination(Station departure, Station destination) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM RoutSection r WHERE r.departure = :station1 " +
                    "AND r.destination = :station2");
            q.setParameter("station1", departure);
            q.setParameter("station2", destination);

            return (List<RoutSection>) q.getResultList();
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Departure and Destination Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Departure and Destination Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Departure and Destination Failed: " + e.getMessage());
        }
    }
}
