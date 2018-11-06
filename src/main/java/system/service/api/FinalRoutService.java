package system.service.api;

import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;

import java.sql.Date;
import java.util.Set;

/**
 * Service class for {@link system.entity.FinalRout}
 */
public interface FinalRoutService {
    void create(FinalRout finalRout);
    void save(FinalRout finalRout);
    void delete(FinalRout finalRout);
    FinalRout findById(Long id);
    Set<FinalRout> findAll();
    Set<FinalRout> findByDate(Date date);
    Set<FinalRout> findByStationAndDate(Station station, Date date);
    Set<FinalRout> findByStationToStationOnDate(Station start, Station end, Date date);
    FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, Date date);
}
