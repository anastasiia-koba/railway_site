package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.DaoException;
import system.dao.api.TicketDao;
import system.entity.FinalRout;
import system.entity.Ticket;
import system.entity.UserProfile;
import system.service.api.TicketService;

import java.util.Set;

/**
 * Implementation of {@link system.service.api.TicketService} interface.
 */
@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

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
}
