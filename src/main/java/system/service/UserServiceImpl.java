package system.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserProfile user){
        user.setPass(bCryptPasswordEncoder.encode(user.getPass()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1L));
        user.setRoles(roles);
    }

    @Override
    public UserProfile findByUserName(String username){
        return userDao.findByUserName(username);
    }


}
