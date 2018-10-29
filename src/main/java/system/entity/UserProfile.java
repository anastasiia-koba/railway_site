package system.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a User.
 */
@Entity
@Table(name = "users")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "This field is required.")
    @Size(min=5, max=30, message = "Username must be between 5 and 32 characters.")
    @Column(name = "username")
    private String username;

    @NotNull(message = "This field is required.")
    @Min(value = 8, message = "Password must be over 8 characters.")
    @Column(name = "password")
    private String password;

    //private Date birthDate;

    @NotNull(message = "This field is required.")
    @Transient
    private String confirmPassword;

    @Transient
    private boolean validPasswords = isValidPasswords();

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public UserProfile(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserProfile(String username) {
        this.username = username;
        this.password = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @AssertTrue(message = "Password don't match.")
    public boolean isValidPasswords() {
        if (password != null &&  confirmPassword != null  && password.equals(confirmPassword) ) {
            return true;
        }
        return false;
    }
}
