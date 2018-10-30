package system.entity;

import javax.persistence.*;

/**
 * Simple JavaBean domain object that represents a TrainDao.
 */
@Entity
@Table(name = "trains")
public class Train extends BaseEntity {

    @Column(name = "trainname")
    private String trainName;

    @Column(name = "places_number")
    private Integer placesNumber;

    public Train() {
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Integer getPlacesNumber() {
        return placesNumber;
    }

    public void setPlacesNumber(Integer placesNumber) {
        this.placesNumber = placesNumber;
    }
}
