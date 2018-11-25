package system.service.api;

import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;

/**
 * Service class for {@link system.entity.Rout}
 */
public interface RoutService {
    void save(Rout rout);
    void delete(Rout rout);
    Rout findById(Long id);
    List<Rout> findAll();
    Rout findByName(String routName);
    List<Rout> findByStartStationAndEndStation(Station start, Station end);
    List<Rout> findByRoutSection(RoutSection routSection);
    List<RoutSection> getRoutSectionInRout(Rout rout);
    RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation);
    RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation);
    List<RoutSection> getRoutSectionsInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination);
    Integer getPriceInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination);
}
