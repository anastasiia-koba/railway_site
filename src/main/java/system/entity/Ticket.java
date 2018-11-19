package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Simple JavaBean domain object that represents a Ticket.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
@SQLDelete(sql="UPDATE tickets SET deleted = true WHERE id = ?")
@Where(clause="deleted = false")
public class Ticket extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserProfile user;

    @ManyToOne
    @JoinColumn(name = "train_rout_id", referencedColumnName = "id", nullable = false)
    private FinalRout finalRout;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "start_station_id", referencedColumnName = "id", nullable = false)
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "end_station_id", referencedColumnName = "id", nullable = false)
    private Station endStation;
}
