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
    void save(Rout rout);
    List<Rout> findByStartStationAndEndStation(Station start, Station end);
    Set<RoutSection> getRoutSectionInRout(Rout rout);
}
