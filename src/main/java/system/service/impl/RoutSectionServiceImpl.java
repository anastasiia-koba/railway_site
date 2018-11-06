package system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.DaoException;
import system.dao.api.RoutSectionDao;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.api.RoutSectionService;

import java.util.List;

/**
 * Implementation of {@link system.service.api.RoutSectionService} interface.
 */
@Service
public class RoutSectionServiceImpl implements RoutSectionService {

    @Autowired
    private RoutSectionDao routSectionDao;

    @Override
    public void create(RoutSection routSection) {
        try {
            routSectionDao.create(routSection);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(RoutSection routSection){
        try {
        routSectionDao.update(routSection);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(RoutSection routSection) {
        try {
            routSectionDao.remove(routSection);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RoutSection findById(Long id) {
        try {
            return routSectionDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RoutSection> findByDeparture(Station departure) {
        try {
            return routSectionDao.findByDeparture(departure);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RoutSection> findByDestination(Station destination) {
        try {
            return routSectionDao.findByDestination(destination);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RoutSection> findByDepartureAndDestination(Station departure, Station destination) {
        try {
            return routSectionDao.findByDepartureAndDestination(departure, destination);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return null;
    }
}
