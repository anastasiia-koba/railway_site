package system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.api.RoutDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.api.RoutService;

import java.util.List;
import java.util.Set;

/**
 *Implementation of {@link RoutService} interface.
 */
@Service
public class RoutServiceImpl implements RoutService {

    @Autowired
    private RoutDao routDao;

    @Override
    public void create(Rout rout) {
        routDao.create(rout);
    }

    @Override
    public void save(Rout rout) {
        routDao.update(rout);
    }

    @Override
    public void delete(Rout rout) {
        routDao.remove(rout);
    }

    @Override
    public Rout findById(Long id) {
        return routDao.findById(id);
    }

    @Override
    public List<Rout> findAll() {
        return routDao.findAll();
    }

    @Override
    public List<Rout> findByStartStationAndEndStation(Station start, Station end) {
        return routDao.findByStartStationAndEndStation(start, end);
    }

    @Override
    public Set<RoutSection> getRoutSectionInRout(Rout rout) {
        Set<RoutSection> routSections = routDao.getRoutSectionInRout(rout);
        //TODO sort of routSection
        return routSections;
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation) {
        return routDao.getRoutSectionByRoutAndDepartureStation(rout, departureStation);
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation) {
        return routDao.getRoutSectionByRoutAndDestinationStation(rout, destinationStation);
    }
}
