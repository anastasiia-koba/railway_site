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
    public void save(Rout rout) {
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
}
