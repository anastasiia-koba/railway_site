package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        stationDao.create(station);
    }

    @Override
    public void save(Station station) {
        stationDao.update(station);
    }

    @Override
    public void delete(Station station) {
        stationDao.remove(station);
    }

    @Override
    public Station findByName(String stationName) {
        return stationDao.findByName(stationName);
    }

    @Override
    public Station findById(Long id) {
        return stationDao.findById(id);
    }

    @Override
    public List<Station> findAll() {
        return stationDao.findAll();
    }
}
