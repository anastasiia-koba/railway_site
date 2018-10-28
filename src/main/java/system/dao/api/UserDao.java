package system.dao.api;

import system.entity.UserProfile;

public interface UserDao extends Dao<Long, UserProfile>{
    UserProfile findByUsername(String username);
}
