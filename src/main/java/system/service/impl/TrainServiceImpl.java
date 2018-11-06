package system.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public void create(Train train) {
        try {
            trainDao.create(train);
            log.debug("Created Train {} ", train.getTrainName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Create Train {} failed ", train.getTrainName());
        }
    }

    @Override
    public void save(Train train) {
        try {
            trainDao.update(train);
            log.debug("Updated Train {} ", train.getTrainName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Update Train {} failed ", train.getTrainName());
        }
    }

    @Override
    public void delete(Train train) {
        try {
            trainDao.remove(train);
            log.debug("Deleted Train {} ", train.getTrainName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Delete Train {} failed ", train.getTrainName());
        }
    }

    @Override
    public Train findById(Long id) {
        try {
            return trainDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Train by Id {} failed ", id);
        }
        return null;
    }

    @Override
    public Train findByName(String trainName) {
        try {
            return trainDao.findByName(trainName);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Train by Name {} failed ", trainName);
        }
        return null;
    }

    @Override
    public List<Train> findAll() {
        try {
            return trainDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find All Trains failed ");
        }
        return null;
    }
}
