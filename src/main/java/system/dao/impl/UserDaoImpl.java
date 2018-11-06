package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.UserDao;
import system.entity.UserProfile;

import javax.persistence.Query;
import java.util.List;

/**
 *Implementation of {@link UserDao} interface.
 */
@Slf4j
@Repository
public class UserDaoImpl extends JpaDao<Long, UserProfile> implements UserDao {
    @Override
    public UserProfile findByUsername(String username) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT u FROM UserProfile u WHERE u.username = :username");
            q.setParameter("username", username);

            List results = q.getResultList();
            if (results.isEmpty()) {
                log.debug("User {} is not founded", username);
                return null; // handle no-results case
            } else {
                return (UserProfile) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Name Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Name Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException._SQL_ERROR, "Find by Name Failed: " + e.getMessage());
        }
    }
}
