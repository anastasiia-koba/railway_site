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


    //private Set<Rout> routs;

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
}
