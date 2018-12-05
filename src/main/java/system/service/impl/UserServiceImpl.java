package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import system.DaoException;
import system.dao.api.RoleDao;
import system.dao.api.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.api.UserProfileDao;
import system.entity.Role;
import system.entity.UserData;
import system.entity.UserProfile;
import system.service.api.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 *Implementation of {@link UserService} interface.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void createUser(UserData user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(user.getPassword());
        Set<Role> roles = new HashSet<>();

        try {
            roles.add(roleDao.findById(1L));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        user.setRoles(roles);

        try
        {
            userDao.create(user);
            log.info("Created User {} ", user.getUsername());
        } catch (DaoException e) {
            log.error("Create User {} failed: {}: {} ", user.getUsername(), e.getErrorCode(), e.getMessage());
        }
    }

    @Transactional
    @Override
    public void saveUser(UserData user) {
        try
        {
            UserData userForChange = userDao.findById(user.getId());
            userForChange.setUsername(user.getUsername());
            userForChange.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userForChange.setConfirmPassword(user.getPassword());
            userDao.update(userForChange);
            log.info("Updated User {} ", user.getUsername());
        } catch (DaoException e) {
            log.error("Update User {} failed: {}: {} ", user.getUsername(), e.getErrorCode(), e.getMessage());
        }
    }

    @Transactional
    @Override
    public void createProfile(UserProfile user) {
        try
        {
            userProfileDao.create(user);
            log.info("Created UserProfile {} {}", user.getSurname(), user.getFirstname());
        } catch (DaoException e) {
            log.error("Create UserProfile {} {} failed: {}: {} ", user.getSurname(), user.getFirstname(),
                    e.getErrorCode(), e.getMessage());
        }
    }

    @Transactional
    @Override
    public void saveProfile(UserProfile user) {
        try
        {
            UserProfile userProfile = userProfileDao.findById(user.getId());
            userProfile.setFirstname(user.getFirstname());
            userProfile.setSurname(user.getFirstname());
            userProfile.setBirthDate(user.getBirthDate());
            userProfileDao.update(user);
            log.info("Updated UserProfile {} {} ", user.getSurname(), user.getFirstname());
        } catch (DaoException e) {
            log.error("Update UserProfile {} failed: {}: {} ", user.getSurname(), user.getFirstname(),
                    e.getErrorCode(), e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteUser(UserData user) {
        try {
            userDao.remove(user);
            log.info("Deleted User {} ", user.getUsername());
        } catch (DaoException e) {
            log.error("Delete User {} failed: {}: {} ", user.getUsername(), e.getErrorCode(), e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteProfile(UserProfile user) {
        try {
            userProfileDao.remove(user);
            log.info("Deleted UserProfile {} {} ", user.getSurname(), user.getFirstname());
        } catch (DaoException e) {
            log.error("Delete UserProfile {} {} failed: {}: {} ", user.getSurname(), user.getFirstname(),
                    e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public UserData findByUsername(String username){
        try {
            return userDao.findByUsername(username);
        } catch (DaoException e) {
            log.error("Find User by Name {} failed: {}: {} ", username, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public UserProfile findProfileByUsername(String username) {
        try {
             UserData userData = userDao.findByUsername(username);
             return userData.getUserProfile();
        } catch (DaoException e) {
            log.error("Find User by Name {} failed: {}: {} ", username, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public UserData findById(Long id) {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            log.error("Find User by Id {} failed: {}: {} ", id, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public UserProfile findProfileById(Long id) {
        try {
            return userProfileDao.findById(id);
        } catch (DaoException e) {
            log.error("Find UserProfile by Id {} failed: {}: {} ", id, e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    @Override
    public UserProfile findByNamesAndDate(String surname, String firstname, LocalDate birthday) {
        try {
            return userProfileDao.findByNamesAndDate(surname, firstname, birthday);
        } catch (DaoException e) {
            log.error("Find User by Name {} {} and Date {} failed: {}: {} ", surname, firstname,
                    birthday, e.getErrorCode(), e.getMessage());
        }
        return null;
    }
}
