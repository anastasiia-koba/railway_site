package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean object that represents role of (@link UserProfile)
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
@SQLDelete(sql="UPDATE roles SET deleted = true WHERE id = ?")
@Where(clause="deleted = false")
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserProfile> users;
}
