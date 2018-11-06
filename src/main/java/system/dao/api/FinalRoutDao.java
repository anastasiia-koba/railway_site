package system.dao.api;

import system.DaoException;
import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;

import java.sql.Date;
import java.util.Set;

public interface FinalRoutDao extends Dao<Long, FinalRout> {
    Set<FinalRout> findByDate(Date date) throws DaoException;
    Set<FinalRout> findAll() throws DaoException;
    Set<FinalRout> findByStationAndDate(Station station, Date date) throws DaoException;
    Set<FinalRout> findByStationToStationOnDate(Station start, Station end, Date date) throws DaoException;
    FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, Date date) throws DaoException;
}
