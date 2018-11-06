package system.dao.api;

import system.DaoException;
import system.entity.Station;

import java.util.List;

public interface StationDao extends Dao<Long, Station> {
    Station findByName(String stationName) throws DaoException;
    List<Station> findAll() throws DaoException;
}
