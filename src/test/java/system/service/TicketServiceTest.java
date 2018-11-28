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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketDao ticketDao;

    @Mock
    private RoutService routService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private UserProfile userProfile;
    private Train train;
    private Rout rout;
    private FinalRout finalRout;
    private Ticket ticket;
    private Set<Ticket> tickets;
    private List<RoutSection> routSections;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setUsername("user");
        userProfile.setPassword("useruser");

        train = new Train("testTrain", 50);
        train.setId(1L);

        rout = new Rout();
        rout.setId(1L);
        rout.setRoutName("testRout");
        rout.setStartStation(new Station("station1"));
        rout.setEndStation(new Station("station4"));

        finalRout = new FinalRout();
        finalRout.setId(1L);
        finalRout.setRout(rout);
        finalRout.setTrain(train);
        finalRout.setDate(LocalDate.of(2018, 11, 25));

        ticket = new Ticket();
        ticket.setPrice(300);
        ticket.setUser(userProfile);
        ticket.setFinalRout(finalRout);
        ticket.setStartStation(new Station("station2"));
        ticket.setEndStation(new Station("station3"));

        tickets = Stream.of(ticket).collect(Collectors.toSet());

        RoutSection routSection = new RoutSection();
        routSection.setDeparture(new Station("station2"));
        routSection.setDestination(new Station("station3"));
        routSection.setPrice(150);
        routSection.setDistance(100);
        routSection.setDepartureTime(LocalTime.of(14, 10));
        routSection.setArrivalTime(LocalTime.of(17, 30));

        RoutSection routSection2 = new RoutSection();
        routSection2.setDeparture(new Station("station3"));
        routSection2.setDestination(new Station("station4"));
        routSection2.setDistance(100);
        routSection2.setPrice(50);
        routSection2.setDepartureTime(LocalTime.of(17, 40));
        routSection2.setArrivalTime(LocalTime.of(19, 50));
        routSections = Stream.of(routSection, routSection2).collect(Collectors.toList());
    }

    @Test
    public void create() throws DaoException {
        ticketService.create(ticket);
        verify(ticketDao, times(1)).create(ticket);
        verify(ticketDao, never()).update(ticket);
        verify(ticketDao, never()).remove(ticket);
    }

    @Test
    public void save() throws DaoException {
        ticket.setPrice(400);

        ticketService.save(ticket);
        verify(ticketDao, times(1)).update(ticket);
        verify(ticketDao, never()).create(ticket);
        verify(ticketDao, never()).remove(ticket);
    }

    @Test
    public void delete() throws DaoException {
        ticketService.delete(ticket);

        verify(ticketDao, times(1)).remove(ticket);
    }

    @Test
    public void findById() throws DaoException {
        when(ticketDao.findById(1L)).thenReturn(ticket);

        Ticket result = ticketService.findById(1L);
        assertNotNull(result);
        assertEquals("testRout", result.getFinalRout().getRout().getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void findByUser() throws DaoException {
        when(ticketDao.findByUser(userProfile)).thenReturn(tickets);

        Set<Ticket> result = ticketService.findByUser(userProfile);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void findByFinalRout() throws DaoException {
        when(ticketDao.findByFinalRout(finalRout)).thenReturn(tickets);

        Set<Ticket> result = ticketService.findByFinalRout(finalRout);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void findCountTicketsByFinalRoutAndStartAndEndStations() throws DaoException {
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
        when(ticketDao.isAnyBodyInFinalRoutWithUserData(finalRout, userProfile)).thenReturn(true);

        Boolean result = ticketService.isAnyBodyInFinalRoutWithUserData(finalRout, userProfile);
        assertEquals(true, result);
    }

    @Test
    public void getMapFreePlacesInCustomRout() throws DaoException {
        Set<FinalRout> finalRouts = Stream.of(finalRout).collect(Collectors.toSet());

        Station start = new Station("station2");
        Station end = new Station("station4");

        when(routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, start, end)).thenReturn(routSections);
        when(routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, ticket.getStartStation(), ticket.getEndStation())).thenReturn(routSections);
        when(ticketDao.findByFinalRout(finalRout)).thenReturn(tickets);

        Map<Long, Integer> result = ticketService.getMapFreePlacesInCustomRout(finalRouts, start, end);
        assertEquals(true, result.containsValue(49));
    }
}