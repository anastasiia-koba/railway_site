package system.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import system.DaoException;
import system.dao.api.UserDao;
import system.entity.UserData;

import javax.persistence.Query;
import java.util.List;

/**
 *Implementation of {@link UserDao} interface.
 */
@Slf4j
@Repository
public class UserDaoImpl extends JpaDao<Long, UserData> implements UserDao {
    @Override
    public UserData findByUsername(String username) throws DaoException {
        try {
            Query q = entityManager.createQuery("SELECT u FROM UserData u WHERE u.username = :username");
            q.setParameter("username", username);

            List results = q.getResultList();
            if (results.isEmpty()) {
                log.debug("User {} is not founded", username);
                return null; // handle no-results case
            } else {
                return (UserData) results.get(0);
            }
        } catch (IllegalStateException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalStateException: Find by Name Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DaoException(DaoException.SQL_ERROR, "IllegalArgumentException: Find by Name Failed: " + e.getMessage());
        } catch (Exception e) {
            throw new DaoException(DaoException.SQL_ERROR, "Exception: Find by Name Failed: " + e.getMessage());
        }
    }
}
