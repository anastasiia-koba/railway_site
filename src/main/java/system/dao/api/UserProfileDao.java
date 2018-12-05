package system.dao.api;

import system.DaoException;
import system.entity.UserProfile;

import java.time.LocalDate;

public interface UserProfileDao extends Dao<Long, UserProfile>{
    UserProfile findByNamesAndDate(String surname, String firstname, LocalDate birthday) throws DaoException;
}
