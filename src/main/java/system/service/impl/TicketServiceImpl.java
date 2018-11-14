package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.DaoException;
import system.dao.api.RoutDao;
import system.dao.api.TicketDao;
import system.entity.*;
import system.service.api.TicketService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link system.service.api.TicketService} interface.
 */
@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private RoutDao routDao;

    @Override
    public void create(Ticket ticket) {
        try {
            ticketDao.create(ticket);
            log.debug("Created Ticket for User {} ", ticket.getUser().getUsername());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Create Ticket for User {} failed ", ticket.getUser().getUsername());
        }
    }

    @Override
    public void save(Ticket ticket) {
        try {
            ticketDao.update(ticket);
            log.debug("Updated Ticket for User {} ", ticket.getUser().getUsername());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Update Ticket for User {} failed ", ticket.getUser().getUsername());
        }
    }

    @Override
    public void delete(Ticket ticket) {
        try {
            ticketDao.remove(ticket);
            log.debug("Delete Ticket for User {} ", ticket.getUser().getUsername());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Delete Ticket for User {} failed ", ticket.getUser().getUsername());
        }
    }

    @Override
    public Ticket findById(Long id) {
        try {
            return ticketDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Ticket by Id {} failed ", id);
        }
        return null;
    }

    @Override
    public Set<Ticket> findByUser(UserProfile user) {
        try {
            return ticketDao.findByUser(user);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Ticket by User {} failed ", user.getUsername());
        }
        return null;
    }

    @Override
    public Set<Ticket> findByFinalRout(FinalRout finalRout) {
        try {
            return ticketDao.findByFinalRout(finalRout);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Tickets by Final Rout from {} to {} failed ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        }
        return null;
    }

    @Override
    public Integer findCountTicketsByFinalRoutAndStartAndEndStations(FinalRout finalRout, Station start, Station end) {
        try {
            Set<RoutSection> routSections = routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(finalRout.getRout(),
                                            start, end);

            Set<Ticket> tickets = ticketDao.findByFinalRout(finalRout);

            Integer countTickets = 0;

            for (Ticket ticket : tickets){
                Set<RoutSection> ticketSection = routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(finalRout.getRout(),
                        ticket.getStartStation(), ticket.getEndStation());

                Set<RoutSection> intersect = ticketSection.stream().filter(routSections::contains).collect(Collectors.toSet());

                if (!intersect.isEmpty())
                    countTickets++;
            }

            return countTickets;
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Tickets by Rout id {} Between Departure {} And Destination {} failed ", finalRout.getId(),
                    start.getStationName(), end.getStationName());
        }

        return null;
    }

    @Override
    public Boolean isAnyBodyInFinalRoutWithUserData(FinalRout finalRout, UserProfile user) {
        try {
            Set<Ticket> tickets = ticketDao.findByFinalRout(finalRout);

            for (Ticket ticket : tickets){
                if (ticket.getUser().getSurname().equals(user.getSurname()) &&
                        ticket.getUser().getFirstname().equals(user.getFirstname()) &&
                        ticket.getUser().getBirthDate().equals(user.getBirthDate())) {
                    return true;
                }
            }

            return false;
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find Tickets by Final Rout from {} to {} failed ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName());
        }

        return false;
    }

    @Override
    public Map<Long, Integer> getMapFreePlacesInCustomRout(Set<FinalRout> finalRouts, Station from, Station to) {
        Map<Long, Integer> mapPlaces = new HashMap<>();

        for (FinalRout finalRout: finalRouts) {
            mapPlaces.put(finalRout.getId(), finalRout.getTrain().getPlacesNumber() -
                    findCountTicketsByFinalRoutAndStartAndEndStations(finalRout, from, to));
        }
        return mapPlaces;
    }
}
