package system.dao.api;

import system.entity.FinalRout;
import system.entity.Ticket;
import system.entity.UserProfile;

import java.util.Set;

public interface TicketDao extends Dao<Long, Ticket> {
    Set<Ticket> findByUser(UserProfile user);
    Set<Ticket> findByFinalRout(FinalRout finalRout);
}
