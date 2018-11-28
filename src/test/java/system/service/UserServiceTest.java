package system.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import system.DaoException;
import system.dao.api.RoleDao;
import system.dao.api.UserDao;
import system.entity.UserProfile;
import system.service.impl.UserServiceImpl;

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

    private UserProfile user;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        user = new UserProfile();
        user.setUsername("testUser");
        user.setPassword("useruser");
    }

    @Test
    public void testSave() throws DaoException {
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq");
        userService.save(user);

        verify(userDao, times(1)).create(user);
        verify(userDao, never()).update(user);
        verify(userDao, never()).remove(user);
    }

    @Test
    public void testDelete() throws DaoException {
        userService.delete(user);

        verify(userDao, times(1)).remove(user);
    }

    @Test
    public void testFindById() throws DaoException {
        when(userDao.findById(1L)).thenReturn(user);

        UserProfile result = userService.findById(1L);
        assertEquals("testUser", result.getUsername());
        assertEquals("useruser", result.getPassword());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByUsername() throws DaoException {
        when(userDao.findByUsername("testUser")).thenReturn(user);

        UserProfile result = userService.findByUsername("testUser");
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("useruser", result.getPassword());
        assertEquals(false, result.getDeleted());
    }
}
