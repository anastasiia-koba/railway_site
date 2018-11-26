package system.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.DaoException;
import system.dao.api.FinalRoutDao;
import system.entity.*;
import system.service.api.RoutService;
import system.service.impl.FinalRoutServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class FinalRoutServiceTest {

    @Mock
    private FinalRoutDao finalRoutDao;

    @Mock
    private RoutService routService;

    @InjectMocks
    private FinalRoutServiceImpl finalRoutService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save() throws DaoException {
        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        finalRoutService.save(finalRout);
        verify(finalRoutDao, times(1)).create(finalRout);
        verify(finalRoutDao, never()).update(finalRout);
        verify(finalRoutDao, never()).remove(finalRout);
    }

    @Test
    public void delete() throws DaoException {
        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        when(finalRoutDao.findById(1L)).thenReturn(finalRout);

        FinalRout result = finalRoutService.findById(1L);
        finalRoutService.delete(result);

        verify(finalRoutDao, times(1)).remove(result);
    }

    @Test
    public void findById() throws DaoException {
        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        when(finalRoutDao.findById(1L)).thenReturn(finalRout);

        FinalRout result = finalRoutService.findById(1L);
        assertNotNull(result);
        assertEquals("testTrain", result.getTrain().getTrainName());
        assertEquals("testRout", result.getRout().getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void findAll() throws DaoException {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station2"));
        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));
        finalRout1.setId(1L);

        Train train2 = new Train("testTrain2", 50);
        Rout rout2 = new Rout("testRout2", new Station("station2"),
                new Station("station3"));
        FinalRout finalRout2 = new FinalRout(train2, rout2, LocalDate.of(2018, 11, 20));
        finalRout2.setId(2L);

        Set<FinalRout> finalRouts = Stream.of(finalRout1, finalRout2).collect(Collectors.toSet());

        when(finalRoutDao.findAll()).thenReturn(finalRouts);

        Set<FinalRout> result = finalRoutService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void findByDate() throws DaoException {
        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));
        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout);

        when(finalRoutDao.findByDate(LocalDate.of(2018, 11, 25))).thenReturn(finalRouts);

        Set<FinalRout> result = finalRoutService.findByDate(LocalDate.of(2018, 11, 25));

        assertEquals("testRout", (new ArrayList<>(result)).get(0).getRout().getRoutName());
        assertEquals("testTrain", (new ArrayList<>(result)).get(0).getTrain().getTrainName());
    }

    @Test
    public void findByStationAndDate() throws DaoException {
        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));
        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout);

        when(finalRoutDao.findByStationAndDate(new Station("station1"),
                LocalDate.of(2018, 11, 25))).thenReturn(finalRouts);

        Set<FinalRout> result = finalRoutService.findByStationAndDate(new Station("station1"),
                LocalDate.of(2018, 11, 25));

        assertEquals("testRout", (new ArrayList<>(result)).get(0).getRout().getRoutName());
        assertEquals("testTrain", (new ArrayList<>(result)).get(0).getTrain().getTrainName());
    }

    @Test
    public void findByStationToStationOnDate() throws DaoException {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station3"));

        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));
        finalRout1.setId(1L);

        Train train2 = new Train("testTrain2", 50);
        Rout rout2 = new Rout("testRout2", new Station("station2"),
                new Station("station3"));
        FinalRout finalRout2 = new FinalRout(train2, rout2, LocalDate.of(2018, 11, 20));
        finalRout2.setId(2L);

        Set<FinalRout> finalRouts = Stream.of(finalRout1, finalRout2).collect(Collectors.toSet());

        when(finalRoutDao.findByStationToStationOnDate(new Station("station2"), new Station("station3"),
                    LocalDate.of(2018, 11, 25))).thenReturn(finalRouts);

        Set<FinalRout> result = finalRoutService.findByStationToStationOnDate(new Station("station2"),
                new Station("station3"),
                LocalDate.of(2018, 11, 25));

        assertEquals(true, result.contains(finalRout1));
        assertEquals(true, result.contains(finalRout2));
    }

    @Test
    public void findByRoutAndTrainAndDate() throws DaoException {
        Train train = new Train("testTrain", 50);
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));

        FinalRout finalRout = new FinalRout(train, rout, LocalDate.of(2018, 11, 25));

        when(finalRoutDao.findByRoutAndTrainAndDate(rout, train,
                LocalDate.of(2018, 11, 25))).thenReturn(finalRout);

        FinalRout result = finalRoutService.findByRoutAndTrainAndDate(rout, train,
                LocalDate.of(2018, 11, 25));
        assertNotNull(result);
        assertEquals("testTrain", result.getTrain().getTrainName());
        assertEquals("testRout", result.getRout().getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void getMapDeparture() {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station3"));

        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));

        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout1);

        RoutSection routSection1 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        when(routService.getRoutSectionByRoutAndDepartureStation(finalRout1.getRout(), finalRout1.getRout().getStartStation())).
                thenReturn(routSection1);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapDeparture(finalRouts);
        assertEquals(true, timeMap.containsValue(LocalTime.of(14, 10)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapArrival() {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station3"));

        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));

        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout1);

        RoutSection routSection1 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        when(routService.getRoutSectionByRoutAndDestinationStation(finalRout1.getRout(), finalRout1.getRout().getEndStation())).
                thenReturn(routSection1);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapArrival(finalRouts);
        assertEquals(true, timeMap.containsValue(LocalTime.of(17, 30)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapDepartureByStation() {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station3"));

        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));

        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout1);

        RoutSection routSection1 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        Station station = new Station("station2");

        when(routService.getRoutSectionByRoutAndDepartureStation(finalRout1.getRout(), station)).
                thenReturn(routSection1);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapDepartureByStation(finalRouts, station);
        assertEquals(true, timeMap.containsValue(LocalTime.of(14, 10)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapArrivalByStation() {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station3"));

        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));

        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout1);

        RoutSection routSection1 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        Station station = new Station("station3");

        when(routService.getRoutSectionByRoutAndDestinationStation(finalRout1.getRout(), station)).
                thenReturn(routSection1);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapArrivalByStation(finalRouts, station);
        assertEquals(true, timeMap.containsValue(LocalTime.of(17, 30)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapTimeInTravel() {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station3"));

        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));

        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout1);

        RoutSection routSection1 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));
        RoutSection routSection2 = new RoutSection(new Station("station3"), new Station("station4"),
                100, 50, LocalTime.of(17, 40), LocalTime.of(19, 50));

        Station from = new Station("station2");
        Station to = new Station("station4");

        when(routService.getRoutSectionByRoutAndDepartureStation(finalRout1.getRout(), from)).
                thenReturn(routSection1);

        when(routService.getRoutSectionByRoutAndDestinationStation(finalRout1.getRout(), to)).
                thenReturn(routSection2);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapTimeInTravel(finalRouts, from, to);
        assertEquals(true, timeMap.containsValue(LocalTime.of(5, 40)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapPriceInCustomRout() {
        Train train1 = new Train("testTrain1", 50);
        Rout rout1 = new Rout("testRout1", new Station("station1"),
                new Station("station4"));

        FinalRout finalRout1 = new FinalRout(train1, rout1, LocalDate.of(2018, 11, 25));

        Set<FinalRout> finalRouts = new HashSet<>();
        finalRouts.add(finalRout1);

        Station from = new Station("station2");
        Station to = new Station("station4");

        when(routService.getPriceInRoutBetweenDepartureAndDestination(finalRout1.getRout(), from, to)).thenReturn(100);

        Map<Long, Integer> timeMap = finalRoutService.getMapPriceInCustomRout(finalRouts, from, to);
        assertEquals(true, timeMap.containsValue(100));
        assertEquals(1, timeMap.size());
    }
}