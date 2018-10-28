package system.dao.impl;

import org.springframework.stereotype.Repository;
import system.dao.api.UserDao;
import system.entity.UserProfile;

import javax.persistence.Query;

/**
 *Implementation of {@link UserDao} interface.
 */
@Repository
public class UserDaoImpl extends JpaDao<Long, UserProfile> implements UserDao {
    @Override
    public UserProfile findByUsername(String username) {
        Query q = entityManager.createQuery("SELECT u FROM UserProfile u WHERE u.username = :username");
        q.setParameter("username", username);

        UserProfile user = (UserProfile) q.getSingleResult();
        return user;
    }
}
