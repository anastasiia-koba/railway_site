package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.FinalRoutDao;
import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Station;
import system.entity.Train;
import system.service.api.FinalRoutService;
import system.service.api.RoutService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *Implementation of {@link system.service.api.FinalRoutService} interface.
 */
@Slf4j
@Service
public class FinalRoutServiceImpl implements FinalRoutService {

    @Autowired
    private FinalRoutDao finalRoutDao;

    @Autowired
    private RoutService routService;

    @Transactional
    @Override
    public void save(FinalRout finalRout) {
        if (finalRout.getId() != null) {
            try {
                FinalRout finalRoutForChange = finalRoutDao.findById(finalRout.getId());
                finalRoutForChange.setTrain(finalRout.getTrain());
                finalRoutForChange.setRout(finalRout.getRout());
                finalRoutForChange.setDate(finalRout.getDate());

                finalRoutDao.update(finalRoutForChange);
                log.info("Updated Final Rout from {} to {} ", finalRout.getRout().getStartStation().getStationName(),
                        finalRout.getRout().getEndStation().getStationName());
            } catch (DaoException e) {
                log.error("Update Final Rout from {} to {} failed: {}: {} ", finalRout.getRout().getStartStation().getStationName(),
                        finalRout.getRout().getEndStation().getStationName(), e.getErrorCode(), e.getMessage());
            }
        } else {
            try {
                finalRoutDao.create(finalRout);
                log.info("Created Final Rout from {} to {} ", finalRout.getRout().getStartStation().getStationName(),
                        finalRout.getRout().getEndStation().getStationName());
            } catch (DaoException e) {
                log.error("Create Final Rout from {} to {} failed: {}: {} ", finalRout.getRout().getStartStation().getStationName(),
                        finalRout.getRout().getEndStation().getStationName(), e.getErrorCode(), e.getMessage());
            }
        }
    }

    @Transactional
    @Override
    public void delete(FinalRout finalRout) {
        try {
            finalRoutDao.remove(finalRout);
            log.info("Deleted Final Rout from {} to {} ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        } catch (DaoException e) {
            log.error("Delete Final Rout from {} to {} failed: {}: {} ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName(), e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public FinalRout findById(Long id) {
        try {
            return finalRoutDao.findById(id);
        } catch (DaoException e) {
            log.error("Find Final Rout by Id {} failed: {}: {} ", id, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Set<FinalRout> findAll() {
        try {
            return finalRoutDao.findAll();
        } catch (DaoException e) {
            log.error("Find All Final Routs failed: {}: {} ", e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByDate(LocalDate date) {
        try {
            return finalRoutDao.findByDate(date);
        } catch (DaoException e) {
            log.error("Find Final Rout by Date {} failed: {}: {} ", date, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByStationAndDate(Station station, LocalDate date) {
        try {
            return finalRoutDao.findByStationAndDate(station, date);
        } catch (DaoException e) {
            log.error("Find Final Rout by Station {} and Date {} failed: {}: {} ", station.getStationName(), date,
                    e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Set<FinalRout> findByStationToStationOnDate(Station start, Station end, LocalDate date) {
        try {
            return finalRoutDao.findByStationToStationOnDate(start, end, date);
        } catch (DaoException e) {
            log.error("Find Final Rout by Start Station {} and End Station {} and Date {} failed: {}: {} ", start.getStationName(),
                    end.getStationName(), date, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, LocalDate date) {
        try {
            return finalRoutDao.findByRoutAndTrainAndDate(rout, train, date);
        } catch (DaoException e) {
            log.error("Find Final Rout by Rout id {} and Train {} and Date {} failed: {}: {} ", rout.getId(),
                    train.getTrainName(), date, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Long, LocalTime> getMapDeparture(Set<FinalRout> finalRouts) {
        Map<Long, LocalTime> mapDeparture = new HashMap<>(); // Long - finalRout.id

        for (FinalRout finalRout : finalRouts) {
            LocalTime timeDeparture = routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), finalRout.getRout().getStartStation()) != null ?
                    routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), finalRout.getRout().getStartStation()).getDepartureTime() : null;
            mapDeparture.put(finalRout.getId(), timeDeparture);
        }

        return mapDeparture;
    }

    @Override
    public Map<Long, LocalTime> getMapArrival(Set<FinalRout> finalRouts) {
        Map<Long, LocalTime> mapArrival = new HashMap<>(); // Long - finalRout.id

        for (FinalRout finalRout : finalRouts) {
            LocalTime timeArrival = routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), finalRout.getRout().getEndStation()) != null ?
                    routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), finalRout.getRout().getEndStation()).getArrivalTime(): null ;
            mapArrival.put(finalRout.getId(), timeArrival);
        }

        return mapArrival;
    }

    @Override
    public Map<Long, LocalTime> getMapDepartureByStation(Set<FinalRout> finalRouts, Station station) {
        Map<Long, LocalTime> mapDeparture = new HashMap<>(); // Long - finalRout.id

        for (FinalRout finalRout : finalRouts) {
            LocalTime timeDeparture = routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), station) != null ?
                    routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), station).getDepartureTime() : null;
            mapDeparture.put(finalRout.getId(), timeDeparture);
        }
        return mapDeparture;
    }

    @Override
    public Map<Long, LocalTime> getMapArrivalByStation(Set<FinalRout> finalRouts, Station station) {
        Map<Long, LocalTime> mapArrival = new HashMap<>(); // Long - finalRout.id

        for (FinalRout finalRout : finalRouts) {
            LocalTime timeArrival = routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), station) != null ?
                    routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), station).getArrivalTime(): null ;
            mapArrival.put(finalRout.getId(), timeArrival);
        }

        return mapArrival;
    }

    @Override
    public Map<Long, LocalTime> getMapTimeInTravel(Set<FinalRout> finalRouts, Station from, Station to) {
        Map<Long, LocalTime> mapTimeInTravel = new HashMap<>();

        Map<Long, LocalTime> mapDeparture = getMapDepartureByStation(finalRouts, from);
        Map<Long, LocalTime> mapArrival = getMapArrivalByStation(finalRouts, to);

        mapDeparture = mapDeparture.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),
                e -> (e.getValue() == null ? LocalTime.of(0, 0) : e.getValue())));

        mapArrival = mapArrival.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),
                e -> (e.getValue() == null ? LocalTime.of(0, 0) : e.getValue())));

        for (FinalRout finalRout: finalRouts) {
            Duration duration = Duration.between(mapArrival.get(finalRout.getId()), mapDeparture.get(finalRout.getId())).abs();
            mapTimeInTravel.put(finalRout.getId(), LocalTime.ofSecondOfDay(duration.getSeconds()));
        }

        return mapTimeInTravel;
    }

    @Override
    public Map<Long, Integer> getMapPriceInCustomRout(Set<FinalRout> finalRouts, Station from, Station to) {
        Map<Long, Integer> mapPrice = new HashMap<>();

        for (FinalRout finalRout: finalRouts) {
            mapPrice.put(finalRout.getId(), routService.getPriceInRoutBetweenDepartureAndDestination(finalRout.getRout(), from, to));
        }

        return mapPrice;
    }

}
