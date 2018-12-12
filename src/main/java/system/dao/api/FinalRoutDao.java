package system.dao.api;

import system.DaoException;
import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface FinalRoutDao extends Dao<Long, FinalRout> {
    Set<FinalRout> findByDate(LocalDate date) throws DaoException;
    Set<FinalRout> findAll() throws DaoException;
    List<FinalRout> findAllByPage(int pageid, int total) throws DaoException;
    Set<FinalRout> findByStationAndDate(Station station, LocalDate date) throws DaoException;
    Set<FinalRout> findByStationToStationOnDate(Station start, Station end, LocalDate date) throws DaoException;
    FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, LocalDate date) throws DaoException;
}
