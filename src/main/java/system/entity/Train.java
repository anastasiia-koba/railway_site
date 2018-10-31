package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Simple JavaBean domain object that represents a TrainDao.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trains")
public class Train extends BaseEntity {

    @Column(name = "trainname")
    private String trainName;

    @Column(name = "places_number")
    private Integer placesNumber;
}
