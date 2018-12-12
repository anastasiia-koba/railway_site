package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.UserProfileDao;
import system.entity.UserProfile;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

/**
 *Implementation of {@link UserProfileDao} interface.
 */
@Slf4j
@Repository
public class UserProfileDaoImpl extends JpaDao<Long, UserProfile> implements UserProfileDao {
    @Override
    public UserProfile findByNamesAndDate(String surname, String firstname, LocalDate birthday) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT u FROM UserProfile u " +
                    "WHERE u.surname = :surname AND u.firstname = :firstname AND u.birthDate = :birthDate");
            q.setParameter("surname", surname);
            q.setParameter("firstname", firstname);
            q.setParameter("birthDate", birthday);

            List results = q.getResultList();
            if (results.isEmpty()) {
                log.debug("User {} {} is not founded", surname, firstname);
                return null; // handle no-results case
            } else {
                return (UserProfile) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Names and Birthday Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Names and Birthday Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Find by Names and Birthday Failed: " + e.getMessage());
        }
    }
}
