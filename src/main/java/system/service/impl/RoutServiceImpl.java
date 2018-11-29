package system.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.RoutDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.api.RoutService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RoutService} interface.
 */
@Slf4j
@Service
public class RoutServiceImpl implements RoutService {

    @Autowired
    private RoutDao routDao;

    @Transactional
    @Override
    public void save(Rout rout) {
        if (rout.getId() != null) {
            try {
                Rout routForChange = routDao.findById(rout.getId());
                routForChange.setRoutName(rout.getRoutName());
                routForChange.setStartStation(rout.getStartStation());
                routForChange.setEndStation(rout.getEndStation());
                routForChange.setRoutSections(rout.getRoutSections());

                routDao.update(routForChange);
                log.info("Updated Rout from {} to {} ", rout.getStartStation().getStationName(),
                        rout.getEndStation().getStationName());
            } catch (DaoException e) {
                e.printStackTrace();
                log.error("Update Rout from {} to {} failed ", rout.getStartStation().getStationName(),
                        rout.getEndStation().getStationName());
            }
        } else {
            try {
                routDao.create(rout);
                log.info("Created Rout from {} to {} ", rout.getStartStation().getStationName(),
                        rout.getEndStation().getStationName());
            } catch (DaoException e) {
                e.printStackTrace();
                log.error("Create Rout from {} to {} failed ", rout.getStartStation().getStationName(),
                        rout.getEndStation().getStationName());
            }
        }
    }

    @Transactional
    @Override
    public void delete(Rout rout) {
        try {
            routDao.remove(rout);
            log.info("Deleted Rout from {} to {} ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Delete Rout from {} to {} failed ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        }
    }

    @Override
    public Rout findById(Long id) {
        try {
            return routDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Rout by Id {} failed ", id);
        }
        return null;
    }

    @Override
    public List<Rout> findAll() {
        try {
            return routDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find All Routs failed: {}: {} ", e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public List<Rout> findAllValid() {
        try {
            List<Rout> routs = routDao.findAll();
            List<Rout> result = new ArrayList<>();

            for (Rout rout: routs) {
                if (isRoutWellBuilt(rout))
                    result.add(rout);
            }
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find All Valid Routs failed: {}: {} ", e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Rout findByName(String routName) {
        try {
            return routDao.findByName(routName);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Rout by routName {} failed ", routName);
        }
        return null;
    }

    @Override
    public List<Rout> findByStartStationAndEndStation(Station start, Station end) {
        try {
            return routDao.findByStartStationAndEndStation(start, end);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Rout by Departure {} and Destination {} failed ", start.getStationName(),
                    end.getStationName());
        }
        return null;
    }

    @Override
    public List<Rout> findByRoutSection(RoutSection routSection) {
        try {
            return routDao.findByRoutSection(routSection);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Rout by routSection {} failed ", routSection.getId());
        }
        return null;
    }

    @Override
    public List<RoutSection> getRoutSectionInRout(Rout rout) {
        try {
            List<RoutSection> routSections = routDao.getRoutSectionInRout(rout);

            List<RoutSection> result = new ArrayList<>();
            Station startStation = rout.getStartStation();

            sortRoutSections(routSections, result, startStation);

            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Rout Section by Rout from {} to {} failed ", rout.getStartStation().getStationName(),
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
            log.error("Find Rout Section by Rout id {} And Departure {} failed ", rout.getId(), departureStation.getStationName());
        }
        return null;
    }

    @Override
    public RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation) {
        try {
            return routDao.getRoutSectionByRoutAndDestinationStation(rout, destinationStation);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Rout Section by Rout id {} And Destination {} failed ", rout.getId(), destinationStation.getStationName());
        }
        return null;
    }

    @Override
    public List<RoutSection> getRoutSectionsInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination) {
        try {
            List<RoutSection> prep = routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination);

            List<RoutSection> result = new ArrayList<>();

            sortRoutSections(prep, result, rout.getStartStation());

            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Rout Sections in Rout {}, Departure {} And Destination {} failed ", rout.getRoutName(),
                    departure.getStationName(), destination.getStationName());
        }
        return null;
    }

    @Override
    public Integer getPriceInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination) {
        try {
            List<RoutSection> routSections = routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination);

            Integer price = 0;
            for (RoutSection rs : routSections) {
                price += rs.getPrice();
            }

            return price;
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Find Price by Rout id {} Between Departure {} And Destination {} failed ", rout.getId(),
                    departure.getStationName(), destination.getStationName());
        }

        return null;
    }

    @Override
    public void sortRoutSections(List<RoutSection> routSections, List<RoutSection> result, Station start) {
        List<RoutSection> prep = routSections;
        Station startStation = start;
        int idx;
        while ((idx = prep.stream().map(e -> e.getDeparture()).collect(Collectors.toList()).indexOf(startStation)) != -1) {
            result.add(prep.get(idx));
            startStation = prep.get(idx).getDestination();
            prep.remove(idx);
        }

        result.addAll(prep);
    }

    @Override
    public Boolean isRoutWellBuilt(Rout rout) {
        try {
            List<RoutSection> routSections = routDao.getRoutSectionInRout(rout);

            if (routSections.isEmpty())
                return false;

            int countNotSorted;
            List<RoutSection> result = new ArrayList<>();
            sortRoutSections(routSections, result, rout.getStartStation());
            countNotSorted = routSections.size();

            if ((countNotSorted == 0) && result.get(result.size()-1).getDestination().equals(rout.getEndStation()))
                return true;
            else
                return false;
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Validate built rout from {} to {} failed ", rout.getStartStation().getStationName(),
                    rout.getEndStation().getStationName());
        }
        return false;
    }
}
