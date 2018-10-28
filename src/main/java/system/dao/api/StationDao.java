package system.dao.api;

import system.entity.Station;

import java.util.List;

public interface StationDao extends Dao<Long, Station> {
    Station findByName(String stationName);

    List<Station> findAll();
}
