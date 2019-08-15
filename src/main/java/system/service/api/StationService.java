package system.service.api;

import system.entity.Station;

import java.util.List;

/**
 * Service class for {@link system.entity.Station}
 */
public interface StationService {
    void save(Station station);
    String delete(Long stationId);
    Station findByName(String stationName);
    Station findById(Long id);
    List<Station> findAll();
}
