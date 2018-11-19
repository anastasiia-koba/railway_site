package system.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a minimum RoutSectionDao.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "rout_section")
@SQLDelete(sql="UPDATE rout_section SET deleted = true WHERE id = ?")
@Where(clause="deleted = false")
public class RoutSection extends BaseEntity {

    @NotNull(message = "This field is required.")
    @ManyToOne
    @JoinColumn(name = "departure_id", referencedColumnName = "id", nullable = false)
    private Station departure;

    @NotNull(message = "This field is required.")
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false)
    private Station destination;

    @NotNull(message = "This field is required.")
    @Digits(integer=4, fraction = 0, message = "This field must be four-digit number or less.")
    @Column(name = "distance")
    private Integer distance;

    @NotNull(message = "This field is required.")
    @Digits(integer=4, fraction = 0, message = "This field must be four-digit number or less.")
    @Column(name = "price")
    private Integer price;

    @NotNull(message = "This field is required.")
    @Column(name = "departure_time")
    private LocalTime departureTime;

    @NotNull(message = "This field is required.")
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "routSections")
    private Set<Rout> routs;
}
