package system.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.TrainDao;
import system.entity.Train;
import system.service.api.TrainService;

import java.util.List;

/**
 *Implementation of {@link system.service.api.TrainService} interface.
 */
@Slf4j
@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainDao trainDao;

    @Transactional
    @Override
    public void save(Train train) {
        if (train.getId() != null) {
            try {
                Train trainForChange = trainDao.findById(train.getId());
                trainForChange.setTrainName(train.getTrainName());
                trainForChange.setPlacesNumber(train.getPlacesNumber());

                trainDao.update(trainForChange);
                log.info("Updated Train {} ", train.getTrainName());
            } catch (DaoException e) {
                e.printStackTrace();
                log.error("Update Train {} failed ", train.getTrainName());
            }
        } else {
            try {
                trainDao.create(train);
                log.info("Created Train {} ", train.getTrainName());
            } catch (DaoException e) {
                e.printStackTrace();
                log.error("Create Train {} failed ", train.getTrainName());
            }
        }
    }

    @Transactional
    @Override
    public void delete(Train train) {
        try {
            trainDao.remove(train);
            log.info("Deleted Train {} ", train.getTrainName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Delete Train {} failed ", train.getTrainName());
        }
    }

    @Override
    public Train findById(Long id) {
        try {
            return trainDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Train by Id {} failed ", id);
        }
        return null;
    }

    @Override
    public Train findByName(String trainName) {
        try {
            return trainDao.findByName(trainName);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Train by Name {} failed ", trainName);
        }
        return null;
    }

    @Override
    public List<Train> findAll() {
        try {
            return trainDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find All Trains failed ");
        }
        return null;
    }
}
