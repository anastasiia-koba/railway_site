package system.service.api;

import system.entity.UserData;
import system.entity.UserProfile;

import java.time.LocalDate;

/**
 * Service class for {@link system.entity.UserProfile}
 */
public interface UserService {
    void createUser(UserData user);
    void saveUser(UserData user);
    void createProfile(UserProfile user);
    void saveProfile(UserProfile user);
    void deleteUser(UserData user);
    void deleteProfile(UserProfile user);
    UserData findByUsername(String username);
    UserProfile findProfileByUsername(String username);
    UserData findById(Long id);
    UserProfile findProfileById(Long id);
    UserProfile findByNamesAndDate(String surname, String firstname, LocalDate birthday);
}
