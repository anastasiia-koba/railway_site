package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.StationDao;
import system.entity.Station;
import system.service.api.StationService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
                log.info("Updated Station {} ", station.getStationName());
            } catch (DaoException e) {
                log.error("Update Station {} failed: {}: {} ", station.getStationName(), e.getErrorCode(), e.getMessage());
            }
        } else {
            try {
                stationDao.create(station);
                log.info("Created Station {} ", station.getStationName());
            } catch (DaoException e) {
                log.error("Create Station {} failed: {}: {} ", station.getStationName(), e.getErrorCode(), e.getMessage());
            }
        }
    }

    @Transactional
    @Override
    public String delete(Long stationId) {
        Optional<Station> station = Optional.empty();
        try {
            station = Optional.ofNullable(stationDao.findById(stationId));
            stationDao.remove(station.get());

            log.info("Deleted Station {} ", station.get().getStationName());
        } catch (DaoException e) {
            log.error("Delete Station {} failed: {}: {} ", station.get().getStationName(), e.getErrorCode(), e.getMessage());
        }
        return "Station '" + station.get().getStationName() + "' was deleted.";
    }

    @Override
    public Station findByName(String stationName) {
        try {
            return stationDao.findByName(stationName);
        } catch (DaoException e) {
            log.error("Find Station by Name {} failed: {}: {} ", stationName, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Station findById(Long id) {
        try {
            return stationDao.findById(id);
        } catch (DaoException e) {
            log.error("Find Station by Id {} failed: {}: {} ", id, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public List<Station> findAll() {
        try {
            List<Station> list = stationDao.findAll();
            Collections.sort(list, Comparator.comparing(Station::getStationName));
            return list;
        } catch (DaoException e) {
            log.error("Find All Stations failed: {}: {} ", e.getErrorCode(), e.getMessage());
        }
        return Collections.emptyList();
    }
}
