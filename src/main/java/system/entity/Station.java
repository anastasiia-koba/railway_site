package system.entity;


import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Station.
 */
@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "stationname")
    private String stationName;


    @OneToMany(mappedBy = "departure_id",
               cascade = CascadeType.ALL)
    private Set<RoutSection> routsFrom;

    @OneToMany(mappedBy = "destination_id",
            cascade = CascadeType.ALL)
    private Set<RoutSection> routsTo;

    public Station() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public Set<RoutSection> getRoutsFrom() {
        return routsFrom;
    }

    public void setRoutsFrom(Set<RoutSection> routsFrom) {
        this.routsFrom = routsFrom;
    }

    public Set<RoutSection> getRoutsTo() {
        return routsTo;
    }

    public void setRoutsTo(Set<RoutSection> routsTo) {
        this.routsTo = routsTo;
    }
}
