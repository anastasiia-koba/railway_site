package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.StationDao;
import system.entity.Station;
import system.service.api.StationService;

import java.util.List;

/**
 * Implementation of {@link StationService} interface.
 */
@Slf4j
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao stationDao;

    @Transactional
    @Override
    public void save(Station station) {
        if (station.getId() != null) {
            try {
                Station stationForChange = stationDao.findById(station.getId());
                stationForChange.setStationName(station.getStationName());

                stationDao.update(stationForChange);
            } catch (DaoException e) {
                e.printStackTrace();
                log.debug("Update Station {} failed", station.getStationName());
            }
        } else {
            try {
                stationDao.create(station);
                log.debug("Created Station {} ", station.getStationName());
            } catch (DaoException e) {
                e.printStackTrace();
                log.debug("Create Station {} failed", station.getStationName());
            }
        }
    }

    @Transactional
    @Override
    public void delete(Station station) {
        try {
            stationDao.remove(station);
            log.debug("Deleted Station {} ", station.getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Delete Station {} failed", station.getStationName());
        }
    }

    @Override
    public Station findByName(String stationName) {
        try {
            return stationDao.findByName(stationName);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Station by Name {} failed ", stationName);
        }
        return null;
    }

    @Override
    public Station findById(Long id) {
        try {
            return stationDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Station by Id {} failed ", id);
        }
        return null;
    }

    @Override
    public List<Station> findAll() {
        try {
            return stationDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find All Stations failed ");
        }
        return null;
    }
}
