package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.RoutDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;

import javax.persistence.Query;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            throw new DaoException(DaoException._SQL_ERROR, "Find All Routs Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Rout> findByStartStationAndEndStation(Station start, Station end) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM Rout r WHERE r.startStation = :station1 " +
                    "AND r.endStation = :station2");
            q.setParameter("station1", start);
            q.setParameter("station2", end);

            List<Rout> rout = (List<Rout>) q.getResultList();
            return rout;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Start and End stations Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Start and End stations Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Start and End stations Failed: " + e.getMessage());
        }
    }

    @Override
    public List<Rout> findByRoutSection(RoutSection routSection) throws DaoException {
        try {
            Query q = entityManager.createQuery("Select r FROM Rout r " +
                    "inner join fetch r.routSections rs WHERE rs = :routSection");
            q.setParameter("routSection", routSection);

            List<Rout> rout = (List<Rout>) q.getResultList();
            return rout;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Start and End stations Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Start and End stations Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Start and End stations Failed: " + e.getMessage());
        }
    }

    @Override
    public Rout findByName(String routName) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM Rout r WHERE r.routName = :routname");
            q.setParameter("routname", routName);

            Rout rout = (Rout) q.getSingleResult();
            return rout;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Rout by routName Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Rout by routName Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Rout by routName Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<RoutSection> getRoutSectionInRout(Rout rout) throws DaoException {
        try {
            Query q = entityManager.createQuery("Select rs FROM RoutSection rs " +
                    "inner join fetch rs.routs r WHERE r = :rout");

            q.setParameter("rout", rout);

            Set<RoutSection> routSections = new HashSet<RoutSection>(q.getResultList());
            return routSections;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout Failed: " + e.getMessage());
        }
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT rs FROM RoutSection rs " +
                    "inner join fetch rs.routs r WHERE r = :rout AND rs.departure = :departure");
            q.setParameter("rout", rout);
            q.setParameter("departure", departureStation);

            List results = q.getResultList();

            if (results.isEmpty()) {
                log.debug("RoutSection in rout {} with departure {} is not founded", rout.getRoutName(), departureStation);
                return null; // handle no-results case
            } else {
                return (RoutSection) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout And Departure Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout And Departure Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout And Departure Failed: " + e.getMessage());
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
                log.debug("RoutSection in rout {} with destination {} is not founded", rout.getRoutName(), destinationStation);
                return null; // handle no-results case
            } else {
                return (RoutSection) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout And Destination Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout And Destination Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout And Destination Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<RoutSection> getRoutSectionsInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination) throws DaoException {
        try {
            Station endStation = new Station();
            Set<RoutSection> routSections = new HashSet<>();
            while (endStation.getId() != destination.getId()) {
                Query q = entityManager.createQuery("SELECT rs FROM RoutSection rs " +
                        "inner join fetch rs.routs r WHERE r = :rout AND rs.departure = :departure");

                q.setParameter("rout", rout);
                q.setParameter("departure", departure);

                RoutSection routSection = (RoutSection) q.getSingleResult();
                routSections.add(routSection);

                departure = routSection.getDestination();
                endStation = departure;
            }

            return routSections;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Rout Sections by Rout, Departure And Destination Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Rout Sections by Rout, Departure And Destination Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Rout Sections by Rout, Departure And Destination Failed: " + e.getMessage());
        }
    }
}
