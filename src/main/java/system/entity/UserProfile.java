package system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
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
@SQLDelete(sql="UPDATE users SET deleted = true WHERE id = ?")
@Where(clause="deleted = false")
public class UserProfile extends BaseEntity {

    @NotBlank(message = "This field is required.")
    @Size(min=5, max=30, message = "Username must be between 5 and 32 characters.")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "This field is required.")
    @Size(min = 8, message = "Password must be over 8 characters.")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "This field is required.")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "This field is required.")
    @Column(name = "firstname")
    private String firstname;

    @NotNull(message = "This field is required.")
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birthdate")
    private LocalDate birthDate;

    @NotBlank(message = "This field is required.")
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
