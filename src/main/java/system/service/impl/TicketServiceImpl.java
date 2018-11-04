package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.api.TicketDao;
import system.entity.FinalRout;
import system.entity.Ticket;
import system.entity.UserProfile;
import system.service.api.TicketService;

import java.util.Set;

/**
 *Implementation of {@link system.service.api.TicketService} interface.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    public void create(Ticket ticket) {
        ticketDao.create(ticket);
    }

    @Override
    public void save(Ticket ticket) {
        ticketDao.update(ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketDao.remove(ticket);
    }

    @Override
    public Ticket findById(Long id) {
        return ticketDao.findById(id);
    }

    @Override
    public Set<Ticket> findByUser(UserProfile user) {
        return ticketDao.findByUser(user);
    }

    @Override
    public Set<Ticket> findByFinalRout(FinalRout finalRout) {
        return ticketDao.findByFinalRout(finalRout);
    }
}
