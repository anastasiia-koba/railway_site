package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.TicketDao;
import system.entity.FinalRout;
import system.entity.Ticket;
import system.entity.UserProfile;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link system.entity.Ticket} interface.
 */
@Repository
public class TicketDaoImpl extends JpaDao<Long, Ticket> implements TicketDao {
    @Override
    public Set<Ticket> findByUser(UserProfile user) {
        Query q = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.user = :user");
        q.setParameter("user", user);

        Set<Ticket> tickets = new HashSet<>(q.getResultList());
        return tickets;
    }

    @Override
    public Set<Ticket> findByFinalRout(FinalRout finalRout) {
        Query q = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.finalRout = :finalRout");
        q.setParameter("finalRout", finalRout);

        Set<Ticket> tickets = new HashSet<>(q.getResultList());
        return tickets;
    }
}
