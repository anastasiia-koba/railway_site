package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.FinalRoutDao;
import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;

import javax.persistence.Query;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link system.entity.FinalRout} interface.
 */
@Repository
public class FinalRoutDaoImpl extends JpaDao<Long, FinalRout> implements FinalRoutDao {
    @Override
    public Set<FinalRout> findByDate(Date date) {
        Query q = entityManager.createQuery("SELECT r FROM FinalRout r WHERE r.date = :date");
        q.setParameter("date", date);

        Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
        return finalRouts;
    }

    @Override
    public Set<FinalRout> findAll() {
        Query q = entityManager.createQuery("SELECT r FROM FinalRout r");

        Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
        return finalRouts;
    }

    @Override
    public Set<FinalRout> findByStationAndDate(Station station, Date date) {
        try {
            Query q = entityManager.createQuery("SELECT fr FROM FinalRout fr " +
                    "JOIN fr.rout.routSections rs WHERE " +
                    "(rs.destination = :station OR rs.departure = :station) AND " +
                    "fr.date = :date");
            q.setParameter("station", station);
            q.setParameter("date", date);

            Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
            return finalRouts;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, Date date) {
        Query q = entityManager.createQuery("SELECT r FROM FinalRout r WHERE r.rout = :rout " +
                "AND r.train = :train AND r.date = :date");
        q.setParameter("rout", rout);
        q.setParameter("train", train);
        q.setParameter("date", date);

        return (FinalRout) q.getSingleResult();
    }
}
