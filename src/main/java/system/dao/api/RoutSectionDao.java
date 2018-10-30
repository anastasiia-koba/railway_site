package system.dao.api;

import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;

public interface RoutSectionDao extends Dao<Long, RoutSection> {
    List<RoutSection> findByDeparture(Station departure);
    List<RoutSection> findByDestination(Station destination);
    List<RoutSection> findByDepartureAndDestination(Station departure, Station destination);
}
