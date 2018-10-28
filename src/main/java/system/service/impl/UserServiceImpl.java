package system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserProfile user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.findById(1L));
        user.setRoles(roles);

        try
        {
            userDao.create(user);
        }
        catch (Exception e)
        {
            logger.debug("Create user {} failed ", user.getUsername());
        }
    }

    @Override
    public UserProfile findByUsername(String username){
        return userDao.findByUsername(username);
    }
}
