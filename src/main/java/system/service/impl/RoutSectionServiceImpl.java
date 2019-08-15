package system.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.RoutSectionDao;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.api.RoutSectionService;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link system.service.api.RoutSectionService} interface.
 */
@Slf4j
@Service
public class RoutSectionServiceImpl implements RoutSectionService {

    @Autowired
    private RoutSectionDao routSectionDao;

    @Transactional
    @Override
    public void save(RoutSection routSection){
        if (routSection.getId() != null) {
            try {
                RoutSection sectionForChange = routSectionDao.findById(routSection.getId());
                sectionForChange.setDeparture(routSection.getDeparture());
                sectionForChange.setDestination(routSection.getDestination());
                sectionForChange.setDistance(routSection.getDistance());
                sectionForChange.setPrice(routSection.getPrice());
                sectionForChange.setDepartureTime(routSection.getDepartureTime());
                sectionForChange.setArrivalTime(routSection.getArrivalTime());

                routSectionDao.update(sectionForChange);
                log.info("Updated Rout Section from {} to {} ", routSection.getDeparture().getStationName(),
                        routSection.getDestination().getStationName());
            } catch (DaoException e) {
                log.error("Update Rout Section from {} to {} failed: {}: {} ", routSection.getDeparture().getStationName(),
                        routSection.getDestination().getStationName(), e.getErrorCode(), e.getMessage());
            }
        } else {
            try {
                routSectionDao.create(routSection);
                log.info("Created Rout Section from {} to {} ", routSection.getDeparture().getStationName(),
                        routSection.getDestination().getStationName());
            } catch (DaoException e) {
                log.error("Create Rout Section from {} to {} failed: {}: {} ", routSection.getDeparture().getStationName(),
                        routSection.getDestination().getStationName(), e.getErrorCode(), e.getMessage());
            }
        }
    }

    @Transactional
    @Override
    public void delete(Long routSectionId) {
        try {
            RoutSection routSection = routSectionDao.findById(routSectionId);
            routSectionDao.remove(routSection);
            log.info("Deleted Rout Section from {} to {} ", routSection.getDeparture().getStationName(),
                    routSection.getDestination().getStationName());
        } catch (DaoException e) {
            log.error("Delete Rout Section id = {} failed: {}: {} ", routSectionId, e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public RoutSection findById(Long id) {
        try {
            return routSectionDao.findById(id);
        } catch (DaoException e) {
            log.error("Find Rout Section by Id {} failed: {}: {} ", id, e.getErrorCode(), e.getMessage());
        }

        return null;
    }

    @Override
    public List<RoutSection> findByDeparture(Station departure) {
        try {
            return routSectionDao.findByDeparture(departure);
        } catch (DaoException e) {
            log.error("Find Rout Section by Departure {} failed: {}: {} ", departure.getStationName(), e.getErrorCode(), e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public List<RoutSection> findByDestination(Station destination) {
        try {
            return routSectionDao.findByDestination(destination);
        } catch (DaoException e) {
            log.error("Find Rout Section by Destination {} failed: {}: {} ", destination.getStationName(), e.getErrorCode(), e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public List<RoutSection> findByDepartureAndDestination(Station departure, Station destination) {
        try {
            return routSectionDao.findByDepartureAndDestination(departure, destination);
        } catch (DaoException e) {
            log.error("Find Rout Section by Departure {} and Destination {} failed: {}: {} ", departure.getStationName(),
                    destination.getStationName(), e.getErrorCode(), e.getMessage());
        }

        return Collections.emptyList();
    }
}
