package system.service.api;

import system.entity.Train;

import java.util.List;

/**
 * Service class for {@link system.entity.Train}
 */
public interface TrainService {
    void save(Train train);

    Train findByTrainName(String trainName);

    List<Train> findAll();
}
