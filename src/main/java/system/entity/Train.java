package system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Simple JavaBean domain object that represents a Train.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trains")
@SQLDelete(sql="UPDATE trains SET deleted = true WHERE id = ?")
@Where(clause="deleted = false")
public class Train extends BaseEntity {

    @NotBlank(message = "This field is required.")
    @Column(name = "trainname")
    private String trainName;

    @NotNull(message = "This field is required.")
    @Digits(integer=4, fraction = 0, message = "This field must be four-digit number or less.")
    @Column(name = "places_number")
    private Integer placesNumber;

    public Train(String trainName) {
        this.trainName = trainName;
    }

    public Train(String trainName, Integer placesNumber) {
        this.trainName = trainName;
        this.placesNumber = placesNumber;
    }
}
