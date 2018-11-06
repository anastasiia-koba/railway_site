package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.FinalRoutDao;
import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;

import javax.persistence.Query;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link system.entity.FinalRout} interface.
 */
@Slf4j
@Repository
public class FinalRoutDaoImpl extends JpaDao<Long, FinalRout> implements FinalRoutDao {
    @Override
    public Set<FinalRout> findByDate(Date date) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM FinalRout r WHERE r.date = :date");
            q.setParameter("date", date);

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<FinalRout> findAll() throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM FinalRout r");

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<FinalRout> findByStationAndDate(Station station, Date date) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT fr FROM FinalRout fr " +
                    "JOIN fr.rout.routSections rs WHERE " +
                    "(rs.destination = :station OR rs.departure = :station) AND " +
                    "fr.date = :date");
            q.setParameter("station", station);
            q.setParameter("date", date);

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Station and Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Station and Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Station and Date Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<FinalRout> findByStationToStationOnDate(Station start, Station end, Date date) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT fr FROM FinalRout fr " +
                    "JOIN fr.rout.routSections rs WHERE " +
                    "rs.departure = :start AND rs.destination = :end AND " +
                    "fr.date = :date");
            q.setParameter("start", start);
            q.setParameter("end", end);
            q.setParameter("date", date);

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Two Stations and Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Two Stations and Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Two Stations and Date Failed: " + e.getMessage());
        }
    }

    @Override
    public FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, Date date) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM FinalRout r WHERE r.rout = :rout " +
                    "AND r.train = :train AND r.date = :date");
            q.setParameter("rout", rout);
            q.setParameter("train", train);
            q.setParameter("date", date);

            List results = q.getResultList();

            if (results.isEmpty()) {
                log.debug("Train {} on date {} is not founded", train.getTrainName(), date);
                return null; // handle no-results case
            } else {
                return (FinalRout) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout, Train and Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout, Train and Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Rout, Train and Date Failed: " + e.getMessage());
        }
    }
}
