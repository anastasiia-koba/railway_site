package system.service.api;

import system.entity.Train;

import java.util.List;

/**
 * Service class for {@link system.entity.Train}
 */
public interface TrainService {
    void save(Train train);
    void delete(Long trainId);
    Train findById(Long id);
    Train findByName(String trainName);
    List<Train> findAll();
}
