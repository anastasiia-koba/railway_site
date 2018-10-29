package system.service.api;

/**
 * Service for Security
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
