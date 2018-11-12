package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Rout.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "routs")
public class Rout extends BaseEntity {

    @NotBlank(message = "This field is required.")
    @Column(name = "rout_name")
    private String routName;

    @NotNull(message = "This field is required.")
    @ManyToOne
    @JoinColumn(name = "start_station_id", referencedColumnName = "id", nullable = false)
    private Station startStation;

    @NotNull(message = "This field is required.")
    @ManyToOne
    @JoinColumn(name = "end_station_id", referencedColumnName = "id", nullable = false)
    private Station endStation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "routs_by_sections", joinColumns = @JoinColumn(name = "rout_id"),
            inverseJoinColumns = @JoinColumn(name = "rout_section_id"))
    private Set<RoutSection> routSections;

    public Rout(String routName) {
        this.routName = routName;
    }
}
