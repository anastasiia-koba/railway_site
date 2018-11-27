package system.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import system.DaoException;
import system.config.AppSecurityConfig;
import system.dao.api.RoleDao;
import system.dao.api.UserDao;
import system.entity.Station;
import system.entity.UserProfile;
import system.service.impl.UserDetailsServiceImpl;
import system.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        when(bCryptPasswordEncoder.encode(userProfile.getPassword())).thenReturn("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq");
        userService.save(userProfile);

        verify(userDao, times(1)).create(userProfile);
        verify(userDao, never()).update(userProfile);
        verify(userDao, never()).remove(userProfile);
    }

    @Test
    public void testDelete() throws DaoException {
        when(userDao.findByUsername("testUser")).thenReturn(new UserProfile("testUser", "useruser"));

        UserProfile testUser = userService.findByUsername("testUser");
        userService.delete(testUser);

        verify(userDao, times(1)).remove(testUser);
    }

    @Test
    public void testFindById() throws DaoException {
        UserProfile user = new UserProfile("user 1", "useruser");
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq");
        userService.save(user);

        when(userDao.findById(1L)).thenReturn(user);

        UserProfile result = userService.findById(1L);
        assertEquals("user 1", result.getUsername());
        assertEquals("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq", result.getPassword());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByUsername() throws DaoException {
        UserProfile user = new UserProfile("user 2", "useruser");
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq");
        userService.save(user);

        when(userDao.findByUsername("user 2")).thenReturn(user);

        UserProfile result = userService.findByUsername("user 2");
        assertNotNull(result);
        assertEquals("user 2", result.getUsername());
        assertEquals("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq", result.getPassword());
        assertEquals(false, result.getDeleted());
    }
}
