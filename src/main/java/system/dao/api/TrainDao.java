package system.dao.api;

import system.DaoException;
import system.entity.Train;

import java.util.List;

public interface TrainDao extends Dao<Long, Train> {
    Train findByName(String trainName) throws DaoException;

    List<Train> findAll() throws DaoException;
}
