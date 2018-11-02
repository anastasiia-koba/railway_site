package system.service.api;

import system.entity.Station;

import java.util.List;

/**
 * Service class for {@link system.entity.Station}
 */
public interface StationService {
    void create(Station station);
    void save(Station station);
    void delete(Station station);
    Station findByName(String stationName);
    Station findById(Long id);
    List<Station> findAll();
}
