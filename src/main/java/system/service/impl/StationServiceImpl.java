package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.StationDao;
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
    public void save(Station station) {

    }

    @Override
    public Station findByStationName(String stationName) {
        return stationDao.findByStationName(stationName);
    }

    @Override
    public List<Station> findAll() {
        return stationDao.findAll();
    }
}
