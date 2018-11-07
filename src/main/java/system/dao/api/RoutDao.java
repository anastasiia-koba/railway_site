package system.dao.api;

import system.DaoException;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;

import java.util.List;
import java.util.Set;

public interface RoutDao extends Dao<Long, Rout> {
    List<Rout> findAll() throws DaoException;
    List<Rout> findByStartStationAndEndStation(Station start, Station end) throws DaoException;
    Set<RoutSection> getRoutSectionInRout(Rout rout) throws DaoException;
    RoutSection getRoutSectionByRoutAndDepartureStation(Rout rout, Station departureStation) throws DaoException;
    RoutSection getRoutSectionByRoutAndDestinationStation(Rout rout, Station destinationStation) throws DaoException;
    Set<RoutSection> getRoutSectionsInRoutBetweenDepartureAndDestination(Rout rout, Station departure, Station destination) throws DaoException;
}
