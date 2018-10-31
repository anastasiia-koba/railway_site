package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_station_id", referencedColumnName = "id", nullable = false)
    private Station startStation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_station_id", referencedColumnName = "id", nullable = false)
    private Station endStation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "routs_by_sections", joinColumns = @JoinColumn(name = "rout_id"),
            inverseJoinColumns = @JoinColumn(name = "rout_section_id"))
    private Set<RoutSection> routSections;
}
