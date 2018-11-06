package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.DaoException;
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
        try {
            finalRoutDao.create(finalRout);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(FinalRout finalRout) {
        try {
            finalRoutDao.update(finalRout);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(FinalRout finalRout) {
        try {
            finalRoutDao.remove(finalRout);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FinalRout findById(Long id) {
        try {
            return finalRoutDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<FinalRout> findAll() {
        try {
            return finalRoutDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByDate(Date date) {
        try {
            return finalRoutDao.findByDate(date);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByStationAndDate(Station station, Date date) {
        try {
            return finalRoutDao.findByStationAndDate(station, date);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByStationToStationOnDate(Station start, Station end, Date date) {
        try {
            return finalRoutDao.findByStationToStationOnDate(start, end, date);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, Date date) {
        try {
            return finalRoutDao.findByRoutAndTrainAndDate(rout, train, date);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
