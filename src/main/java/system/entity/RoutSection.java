package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a minimum RoutSectionDao.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rout_section")
public class RoutSection extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "departure_id", referencedColumnName = "id", nullable = false)
    private Station departure;

    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false)
    private Station destination;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "price")
    private Integer price;

    @Column(name = "departure_time")
    private Time departureTime;

    @Column(name = "arrival_time")
    private Time arrivalTime;

    @ManyToMany(mappedBy = "routSections")
    private Set<Rout> routs;
}
