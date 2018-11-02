package system.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Station.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stations")
public class Station extends BaseEntity {

    @NotNull
    @Column(name = "stationname")
    private String stationName;

    @OneToMany(mappedBy = "departure",
               cascade = CascadeType.ALL)
    private Set<RoutSection> sectionsFrom;

    @OneToMany(mappedBy = "destination",
            cascade = CascadeType.ALL)
    private Set<RoutSection> sectionsTo;

    @OneToMany(mappedBy = "startStation",
            cascade = CascadeType.ALL)
    private Set<Rout> routsFrom;

    @OneToMany(mappedBy = "endStation",
            cascade = CascadeType.ALL)
    private Set<Rout> routsTo;

    public Station(String stationName) {
        this.stationName = stationName;
    }
}
