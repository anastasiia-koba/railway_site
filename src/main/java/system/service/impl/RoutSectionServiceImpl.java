package system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.api.RoutSectionDao;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.api.RoutSectionService;

import java.util.List;

/**
 *Implementation of {@link system.service.api.RoutSectionService} interface.
 */
@Service
public class RoutSectionServiceImpl implements RoutSectionService {

    @Autowired
    private RoutSectionDao routSectionDao;

    @Override
    public void save(RoutSection routSection) {
    }

    @Override
    public List<RoutSection> findByDeparture(Station departure) {
        return routSectionDao.findByDeparture(departure);
    }

    @Override
    public List<RoutSection> findByDestination(Station destination) {
        return routSectionDao.findByDestination(destination);
    }

    @Override
    public List<RoutSection> findByDepartureAndDestination(Station departure, Station destination) {
        return routSectionDao.findByDepartureAndDestination(departure, destination);
    }
}
