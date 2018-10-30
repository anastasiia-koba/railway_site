package system.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a minimum RoutSectionDao.
 */
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

    public RoutSection() {
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Station getDeparture() {
        return departure;
    }

    public void setDeparture(Station departure_id) {
        this.departure = departure_id;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination_id) {
        this.destination = destination_id;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Set<Rout> getRouts() {
        return routs;
    }

    public void setRouts(Set<Rout> routs) {
        this.routs = routs;
    }
}
