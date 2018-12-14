package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.RoutDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link Rout} interface.
 */
@Slf4j
@Repository
public class RoutDaoImpl extends JpaDao<Long, Rout> implements RoutDao {
    @Override
    public List<Rout> findAll() throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM Rout r");

            return q.getResultList();
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find All Routs Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Rout> findByStartStationAndEndStation(Station start, Station end) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM Rout r WHERE r.startStation = :station1 " +
                    "AND r.endStation = :station2");
            q.setParameter("station1", start);
            q.setParameter("station2", end);

            return (List<Rout>) q.getResultList();
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Start and End stations Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Start and End stations Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Start and End stations Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Rout> findByRoutSection(RoutSection routSection) throws DaoException {
        try {
            Query q = entityManager.createQuery("Select r FROM Rout r " +
                    "inner join fetch r.routSections rs WHERE rs = :routSection");
            q.setParameter("routSection", routSection);

            return (List<Rout>) q.getResultList();
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Route section Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Route section Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Route section Failed: " + e.getMessage());
        }
    }

    @Override
    public Rout findByName(String routName) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM Rout r WHERE r.routName = :routname");
            q.setParameter("routname", routName);

            List results = q.getResultList();
            if (results.isEmpty()) {
                log.info("Rout {} is not founded", routName);
                return null; // handle no-results case
            } else {
                return (Rout) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find Route by routName Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find Route by routName Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find Route by routName Failed: " + e.getMessage());
        }
    }

    @Override
    public List<RoutSection> getRoutSectionInRout(Rout rout) throws DaoException {
        try {
            Query q = entityManager.createQuery("Select s FROM RoutSection s inner join fetch s.routs r " +
                    "WHERE r = :rout");

            q.setParameter("rout", rout);

            return q.getResultList();
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Route Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Route Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Route Failed: " + e.getMessage());
        }
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT rs FROM RoutSection rs inner join fetch rs.routs r " +
                    "WHERE r = :rout AND rs.departure = :departure");
            q.setParameter("rout", rout);
            q.setParameter("departure", departureStation);

            List results = q.getResultList();

            if (results.isEmpty()) {
                log.info("RoutSection in rout {} with departure {} is not founded", rout.getRoutName(), departureStation);
                return null; // handle no-results case
            } else {
                return (RoutSection) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Route And Departure Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Route And Departure Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Route And Departure Failed: " + e.getMessage());
        }
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT rs FROM RoutSection rs " +
                    "inner join fetch rs.routs r WHERE r = :rout AND rs.destination = :destination");
            q.setParameter("rout", rout);
            q.setParameter("destination", destinationStation);

            List results = q.getResultList();

            if (results.isEmpty()) {
                log.info("RoutSection in rout {} with destination {} is not founded", rout.getRoutName(), destinationStation);
                return null; // handle no-results case
            } else {
                return (RoutSection) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Route And Destination Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Route And Destination Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Route And Destination Failed: " + e.getMessage());
        }
    }

    @Override
    public List<RoutSection> getRoutSectionsInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination) throws DaoException {
        try {
            Station endStation = new Station();
            List<RoutSection> routSections = new ArrayList<>();
            while (endStation.getId() != destination.getId()) {
                Query q = entityManager.createQuery("SELECT rs FROM RoutSection rs " +
                        "inner join fetch rs.routs r WHERE r = :rout AND rs.departure = :departure");

                q.setParameter("rout", rout);
                q.setParameter("departure", departure);


                List results = q.getResultList();
                if (results.isEmpty()) {
                    return Collections.emptyList(); // handle no-results case
                } else {
                    RoutSection routSection = (RoutSection) results.get(0);
                    routSections.add(routSection);
                    departure = routSection.getDestination();
                    endStation = departure;
                }
            }

            return routSections;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find Route Sections by Route, Departure And Destination Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find Route Sections by Route, Departure And Destination Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find Route Sections by Route, Departure And Destination Failed: " + e.getMessage());
        }
    }
}
