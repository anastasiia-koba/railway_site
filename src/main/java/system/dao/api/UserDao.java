package system.dao.api;

import system.DaoException;
import system.entity.UserData;

public interface UserDao extends Dao<Long, UserData>{
    UserData findByUsername(String username) throws DaoException;
}
