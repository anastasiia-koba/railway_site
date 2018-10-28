package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.StationDao;
import system.entity.Station;

import javax.persistence.Query;
import java.util.List;

/**
 *Implementation of {@link StationDao} interface.
 */
@Repository
public class StationDaoImpl extends JpaDao<Long, Station> implements StationDao {
    @Override
    public Station findByName(String stationName) {
        Query q = entityManager.createQuery("SELECT s FROM Station s WHERE s.stationName = :stationname");
        q.setParameter("stationname", stationName);

        Station station = (Station) q.getSingleResult();
        return station;
    }

    @Override
    public List<Station> findAll() {
        Query q = entityManager.createQuery("SELECT s FROM Station s");
        return q.getResultList();
    }
}
