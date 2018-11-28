package system.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.DaoException;
import system.dao.api.RoutDao;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.impl.RoutServiceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class RoutServiceTest {

    @Mock
    private RoutDao routDao;

    @InjectMocks
    private RoutServiceImpl routService;

    private Rout rout;
    private List<Rout> routs;
    private List<RoutSection> routSections;
    private RoutSection routSection;
    private Station start;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        rout = new Rout();
        rout.setRoutName("testRout");
        rout.setStartStation(new Station("station1"));
        rout.setEndStation(new Station("station4"));

        routs = Stream.of(rout).collect(Collectors.toList());

        start = new Station("station1");
        start.setId(1L);
        routSection = new RoutSection();
        routSection.setDeparture(start);
        routSection.setDestination(new Station("station2"));
        routSection.setPrice(200);
        routSection.setDistance(100);
        routSection.setDepartureTime(LocalTime.of(10, 10));
        routSection.setArrivalTime(LocalTime.of(13, 50));

        RoutSection routSection2 = new RoutSection();
        routSection2.setDeparture(new Station("station2"));
        routSection2.setDestination(new Station("station3"));
        routSection2.setPrice(150);
        routSection2.setDistance(100);
        routSection2.setDepartureTime(LocalTime.of(14, 10));
        routSection2.setArrivalTime(LocalTime.of(17, 30));

        RoutSection routSection3 = new RoutSection();
        routSection3.setDeparture(new Station("station3"));
        routSection3.setDestination(new Station("station4"));
        routSection3.setDistance(100);
        routSection3.setPrice(50);
        routSection3.setDepartureTime(LocalTime.of(17, 40));
        routSection3.setArrivalTime(LocalTime.of(19, 50));

        routSections = Stream.of(routSection, routSection3, routSection2).collect(Collectors.toList());
    }

    @Test
    public void testSave() throws DaoException {
        routService.save(rout);

        verify(routDao, times(1)).create(rout);
        verify(routDao, never()).update(rout);
        verify(routDao, never()).remove(rout);
    }

    @Test
    public void testDelete() throws DaoException {
        routService.delete(rout);

        verify(routDao, times(1)).remove(rout);
    }

    @Test
    public void testFindById() throws DaoException {
        when(routDao.findById(1L)).thenReturn(rout);

        Rout result = routService.findById(1L);
        assertEquals("testRout", result.getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByName() throws DaoException {
        when(routDao.findByName("testRout")).thenReturn(rout);

        Rout result = routService.findByName("testRout");
        assertNotNull(result);
        assertEquals("testRout", result.getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Rout> routList = new ArrayList<>();
        routList.add(new Rout("rout 1"));
        routList.add(new Rout("rout 2"));
        routList.add(new Rout("rout 3"));

        when(routDao.findAll()).thenReturn(routList);

        List<Rout> result = routService.findAll();
        assertEquals(3, result.size());
    }

    @Test
    public void testFindByStartStationAndEndStation() throws DaoException {
        when(routDao.findByStartStationAndEndStation(new Station("station1"),
                new Station("station2"))).thenReturn(routs);

        List<Rout> result = routService.findByStartStationAndEndStation(new Station("station1"),
                new Station("station2"));

        assertEquals("testRout", result.get(0).getRoutName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testFindByRoutSection() throws DaoException {
        when(routDao.findByRoutSection(routSection)).thenReturn(routs);

        List<Rout> result = routService.findByRoutSection(routSection);
        assertEquals("testRout", result.get(0).getRoutName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testGetRoutSectionInRout() throws DaoException {
        when(routDao.getRoutSectionInRout(rout)).thenReturn(routSections);

        List<RoutSection> result = routService.getRoutSectionInRout(rout);
        assertEquals("station1", result.get(0).getDeparture().getStationName());
        assertEquals("station2", result.get(1).getDeparture().getStationName());
        assertEquals("station3", result.get(2).getDeparture().getStationName());
        assertEquals("station2", result.get(0).getDestination().getStationName());
        assertEquals("station3", result.get(1).getDestination().getStationName());
        assertEquals("station4", result.get(2).getDestination().getStationName());
    }

    @Test
    public void testGetRoutSectionByRoutAndDepartureStation() throws DaoException {
        Station station = new Station("station1");
        when(routDao.getRoutSectionByRoutAndDepartureStation(rout, station)).thenReturn(routSection);

        RoutSection result = routService.getRoutSectionByRoutAndDepartureStation(rout, station);

        assertEquals("station1", result.getDeparture().getStationName());
        assertEquals("station2", result.getDestination().getStationName());
    }

    @Test
    public void testGetRoutSectionByRoutAndDestinationStation() throws DaoException {
        Station station = new Station("station2");
        when(routDao.getRoutSectionByRoutAndDestinationStation(rout, station)).thenReturn(routSection);

        RoutSection result = routService.getRoutSectionByRoutAndDestinationStation(rout, station);

        assertEquals("station1", result.getDeparture().getStationName());
        assertEquals("station2", result.getDestination().getStationName());
    }

    @Test
    public void testGetRoutSectionsInRoutBetweenDepartureAndDestination() throws DaoException {
        Station departure = new Station("station1");
        Station destination = new Station("station4");

        when(routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination)).thenReturn(routSections);

        List<RoutSection> result = routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination);
        assertEquals("station1", result.get(0).getDeparture().getStationName());
        assertEquals("station2", result.get(1).getDeparture().getStationName());
        assertEquals("station3", result.get(2).getDeparture().getStationName());
        assertEquals("station2", result.get(0).getDestination().getStationName());
        assertEquals("station3", result.get(1).getDestination().getStationName());
        assertEquals("station4", result.get(2).getDestination().getStationName());
    }

    @Test
    public void testGetPriceInRoutBetweenDepartureAndDestination() throws DaoException {
        Station departure = new Station("station1");
        Station destination = new Station("station4");

        when(routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination)).thenReturn(routSections);

        Integer price = routService.getPriceInRoutBetweenDepartureAndDestination(rout, departure, destination);

        assertEquals(Long.valueOf(400), Long.valueOf(price));
    }

    @Test
    public void testSortRoutSections() {
        List<RoutSection> result = new ArrayList<>();

        routService.sortRoutSections(routSections, result, start);
        assertEquals(3, result.size());
        assertEquals(0, routSections.size());
    }

    @Test
    public void testIsRoutWellBuilt() throws DaoException {
        when(routDao.getRoutSectionInRout(rout)).thenReturn(routSections);

        Boolean res = routService.isRoutWellBuilt(rout);
        assertEquals(true, res);
    }
}
