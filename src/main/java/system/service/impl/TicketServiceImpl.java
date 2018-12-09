package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.RoutDao;
import system.dao.api.TicketDao;
import system.entity.*;
import system.service.api.FinalRoutService;
import system.service.api.TicketService;
import system.service.api.UserService;

import java.util.*;
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

    @Autowired
    private UserService userService;

    @Autowired
    private FinalRoutService finalRoutService;

    @Transactional
    @Override
    public void create(Ticket ticket) {
        try {
            ticketDao.create(ticket);
            log.info("Created Ticket for User {} {} ", ticket.getProfile().getSurname(), ticket.getProfile().getFirstname());
        } catch (DaoException e) {
            log.error("Create Ticket for User {} {} failed: {}: {} ", ticket.getProfile().getSurname(),
                    ticket.getProfile().getFirstname(), e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public List<Ticket> formTickets(List<UserProfile> userProfiles, Station start, Station end, FinalRout finalRout, Integer price) {
        if (userProfiles.isEmpty())
            return null;

        List<Ticket> tickets = new ArrayList<>();
        UserData userData = userProfiles.get(0).getUserData();

        for (UserProfile user : userProfiles) {
            if (user.getId() == null) {//need create user
                userService.createProfile(user);
            }

            Ticket ticket = new Ticket();
            ticket.setProfile(user);
            ticket.setUserData(userData);
            ticket.setFinalRout(finalRout);
            ticket.setStartStation(start);
            ticket.setEndStation(end);
            ticket.setPrice(price);

            tickets.add(ticket);
        }

        return tickets;
    }

    @Transactional
    @Override
    public String save(List<Ticket> tickets) {

        for (Ticket ticket : tickets) {
            //check repeating user data
            if (isAnyBodyInFinalRoutWithUserData(ticket.getFinalRout(), ticket.getProfile())) {
                return "User with Surname: " + ticket.getProfile().getSurname() + " Firstname: " + ticket.getProfile().getFirstname() +
                        " Birthday: " + ticket.getProfile().getBirthDate() + " has already register. ";
            }
            //TODO check departure time train
            try {
                ticketDao.create(ticket);
                log.info("Created Ticket for User {} {}", ticket.getProfile().getSurname(), ticket.getProfile().getFirstname());
            } catch (DaoException e) {
                log.error("Purchase Ticket for User {} {} failed: {}: {} ", ticket.getProfile().getSurname(),
                        ticket.getProfile().getFirstname(), e.getErrorCode(), e.getMessage());
                return "Purchase order failed";
            }
        }

        return "ok";
    }

    @Transactional
    @Override
    public void delete(Ticket ticket) {
        try {
            ticketDao.remove(ticket);
            log.info("Delete Ticket for User {} {}", ticket.getProfile().getSurname(), ticket.getProfile().getFirstname());
        } catch (DaoException e) {
            log.error("Delete Ticket for User {} {} failed: {}: {} ", ticket.getProfile().getSurname(),
                    ticket.getProfile().getFirstname(), e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public Ticket findById(Long id) {
        try {
            return ticketDao.findById(id);
        } catch (DaoException e) {
            log.error("Find Ticket by Id {} failed: {}: {} ", id, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Set<Ticket> findByUser(UserProfile user) {
        try {
            Set<Ticket> tickets = ticketDao.findByProfile(user);

            if (user.getUserData() != null) {
                tickets.addAll(ticketDao.findByUser(user.getUserData()));
            }

            return tickets;
        } catch (DaoException e) {
            log.error("Find Ticket by User {} {} failed: {}: {} ", user.getSurname(), user.getFirstname(), e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Set<Ticket> findByFinalRout(FinalRout finalRout) {
        try {
            return ticketDao.findByFinalRout(finalRout);
        } catch (DaoException e) {
            log.error("Find Tickets by Final Rout from {} to {} failed: {}: {} ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName(), e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public Integer findCountTicketsByFinalRoutAndStartAndEndStations(FinalRout finalRout, Station start, Station end) {
        try {
            List<RoutSection> routSections = routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(finalRout.getRout(),
                    start, end);

            if (routSections == null)
                return null;

            Set<Ticket> tickets = ticketDao.findByFinalRout(finalRout);

            Integer countTickets = 0;

            for (Ticket ticket : tickets) {
                List<RoutSection> ticketSection = routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(finalRout.getRout(),
                        ticket.getStartStation(), ticket.getEndStation());

                Set<RoutSection> intersect = ticketSection.stream().filter(routSections::contains).collect(Collectors.toSet());

                if (!intersect.isEmpty())
                    countTickets++;
            }

            return countTickets;
        } catch (DaoException e) {
            log.error("Find Tickets by Rout id {} Between Departure {} And Destination {} failed: {}: {} ", finalRout.getId(),
                    start.getStationName(), end.getStationName(), e.getErrorCode(), e.getMessage());
        }

        return null;
    }

    @Override
    public Boolean isAnyBodyInFinalRoutWithUserData(FinalRout finalRout, UserProfile user) {
        try {
            return ticketDao.isAnyBodyInFinalRoutWithUserData(finalRout, user);
        } catch (DaoException e) {
            log.error("Find Tickets by Final Rout from {} to {} failed: {}: {} ", finalRout.getRout().getStartStation().getStationName(),
                    finalRout.getRout().getEndStation().getStationName(), e.getErrorCode(), e.getMessage());
        }

        return false;
    }

    @Override
    public Map<Long, Integer> getMapFreePlacesInCustomRout(Set<FinalRout> finalRouts, Station from, Station to) {
        Map<Long, Integer> mapPlaces = new HashMap<>();

        Iterator<FinalRout> iterator = finalRouts.iterator();
        FinalRout finalRout;
        while (iterator.hasNext()) {
            finalRout = iterator.next();
            Integer countTickets = findCountTicketsByFinalRoutAndStartAndEndStations(finalRout, from, to);
            if (countTickets == null) {
                iterator.remove();
                continue;
            }
            mapPlaces.put(finalRout.getId(), finalRout.getTrain().getPlacesNumber() -
                          countTickets);
        }
        return mapPlaces;
    }
}
