package system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a User.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserProfile extends BaseEntity {

    @NotNull(message = "This field is required.")
    @Size(min=5, max=30, message = "Username must be between 5 and 32 characters.")
    @Column(name = "username")
    private String username;

    @NotNull(message = "This field is required.")
    @Size(min = 8, message = "Password must be over 8 characters.")
    @Column(name = "password")
    private String password;

    @NotNull(message = "This field is required.")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "This field is required.")
    @Column(name = "firstname")
    private String firstname;

    @NotNull(message = "This field is required.")
    @Column(name = "birthdate")
    private Date birthDate;

    @NotNull(message = "This field is required.")
    @Transient
    private String confirmPassword;

    @Transient
    private boolean validPasswords = isValidPasswords();

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @AssertTrue(message = "Password don't match.")
    public boolean isValidPasswords() {
        if (password != null &&  confirmPassword != null  && password.equals(confirmPassword) ) {
            return true;
        }
        return false;
    }
}
