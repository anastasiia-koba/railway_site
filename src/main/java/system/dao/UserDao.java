package system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import system.entity.UserProfile;

public interface UserDao extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserName(String username);
}
