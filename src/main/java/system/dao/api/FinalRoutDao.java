package system.dao.api;

import system.entity.FinalRout;
import system.entity.Rout;
import system.entity.Train;

import java.sql.Date;
import java.util.Set;

public interface FinalRoutDao extends Dao<Long, FinalRout> {
    Set<FinalRout> findByDate(Date date);
    Set<FinalRout> findAll();
    FinalRout findByRoutAndTrainAndDate(Rout rout, Train train, Date date);
}
