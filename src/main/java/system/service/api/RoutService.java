package system.service.api;

import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;
import java.util.Set;

/**
 * Service class for {@link system.entity.Rout}
 */
public interface RoutService {
    void create(Rout rout);
    void save(Rout rout);
    void delete(Rout rout);
    Rout findById(Long id);
    List<Rout> findAll();
    List<Rout> findByStartStationAndEndStation(Station start, Station end);
    Set<RoutSection> getRoutSectionInRout(Rout rout);
    RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation);
    RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation);
    Integer getPriceInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination);
}
