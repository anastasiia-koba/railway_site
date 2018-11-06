package system.dao.api;

import system.DaoException;
import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;

public interface RoutSectionDao extends Dao<Long, RoutSection> {
    List<RoutSection> findByDeparture(Station departure) throws DaoException;
    List<RoutSection> findByDestination(Station destination) throws DaoException;
    List<RoutSection> findByDepartureAndDestination(Station departure, Station destination) throws DaoException;
}
