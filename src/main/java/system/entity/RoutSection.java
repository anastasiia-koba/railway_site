package system.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a minimum RoutSection.
 */

@Entity
@Table(name = "rout_section")
public class RoutSection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "departure_id", referencedColumnName = "id", nullable = false)
    private Station departure_id;

    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false)
    private Station destination_id;


    @Column(name = "distance")
    private Long distance;

    @ManyToMany
    private Set<Rout> routs;

    public RoutSection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Station getDeparture_id() {
        return departure_id;
    }

    public void setDeparture_id(Station departure_id) {
        this.departure_id = departure_id;
    }

    public Station getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(Station destination_id) {
        this.destination_id = destination_id;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Set<Rout> getRouts() {
        return routs;
    }

    public void setRouts(Set<Rout> routs) {
        this.routs = routs;
    }
}
