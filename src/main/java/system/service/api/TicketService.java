package system.service.api;

import system.entity.FinalRout;
import system.entity.Station;
import system.entity.Ticket;
import system.entity.UserProfile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service class for {@link system.entity.Ticket}
 */
public interface TicketService {
    void create(Ticket ticket);
    List<Ticket> formTickets(List<UserProfile> userProfiles, Station start, Station end, FinalRout finalRout, Integer price);
    String save(List<Ticket> tickets);
    void delete(Ticket ticket);
    Ticket findById(Long id);
    Set<Ticket> findByUser(UserProfile user);
    Set<Ticket> findByFinalRout(FinalRout finalRout);
    Integer findCountTicketsByFinalRoutAndStartAndEndStations(FinalRout finalRout, Station start, Station end);
    Boolean isAnyBodyInFinalRoutWithUserData(FinalRout finalRout, UserProfile user);
    Map<Long, Integer> getMapFreePlacesInCustomRout(Set<FinalRout> finalRouts, Station from, Station to);
}
