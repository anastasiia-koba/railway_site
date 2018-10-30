package system.entity;


import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Station.
 */
@Entity
@Table(name = "stations")
public class Station extends BaseEntity {

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

    public Station() {
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

    public Set<RoutSection> getSectionsFrom() {
        return sectionsFrom;
    }

    public void setSectionsFrom(Set<RoutSection> routsFrom) {
        this.sectionsFrom = routsFrom;
    }

    public Set<RoutSection> getSectionsTo() {
        return sectionsTo;
    }

    public void setSectionsTo(Set<RoutSection> routsTo) {
        this.sectionsTo = routsTo;
    }

    public Set<Rout> getRoutsFrom() {
        return routsFrom;
    }

    public void setRoutsFrom(Set<Rout> routsFrom) {
        this.routsFrom = routsFrom;
    }

    public Set<Rout> getRoutsTo() {
        return routsTo;
    }

    public void setRoutsTo(Set<Rout> routsTo) {
        this.routsTo = routsTo;
    }
}
