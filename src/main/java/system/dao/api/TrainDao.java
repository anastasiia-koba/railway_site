package system.dao.api;

import system.entity.Train;

import java.util.List;

public interface TrainDao extends Dao<Long, Train> {
    Train findByName(String trainName);

    List<Train> findAll();
}
