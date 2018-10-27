package system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import system.entity.Train;

import java.util.List;

public interface TrainDao extends JpaRepository<Train, Long> {
    Train findByTrainName(String trainName);

    List<Train> findAll();
}
