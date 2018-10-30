package system.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Rout.
 */
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

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public Set<RoutSection> getRoutSections() {
        return routSections;
    }

    public void setRoutSections(Set<RoutSection> routSections) {
        this.routSections = routSections;
    }
}
