package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.FinalRoutDao;
import system.entity.FinalRout;

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
        Query q = entityManager.createQuery("Select r FROM FinalRout r WHERE r.date=:date");
        q.setParameter("date", date);

        Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
        return finalRouts;
    }

    @Override
    public Set<FinalRout> findAll() {
        Query q = entityManager.createQuery("Select r FROM FinalRout r");

        Set<FinalRout> finalRouts = new HashSet<>(q.getResultList());
        return finalRouts;
    }
}
