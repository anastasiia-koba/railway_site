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
import system.entity.Role;
import system.entity.UserProfile;
import system.service.api.UserService;

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
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void save(UserProfile user){
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
            log.debug("Created User {} ", user.getUsername());
        }
        catch (DaoException e)
        {
            e.printStackTrace();
            log.debug("Create User {} failed ", user.getUsername());
        }
    }

    @Transactional
    @Override
    public void delete(UserProfile user) {
        try {
            userDao.remove(user);
            log.debug("Deleted User {} ", user.getUsername());
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Delete User {} failed ", user.getUsername());
        }
    }

    @Override
    public UserProfile findByUsername(String username){
        try {
            return userDao.findByUsername(username);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find User by Name {} failed ", username);
        }
        return null;
    }

    @Override
    public UserProfile findById(Long id) {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.debug("Find User by Id {} failed ", id);
        }
        return null;
    }
}
