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
import system.dao.api.UserProfileDao;
import system.entity.UserData;
import system.entity.UserProfile;
import system.service.impl.UserServiceImpl;

import java.time.LocalDate;

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
    private UserProfileDao userProfileDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserData user;
    private UserProfile profile;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        user = new UserData();
        user.setUsername("testUser");
        user.setPassword("useruser");

        profile = new UserProfile();
        profile.setFirstname("Ivan");
        profile.setSurname("Ivanov");
        profile.setBirthDate(LocalDate.of(1960, 10, 11));
        user.setUserProfile(profile);
    }

    @Test
    public void testCreateUser() throws DaoException {
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq");
        userService.createUser(user);

        verify(userDao, times(1)).create(user);
        verify(userDao, never()).update(user);
        verify(userDao, never()).remove(user);
    }

    @Test
    public void testSaveUser() throws DaoException {
        user.setId(1L);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq");
        when(userDao.findById(1L)).thenReturn(user);
        userService.saveUser(user);

        verify(userDao, times(1)).update(user);
        verify(userDao, never()).create(user);
        verify(userDao, never()).remove(user);
    }

    @Test
    public void testCreateProfile() throws DaoException {
        userService.createProfile(profile);

        verify(userProfileDao, times(1)).create(profile);
        verify(userProfileDao, never()).update(profile);
        verify(userProfileDao, never()).remove(profile);
    }

    @Test
    public void testSaveProfile() throws DaoException {
        profile.setId(1L);
        when(userProfileDao.findById(1L)).thenReturn(profile);
        userService.saveProfile(profile);

        verify(userProfileDao, times(1)).update(profile);
        verify(userProfileDao, never()).create(profile);
        verify(userProfileDao, never()).remove(profile);
    }

    @Test
    public void testDeleteUser() throws DaoException {
        userService.deleteUser(user);

        verify(userDao, times(1)).remove(user);
    }

    @Test
    public void testDeleteProfile() throws DaoException {
        userService.deleteProfile(profile);

        verify(userProfileDao, times(1)).remove(profile);
    }

    @Test
    public void testFindById() throws DaoException {
        when(userDao.findById(1L)).thenReturn(user);

        UserData result = userService.findById(1L);
        assertEquals("testUser", result.getUsername());
        assertEquals("useruser", result.getPassword());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindProfileById() throws DaoException {
        when(userProfileDao.findById(1L)).thenReturn(profile);

        UserProfile result = userService.findProfileById(1L);
        assertEquals("Ivan", result.getFirstname());
        assertEquals("Ivanov", result.getSurname());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByUsername() throws DaoException {
        when(userDao.findByUsername("testUser")).thenReturn(user);

        UserData result = userService.findByUsername("testUser");
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("useruser", result.getPassword());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindProfileByUsername() throws DaoException {
        when(userDao.findByUsername("testUser")).thenReturn(user);

        UserProfile result = userService.findProfileByUsername("testUser");
        assertNotNull(result);
        assertEquals("Ivan", result.getFirstname());
        assertEquals("Ivanov", result.getSurname());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByNamesAndDate() throws DaoException {
        LocalDate date = LocalDate.of(1960, 10, 11);
        when(userProfileDao.findByNamesAndDate("Ivanov", "Ivan", date)).thenReturn(profile);

        UserProfile result = userService.findByNamesAndDate("Ivanov", "Ivan", date);
        assertNotNull(result);
        assertEquals("Ivan", result.getFirstname());
        assertEquals("Ivanov", result.getSurname());
        assertEquals(false, result.getDeleted());
    }
}
