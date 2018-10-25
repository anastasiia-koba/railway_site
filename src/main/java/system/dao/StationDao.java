package system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import system.entity.Station;

import java.util.List;

public interface StationDao extends JpaRepository<Station, Long> {
    Station findByStationName(String stationName);

    List<Station> findAll();
}
