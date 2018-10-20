package system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import system.entity.UserProfile;

public interface UserDao extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
}
