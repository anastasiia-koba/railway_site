package system.dao.api;

import system.entity.FinalRout;

import java.sql.Date;
import java.util.Set;

public interface FinalRoutDao extends Dao<Long, FinalRout> {
    Set<FinalRout> findByDate(Date date);
    Set<FinalRout> findAll();
}
