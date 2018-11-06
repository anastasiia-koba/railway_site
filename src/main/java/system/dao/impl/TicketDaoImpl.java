package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.DaoException;
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
    public Set<Ticket> findByUser(UserProfile user) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.user = :user");
            q.setParameter("user", user);

            Set<Ticket> tickets = new HashSet<>(q.getResultList());
            return tickets;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Ticket by User Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Ticket by User Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find Ticket by User  Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<Ticket> findByFinalRout(FinalRout finalRout) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.finalRout = :finalRout");
            q.setParameter("finalRout", finalRout);

            Set<Ticket> tickets = new HashSet<>(q.getResultList());
            return tickets;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by FinalRout Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by FinalRout Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by FinalRout Failed: " + e.getMessage());
        }
    }
}
