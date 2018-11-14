package system.service.api;

import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;

/**
 * Service class for {@link system.entity.RoutSection}
 */
public interface RoutSectionService {
    void save(RoutSection routSection);
    void delete(RoutSection routSection);
    RoutSection findById(Long id);
    List<RoutSection> findByDeparture(Station departure);
    List<RoutSection> findByDestination(Station destination);
    List<RoutSection> findByDepartureAndDestination(Station departure, Station destination);
}
