package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.api.FinalRoutDao;
import system.entity.FinalRout;
import system.service.api.FinalRoutService;

import java.util.Set;

/**
 *Implementation of {@link system.service.api.FinalRoutService} interface.
 */
@Service
public class FinalRoutServiceImpl implements FinalRoutService {

    @Autowired
    private FinalRoutDao finalRoutDao;

    @Override
    public void create(FinalRout finalRout) {
        finalRoutDao.create(finalRout);
    }

    @Override
    public void save(FinalRout finalRout) {
        finalRoutDao.update(finalRout);
    }

    @Override
    public void delete(FinalRout finalRout) {
        finalRoutDao.remove(finalRout);
    }

    @Override
    public FinalRout findById(Long id) {
        return finalRoutDao.findById(id);
    }

    @Override
    public Set<FinalRout> findAll() {
        return finalRoutDao.findAll();
    }
}
