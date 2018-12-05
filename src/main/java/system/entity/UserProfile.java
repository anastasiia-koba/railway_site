package system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "profiles")
@SQLDelete(sql="UPDATE profiles SET deleted = true WHERE id = ?")
@Where(clause="deleted = false")
public class UserProfile extends BaseEntity {

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

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserData userData;
}
