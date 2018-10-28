package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.TrainDao;
import system.entity.Train;

import javax.persistence.Query;
import java.util.List;

/**
 *Implementation of {@link TrainDao} interface.
 */
@Repository
public class TrainDaoImpl extends JpaDao<Long, Train> implements TrainDao {
    @Override
    public Train findByName(String trainName) {
        Query q = entityManager.createQuery("SELECT t FROM Train t WHERE t.trainName = :trainname");
        q.setParameter("trainname", trainName);

        Train train = (Train) q.getSingleResult();
        return train;
    }

    @Override
    public List<Train> findAll() {
        Query q = entityManager.createQuery("SELECT t FROM Train t");
        return q.getResultList();
    }
}
