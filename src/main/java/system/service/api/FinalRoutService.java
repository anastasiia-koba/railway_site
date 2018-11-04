package system.service.api;

import system.entity.FinalRout;

import java.util.Set;

/**
 * Service class for {@link system.entity.FinalRout}
 */
public interface FinalRoutService {
    void create(FinalRout finalRout);
    void save(FinalRout finalRout);
    void delete(FinalRout finalRout);
    FinalRout findById(Long id);
    Set<FinalRout> findAll();
}
