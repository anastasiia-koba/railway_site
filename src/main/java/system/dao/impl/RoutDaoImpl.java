package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.RoutDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link Rout} interface.
 */
@Repository
public class RoutDaoImpl extends JpaDao<Long, Rout> implements RoutDao {
    @Override
    public List<Rout> findByStartStationAndEndStation(Station start, Station end) {
        Query q = entityManager.createQuery("SELECT r FROM Rout r WHERE r.startStation = :station1 " +
                "AND r.endStation = :station2");
        q.setParameter("station1", start);
        q.setParameter("station2", end);

        List<Rout> rout = (List<Rout>) q.getResultList();
        return rout;
    }

    @Override
    public Set<RoutSection> getRoutSectionInRout(Rout rout) {
        Query q = entityManager.createQuery("Select rs FROM RoutSection rs " +
                    "inner join fetch rs.routs r WHERE r=:rout");

        q.setParameter("rout", rout);

        Set<RoutSection> routSections = new HashSet<RoutSection>(q.getResultList());
        return routSections;
    }
}
