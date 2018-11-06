package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.DaoException;
import system.dao.api.StationDao;
import system.entity.Station;
import system.service.api.StationService;

import java.util.List;

/**
 *Implementation of {@link StationService} interface.
 */
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao stationDao;

    @Override
    public void create(Station station) {
        try {
            stationDao.create(station);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Station station) {
        try {
            stationDao.update(station);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Station station) {
        try {
            stationDao.remove(station);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Station findByName(String stationName) {
        try {
            return stationDao.findByName(stationName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Station findById(Long id) {
        try {
            return stationDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Station> findAll() {
        try {
            return stationDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
