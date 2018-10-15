package system.dao;

import system.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Repository
public class UserDao {

    private List<UserProfile> users = Arrays.asList(new UserProfile("user1", "user1"),
            new UserProfile("user2", "user2"));

    public List<UserProfile> getAllUsers(){
        return users;
    }
}
