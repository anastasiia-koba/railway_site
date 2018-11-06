package system.service.api;

import system.entity.UserProfile;

/**
 * Service class for {@link system.entity.UserProfile}
 */
public interface UserService {
    void save(UserProfile user);
    void delete(UserProfile user);
    UserProfile findByUsername(String username);
    UserProfile findById(Long id);
}
