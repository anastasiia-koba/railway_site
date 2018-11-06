package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.api.FinalRoutDao;
import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;
import system.service.api.FinalRoutService;

import java.sql.Date;
import java.util.Set;

/**
 *Implementation of {@link system.service.api.FinalRoutService} interface.
 */
@Service
public class FinalRoutServiceImpl implements FinalRoutService {

    @Autowired
    private FinalRoutDao finalRoutDao;

    @Override
    public void create(FinalRout finalRout) {
        finalRoutDao.create(finalRout);
    }

    @Override
    public void save(FinalRout finalRout) {
        finalRoutDao.update(finalRout);
    }

    @Override
    public void delete(FinalRout finalRout) {
        finalRoutDao.remove(finalRout);
    }

    @Override
    public FinalRout findById(Long id) {
        return finalRoutDao.findById(id);
    }

    @Override
    public Set<FinalRout> findAll() {
        return finalRoutDao.findAll();
    }

    @Override
    public Set<FinalRout> findByDate(Date date) {
        return finalRoutDao.findByDate(date);
    }

    @Override
    public Set<FinalRout> findByStationAndDate(Station station, Date date) {
        return finalRoutDao.findByStationAndDate(station, date);
    }

    @Override
    public Set<FinalRout> findByStationToStationOnDate(Station start, Station end, Date date) {
        return finalRoutDao.findByStationToStationOnDate(start, end, date);
    }

    @Override
    public FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, Date date) {
        return finalRoutDao.findByRoutAndTrainAndDate(rout, train, date);
    }
}
