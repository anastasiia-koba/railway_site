package system.service.api;

import system.entity.Station;

import java.util.List;

/**
 * Service class for {@link system.entity.Station}
 */
public interface StationService {

    void save(Station station);

    Station findByName(String stationName);

    List<Station> findAll();
}
