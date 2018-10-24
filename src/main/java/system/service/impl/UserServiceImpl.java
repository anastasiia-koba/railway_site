package system.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import system.dao.RoleDao;
import system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.entity.Role;
import system.entity.UserProfile;

import java.util.HashSet;
import java.util.Set;


/**
 *Implementation of {@link UserService} interface.
 */
@Service
public class UserServiceImpl implements UserService {

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
        roles.add(roleDao.getOne(1L));
        user.setRoles(roles);
    }

    @Override
    public UserProfile findByUsername(String username){
        return userDao.findByUsername(username);
    }


}
