package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

/**
 * Simple JavaBean domain object that represents a date for Rout and Train.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trains_routs")
public class FinalRout extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "rout_id", referencedColumnName = "id", nullable = false)
    private Rout rout;

    @Column(name = "date")
    private Date date;
}
