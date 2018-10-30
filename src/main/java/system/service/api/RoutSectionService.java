package system.service.api;

import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;

/**
 * Service class for {@link system.entity.RoutSection}
 */
public interface RoutSectionService {
    void save(RoutSection routSection);
    List<RoutSection> findByDeparture(Station departure);
    List<RoutSection> findByDestination(Station destination);
    List<RoutSection> findByDepartureAndDestination(Station departure, Station destination);
}
