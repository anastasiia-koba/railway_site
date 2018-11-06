package system.dao.api;

import system.DaoException;
import system.entity.FinalRout;
import system.entity.Ticket;
import system.entity.UserProfile;

import java.util.Set;

public interface TicketDao extends Dao<Long, Ticket> {
    Set<Ticket> findByUser(UserProfile user) throws DaoException;
    Set<Ticket> findByFinalRout(FinalRout finalRout) throws DaoException;
}
