package system.dao.api;

import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;
import java.util.Set;

public interface RoutDao extends Dao<Long, Rout> {
    List<Rout> findByStartStationAndEndStation(Station start, Station end);
    Set<RoutSection> getRoutSectionInRout(Rout rout);
}
