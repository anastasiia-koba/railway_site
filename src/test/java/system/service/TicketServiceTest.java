package system.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.DaoException;
import system.dao.api.TicketDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;
import system.entity.Ticket;
import system.entity.UserProfile;
import system.entity.Train;
import system.entity.FinalRout;
import system.service.api.RoutService;
import system.service.impl.TicketServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketDao ticketDao;

    @Mock
    private RoutService routService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));

        ticketService.create(ticket);
        verify(ticketDao, times(1)).create(ticket);
        verify(ticketDao, never()).update(ticket);
        verify(ticketDao, never()).remove(ticket);
    }

    @Test
    public void save() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));
        ticket.setId(1L);

        when(ticketDao.findById(1L)).thenReturn(ticket);

        Ticket result = ticketService.findById(1L);

        result.setPrice(400);

        ticketService.save(result);
        verify(ticketDao, times(1)).update(result);
        verify(ticketDao, never()).create(result);
        verify(ticketDao, never()).remove(result);
    }

    @Test
    public void delete() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));

        when(ticketDao.findById(1L)).thenReturn(ticket);

        Ticket result = ticketService.findById(1L);
        ticketService.delete(result);

        verify(ticketDao, times(1)).remove(result);
    }

    @Test
    public void findById() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));

        when(ticketDao.findById(1L)).thenReturn(ticket);

        Ticket result = ticketService.findById(1L);
        assertEquals("testRout", result.getFinalRout().getRout().getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void findByUser() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));
        Set<Ticket> tickets = Stream.of(ticket).collect(Collectors.toSet());

        when(ticketDao.findByUser(userProfile)).thenReturn(tickets);

        Set<Ticket> result = ticketService.findByUser(userProfile);
        assertEquals(1, result.size());
    }

    @Test
    public void findByFinalRout() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));

        Set<Ticket> tickets = Stream.of(ticket).collect(Collectors.toSet());
        when(ticketDao.findByFinalRout(finalRout)).thenReturn(tickets);

        Set<Ticket> result = ticketService.findByFinalRout(finalRout);
        assertEquals(1, result.size());
    }

    @Test
    public void findCountTicketsByFinalRoutAndStartAndEndStations() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        RoutSection routSection2 = new RoutSection(new Station("station3"), new Station("station4"),
                100, 50, LocalTime.of(17, 40), LocalTime.of(19, 50));

        RoutSection routSection3 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        List<RoutSection> routSections = Stream.of(routSection2, routSection3).collect(Collectors.toList());

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));
        Set<Ticket> tickets = Stream.of(ticket).collect(Collectors.toSet());

        Station start = new Station("station2");
        Station end = new Station("station4");

        when(routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, start, end)).thenReturn(routSections);
        when(routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, ticket.getStartStation(), ticket.getEndStation())).thenReturn(routSections);
        when(ticketDao.findByFinalRout(finalRout)).thenReturn(tickets);

        Integer count = ticketService.findCountTicketsByFinalRoutAndStartAndEndStations(finalRout, start, end);
        assertEquals(Long.valueOf(1), Long.valueOf(count));
    }

    @Test
    public void isAnyBodyInFinalRoutWithUserData() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        when(ticketDao.isAnyBodyInFinalRoutWithUserData(finalRout, userProfile)).thenReturn(true);

        Boolean result = ticketService.isAnyBodyInFinalRoutWithUserData(finalRout, userProfile);
        assertEquals(true, result);
    }

    @Test
    public void getMapFreePlacesInCustomRout() throws DaoException {
        UserProfile userProfile = new UserProfile("testUser", "useruser");

        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));
        Set<FinalRout> finalRouts = Stream.of(finalRout).collect(Collectors.toSet());

        Ticket ticket = new Ticket(userProfile, finalRout, 300, new Station("station2"),
                new Station("station3"));
        Set<Ticket> tickets = Stream.of(ticket).collect(Collectors.toSet());

        RoutSection routSection2 = new RoutSection(new Station("station3"), new Station("station4"),
                100, 50, LocalTime.of(17, 40), LocalTime.of(19, 50));

        RoutSection routSection3 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        List<RoutSection> routSections = Stream.of(routSection2, routSection3).collect(Collectors.toList());

        Station start = new Station("station2");
        Station end = new Station("station4");

        when(routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, start, end)).thenReturn(routSections);
        when(routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, ticket.getStartStation(), ticket.getEndStation())).thenReturn(routSections);
        when(ticketDao.findByFinalRout(finalRout)).thenReturn(tickets);

        Map<Long, Integer> result = ticketService.getMapFreePlacesInCustomRout(finalRouts, start, end);
        assertEquals(true, result.containsValue(49));
    }
}