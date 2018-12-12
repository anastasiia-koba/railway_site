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
import java.time.LocalDate;
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
    public Set<FinalRout> findByDate(LocalDate date) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM FinalRout r WHERE r.date = :date");
            q.setParameter("date", date);

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<FinalRout> findAll() throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT r FROM FinalRout r");

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Date Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<FinalRout> findByStationAndDate(Station station, LocalDate date) throws DaoException {
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
            throw new DaoException(DaoException.SQL_ERROR, "Find by Station and Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Station and Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Station and Date Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<FinalRout> findByStationToStationOnDate(Station start, Station end, LocalDate date) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT fr1 FROM FinalRout fr1 " +
                    "JOIN fr1.rout.routSections rs1 WHERE " +
                    "rs1.departure = :start AND fr1.date = :date AND fr1 IN " +
                    "(SELECT fr2 FROM FinalRout fr2 JOIN fr2.rout.routSections rs2 WHERE " +
                    "rs2.destination = :end AND fr2.date = :date)");
            q.setParameter("start", start);
            q.setParameter("end", end);
            q.setParameter("date", date);

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Two Stations and Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Two Stations and Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Two Stations and Date Failed: " + e.getMessage());
        }
    }

    @Override
    public FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, LocalDate date) throws DaoException {
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
            throw new DaoException(DaoException.SQL_ERROR, "Find by Rout, Train and Date Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Rout, Train and Date Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Rout, Train and Date Failed: " + e.getMessage());
        }
    }
}
