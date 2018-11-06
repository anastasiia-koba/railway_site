package system.service.impl;

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
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    public void create(Ticket ticket) {
        try {
            ticketDao.create(ticket);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Ticket ticket) {
        try {
            ticketDao.update(ticket);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Ticket ticket) {
        try {
            ticketDao.remove(ticket);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket findById(Long id) {
        try {
            return ticketDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Ticket> findByUser(UserProfile user) {
        try {
            return ticketDao.findByUser(user);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Ticket> findByFinalRout(FinalRout finalRout) {
        try {
            return ticketDao.findByFinalRout(finalRout);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
