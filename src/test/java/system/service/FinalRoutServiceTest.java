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

    private Train train;
    private Rout rout;
    private FinalRout finalRout;
    private FinalRout finalRout2;
    private Set<FinalRout> finalRouts1;
    private Set<FinalRout> finalRouts2;
    private RoutSection routSection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        train = new Train("testTrain", 50);
        train.setId(1L);

        rout = new Rout();
        rout.setId(1L);
        rout.setRoutName("testRout");
        rout.setStartStation(new Station("station1"));
        rout.setEndStation(new Station("station4"));

        finalRout = new FinalRout();
        finalRout.setRout(rout);
        finalRout.setTrain(train);
        finalRout.setDate(LocalDate.of(2018, 11, 25));

        Train train2 = new Train("testTrain2", 50);
        train2.setId(2L);

        Rout rout2 = new Rout();
        rout2.setId(2L);
        rout2.setRoutName("testRout3");
        rout2.setStartStation(new Station("station2"));
        rout2.setEndStation(new Station("station3"));

        finalRout2 = new FinalRout();
        finalRout2.setTrain(train2);
        finalRout2.setRout(rout2);
        finalRout2.setDate(LocalDate.of(2018, 11, 20));
        finalRout2.setId(2L);

        finalRouts1 = Stream.of(finalRout).collect(Collectors.toSet());
        finalRouts2 = Stream.of(finalRout, finalRout2).collect(Collectors.toSet());

        routSection = new RoutSection();
        routSection.setDestination(new Station("station3"));
        routSection.setDeparture(new Station("station2"));
        routSection.setPrice(150);
        routSection.setDistance(100);
        routSection.setDepartureTime(LocalTime.of(14, 10));
        routSection.setArrivalTime(LocalTime.of(17, 30));
    }

    @Test
    public void save() throws DaoException {
        finalRoutService.save(finalRout);
        verify(finalRoutDao, times(1)).create(finalRout);
        verify(finalRoutDao, never()).update(finalRout);
        verify(finalRoutDao, never()).remove(finalRout);
    }

    @Test
    public void delete() throws DaoException {
        finalRoutService.delete(finalRout);

        verify(finalRoutDao, times(1)).remove(finalRout);
    }

    @Test
    public void findById() throws DaoException {
        when(finalRoutDao.findById(1L)).thenReturn(finalRout);

        FinalRout result = finalRoutService.findById(1L);
        assertNotNull(result);
        assertEquals("testTrain", result.getTrain().getTrainName());
        assertEquals("testRout", result.getRout().getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void findAll() throws DaoException {
        when(finalRoutDao.findAll()).thenReturn(finalRouts2);

        Set<FinalRout> result = finalRoutService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void findByDate() throws DaoException {
        when(finalRoutDao.findByDate(LocalDate.of(2018, 11, 25))).thenReturn(finalRouts1);

        Set<FinalRout> result = finalRoutService.findByDate(LocalDate.of(2018, 11, 25));

        assertEquals("testRout", (new ArrayList<>(result)).get(0).getRout().getRoutName());
        assertEquals("testTrain", (new ArrayList<>(result)).get(0).getTrain().getTrainName());
    }

    @Test
    public void findByStationAndDate() throws DaoException {
        when(finalRoutDao.findByStationAndDate(new Station("station1"),
                LocalDate.of(2018, 11, 25))).thenReturn(finalRouts1);

        Set<FinalRout> result = finalRoutService.findByStationAndDate(new Station("station1"),
                LocalDate.of(2018, 11, 25));

        assertEquals("testRout", (new ArrayList<>(result)).get(0).getRout().getRoutName());
        assertEquals("testTrain", (new ArrayList<>(result)).get(0).getTrain().getTrainName());
    }

    @Test
    public void findByStationToStationOnDate() throws DaoException {
        when(finalRoutDao.findByStationToStationOnDate(new Station("station2"), new Station("station3"),
                LocalDate.of(2018, 11, 25))).thenReturn(finalRouts2);

        Set<FinalRout> result = finalRoutService.findByStationToStationOnDate(new Station("station2"),
                new Station("station3"),
                LocalDate.of(2018, 11, 25));

        assertEquals(true, result.contains(finalRout));
        assertEquals(true, result.contains(finalRout2));
    }

    @Test
    public void findByRoutAndTrainAndDate() throws DaoException {
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
        when(routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), finalRout.getRout().getStartStation())).
                thenReturn(routSection);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapDeparture(new ArrayList<>(finalRouts1));
        assertEquals(true, timeMap.containsValue(LocalTime.of(14, 10)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapArrival() {
        when(routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), finalRout.getRout().getEndStation())).
                thenReturn(routSection);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapArrival(new ArrayList<>(finalRouts1));
        assertEquals(true, timeMap.containsValue(LocalTime.of(17, 30)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapDepartureByStation() {
        Station station = new Station("station2");

        when(routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), station)).
                thenReturn(routSection);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapDepartureByStation(finalRouts1, station);
        assertEquals(true, timeMap.containsValue(LocalTime.of(14, 10)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapArrivalByStation() {
        Station station = new Station("station3");

        when(routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), station)).
                thenReturn(routSection);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapArrivalByStation(finalRouts1, station);
        assertEquals(true, timeMap.containsValue(LocalTime.of(17, 30)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapTimeInTravel() {
        Station from = new Station("station2");
        Station to = new Station("station4");

        RoutSection routSection2 = new RoutSection();
        routSection2.setDeparture(new Station("station3"));
        routSection2.setDestination(new Station("station4"));
        routSection2.setDistance(100);
        routSection2.setPrice(50);
        routSection2.setDepartureTime(LocalTime.of(17, 40));
        routSection2.setArrivalTime(LocalTime.of(19, 50));

        when(routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), from)).
                thenReturn(routSection);

        when(routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), to)).
                thenReturn(routSection2);

        Map<Long, LocalTime> timeMap = finalRoutService.getMapTimeInTravel(finalRouts1, from, to);
        assertEquals(true, timeMap.containsValue(LocalTime.of(5, 40)));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void getMapPriceInCustomRout() {
        Station from = new Station("station2");
        Station to = new Station("station4");

        when(routService.getPriceInRoutBetweenDepartureAndDestination(finalRout.getRout(), from, to)).thenReturn(100);

        Map<Long, Integer> timeMap = finalRoutService.getMapPriceInCustomRout(finalRouts1, from, to);
        assertEquals(true, timeMap.containsValue(100));
        assertEquals(1, timeMap.size());
    }

    @Test
    public void isDepartureTimeIn10Minutes() {
        when(routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), new Station("station2"))).thenReturn(routSection);

        Boolean result = finalRoutService.isDepartureTimeIn10Minutes(finalRout, new Station("station2"));
        assertEquals(true, result);
    }
}