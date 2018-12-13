package system.service.api;

import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service class for {@link system.entity.FinalRout}
 */
public interface FinalRoutService {
    void save(FinalRout finalRout);
    void delete(FinalRout finalRout);
    FinalRout findById(Long id);
    Set<FinalRout> findAll();
    List<FinalRout> findAllByPage(int pageid);
    Set<FinalRout> findByDate(LocalDate date);
    Set<FinalRout> findByStationAndDate(Station station, LocalDate date);
    Set<FinalRout> findByStationToStationOnDate(Station start, Station end, LocalDate date);
    FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, LocalDate date);
    LocalTime getTimeDepartureByStation(FinalRout finalRout, Station station);
    LocalTime getTimeArrivalByStation(FinalRout finalRout, Station station);
    Map<Long, LocalTime> getMapDeparture(List<FinalRout> finalRouts);
    Map<Long, LocalTime> getMapArrival(List<FinalRout> finalRouts);
    Map<Long, LocalTime> getMapDepartureByStation(Set<FinalRout> finalRouts, Station station);
    Map<Long, LocalTime> getMapArrivalByStation(Set<FinalRout> finalRouts, Station station);
    Map<Long, LocalTime> getMapTimeInTravel(Set<FinalRout> finalRouts, Station from, Station to);
    Map<Long, Integer> getMapPriceInCustomRout(Set<FinalRout> finalRouts, Station from, Station to);
    Boolean isDepartureTimeIn10Minutes(FinalRout finalRout, Station station);
}
