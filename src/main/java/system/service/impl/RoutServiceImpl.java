package system.service.impl;


import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class RoutServiceImpl implements RoutService {

    @Autowired
    private RoutDao routDao;

    @Override
    public void create(Rout rout) {
        try {
            routDao.create(rout);
            log.debug("Created Rout from {} to {} ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Create Rout from {} to {} failed ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        }
    }

    @Override
    public void save(Rout rout) {
        try {
            routDao.update(rout);
            log.debug("Updated Rout from {} to {} ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Update Rout from {} to {} failed ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        }
    }

    @Override
    public void delete(Rout rout) {
        try {
            routDao.remove(rout);
            log.debug("Deleted Rout from {} to {} ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Delete Rout from {} to {} failed ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        }
    }

    @Override
    public Rout findById(Long id) {
        try {
            return routDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Rout by Id {} failed ", id);
        }
        return null;
    }

    @Override
    public List<Rout> findAll() {
        try {
            return routDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find All Routs failed ");
        }
        return null;
    }

    @Override
    public List<Rout> findByStartStationAndEndStation(Station start, Station end) {
        try {
            return routDao.findByStartStationAndEndStation(start, end);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Rout by Departure {} and Destination {} failed ", start.getStationName(),
                    end.getStationName());
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
            log.debug("Find Rout Section by Rout from {} to {} failed ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        }
        return null;
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation) {
        try {
            return routDao.getRoutSectionByRoutAndDepartureStation(rout, departureStation);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Rout Section by Rout id {} And Departure {} failed ", rout.getId(), departureStation.getStationName());
        }
        return null;
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation) {
        try {
            return routDao.getRoutSectionByRoutAndDestinationStation(rout, destinationStation);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Rout Section by Rout id {} And Destination {} failed ", rout.getId(), destinationStation.getStationName());
        }
        return null;
    }
}
