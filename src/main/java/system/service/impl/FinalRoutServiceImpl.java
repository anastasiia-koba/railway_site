package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.DaoException;
import system.dao.api.FinalRoutDao;
import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;
import system.service.api.FinalRoutService;

import java.time.LocalDate;
import java.util.Set;

/**
 *Implementation of {@link system.service.api.FinalRoutService} interface.
 */
@Slf4j
@Service
public class FinalRoutServiceImpl implements FinalRoutService {

    @Autowired
    private FinalRoutDao finalRoutDao;

    @Override
    public void create(FinalRout finalRout) {
        try {
            finalRoutDao.create(finalRout);
            log.debug("Created Final Rout from {} to {} ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Create Final Rout from {} to {} failed ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        }
    }

    @Override
    public void save(FinalRout finalRout) {
        try {
            finalRoutDao.update(finalRout);
            log.debug("Updated Final Rout from {} to {} ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Update Final Rout from {} to {} failed ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        }
    }

    @Override
    public void delete(FinalRout finalRout) {
        try {
            finalRoutDao.remove(finalRout);
            log.debug("Deleted Final Rout from {} to {} ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Delete Final Rout from {} to {} failed ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        }
    }

    @Override
    public FinalRout findById(Long id) {
        try {
            return finalRoutDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Final Rout by Id {} failed ", id);
        }
        return null;
    }

    @Override
    public Set<FinalRout> findAll() {
        try {
            return finalRoutDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find All Final Routs failed ");
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByDate(LocalDate date) {
        try {
            return finalRoutDao.findByDate(date);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Final Rout by Date {} failed ", date);
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByStationAndDate(Station station, LocalDate date) {
        try {
            return finalRoutDao.findByStationAndDate(station, date);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Final Rout by Station {} and Date {} failed ", station.getStationName(), date);
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByStationToStationOnDate(Station start, Station end, LocalDate date) {
        try {
            return finalRoutDao.findByStationToStationOnDate(start, end, date);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Final Rout by Start Station {} and End Station {} and Date {} failed ", start.getStationName(),
                    end.getStationName(), date);
        }
        return null;
    }

    @Override
    public FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, LocalDate date) {
        try {
            return finalRoutDao.findByRoutAndTrainAndDate(rout, train, date);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Final Rout by Rout id {} and Train {} and Date {} failed ", rout.getId(),
                    train.getTrainName(), date);
        }
        return null;
    }
}
