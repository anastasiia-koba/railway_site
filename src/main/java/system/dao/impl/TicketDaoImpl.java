package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.TicketDao;
import system.entity.FinalRout;
import system.entity.Ticket;
import system.entity.UserData;
import system.entity.UserProfile;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link system.entity.Ticket} interface.
 */
@Repository
public class TicketDaoImpl extends JpaDao<Long, Ticket> implements TicketDao {
    @Override
    public Set<Ticket> findByUser(UserData user) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Ticket t " +
                    "inner join fetch t.profile tk WHERE t.userData = :user ");
            q.setParameter("user", user);

            Set<Ticket> tickets = new HashSet<>(q.getResultList());
            return tickets;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by User Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by User Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by User  Failed: " + e.getMessage());
        }
    }

    @Override
    public Set<Ticket> findByProfile(UserProfile user) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.profile = :user");
            q.setParameter("user", user);

            Set<Ticket> tickets = new HashSet<>(q.getResultList());
            return tickets;
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by Profile Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by Profile Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by Profile  Failed: " + e.getMessage());
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
            throw new DaoException(DaoException.SQL_ERROR, "Find by FinalRout Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by FinalRout Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by FinalRout Failed: " + e.getMessage());
        }
    }

    @Override
    public Boolean isAnyBodyInFinalRoutWithUserData(FinalRout finalRout, UserProfile user) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT t FROM Ticket t " +
                    "inner join fetch t.profile user WHERE t.finalRout = :finalRout " +
                    "AND user.surname = :surname AND user.firstname = :firstname " +
                    "AND user.birthDate = :birthdate");
            q.setParameter("finalRout", finalRout);
            q.setParameter("surname", user.getSurname());
            q.setParameter("firstname", user.getFirstname());
            q.setParameter("birthdate", user.getBirthDate());

            List results = q.getResultList();

            if (results.isEmpty()) {
                return false; // handle no-results case
            } else {
                return true;
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by FinalRout and User Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by FinalRout and User Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find Ticket by FinalRout and User Failed: " + e.getMessage());
        }
    }
}
