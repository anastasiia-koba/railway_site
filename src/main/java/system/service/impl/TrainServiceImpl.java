package system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.api.TrainDao;
import system.entity.Train;
import system.service.api.TrainService;

import java.util.List;

/**
 *Implementation of {@link system.service.api.TrainService} interface.
 */
@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainDao trainDao;


    @Override
    public void save(Train train) {
    }

    @Override
    public Train findByName(String trainName) {
        return trainDao.findByName(trainName);
    }

    @Override
    public List<Train> findAll() {
        return trainDao.findAll();
    }
}
