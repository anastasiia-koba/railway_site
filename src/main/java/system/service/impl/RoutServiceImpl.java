package system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.DaoException;
import system.dao.api.RoutDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.api.RoutService;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link RoutService} interface.
 */
@Service
public class RoutServiceImpl implements RoutService {

    @Autowired
    private RoutDao routDao;

    @Override
    public void create(Rout rout) {
        try {
            routDao.create(rout);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Rout rout) {
        try {
            routDao.update(rout);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Rout rout) {
        try {
            routDao.remove(rout);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Rout findById(Long id) {
        try {
            return routDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Rout> findAll() {
        try {
            return routDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Rout> findByStartStationAndEndStation(Station start, Station end) {
        try {
            return routDao.findByStartStationAndEndStation(start, end);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<RoutSection> getRoutSectionInRout(Rout rout) {
        try {
            Set<RoutSection> routSections = routDao.getRoutSectionInRout(rout);
            //TODO sort of routSection
            return routSections;
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation) {
        try {
            return routDao.getRoutSectionByRoutAndDepartureStation(rout, departureStation);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation) {
        try {
            return routDao.getRoutSectionByRoutAndDestinationStation(rout, destinationStation);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
