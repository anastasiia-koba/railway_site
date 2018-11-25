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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() throws DaoException {
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));
        routService.save(rout);

        verify(routDao, times(1)).create(rout);
        verify(routDao, never()).update(rout);
        verify(routDao, never()).remove(rout);
    }

    @Test
    public void testDelete() throws DaoException {
        when(routDao.findByName("testRout")).thenReturn(new Rout("testRout", new Station("station1"),
                new Station("station2")));

        Rout rout = routService.findByName("testRout");
        routService.delete(rout);

        verify(routDao, times(1)).remove(rout);
    }

    @Test
    public void testFindById() throws DaoException {
        Rout rout = new Rout("testRout");
        when(routDao.findById(1L)).thenReturn(rout);

        Rout result = routService.findById(1L);
        assertEquals("testRout", result.getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByName() throws DaoException {
        Rout rout = new Rout("testRout");
        when(routDao.findByName("testRout")).thenReturn(rout);

        Rout result = routService.findByName("testRout");
        assertNotNull(result);
        assertEquals("testRout", result.getRoutName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Rout> stationList = new ArrayList<>();
        stationList.add(new Rout("rout 1"));
        stationList.add(new Rout("rout 2"));
        stationList.add(new Rout("rout 3"));

        when(routDao.findAll()).thenReturn(stationList);

        List<Rout> result = routService.findAll();
        assertEquals(3, result.size());
    }

    @Test
    public void testFindByStartStationAndEndStation() throws DaoException {
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station2"));
        List<Rout> routList = new ArrayList<>();
        routList.add(rout);
        when(routDao.findByStartStationAndEndStation(new Station("station1"),
                new Station("station2"))).thenReturn(routList);

        List<Rout> result = routService.findByStartStationAndEndStation(new Station("station1"),
                new Station("station2"));

        assertEquals("testRout", result.get(0).getRoutName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testFindByRoutSection() throws DaoException {
        RoutSection routSection = new RoutSection(new Station("station1"), new Station("station2"),
                200, 100, LocalTime.of(10, 10), LocalTime.of(13, 50));

        Rout rout = new Rout("testRout");
        List<Rout> routs = new ArrayList<>();
        routs.add(rout);
        when(routDao.findByRoutSection(routSection)).thenReturn(routs);

        List<Rout> result = routService.findByRoutSection(routSection);
        assertEquals("testRout", result.get(0).getRoutName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testGetRoutSectionInRout() throws DaoException {
        RoutSection routSection1 = new RoutSection(new Station("station1"), new Station("station2"),
                200, 100, LocalTime.of(10, 10), LocalTime.of(13, 50));

        RoutSection routSection2 = new RoutSection(new Station("station3"), new Station("station4"),
                100, 50, LocalTime.of(17, 40), LocalTime.of(19, 50));

        RoutSection routSection3 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        Set<RoutSection> routSections = new HashSet<>();
        routSections.add(routSection1);
        routSections.add(routSection2);
        routSections.add(routSection3);

        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

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
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        RoutSection routSection = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        Station station = new Station("station2");
        when(routDao.getRoutSectionByRoutAndDepartureStation(rout, station)).thenReturn(routSection);

        RoutSection result = routService.getRoutSectionByRoutAndDepartureStation(rout, station);

        assertEquals("station2", result.getDeparture().getStationName());
        assertEquals("station3", result.getDestination().getStationName());
    }

    @Test
    public void testGetRoutSectionByRoutAndDestinationStation() throws DaoException {
        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        RoutSection routSection = new RoutSection(new Station("station1"), new Station("station2"),
                200, 100, LocalTime.of(10, 10), LocalTime.of(13, 50));

        Station station = new Station("station2");
        when(routDao.getRoutSectionByRoutAndDestinationStation(rout, station)).thenReturn(routSection);

        RoutSection result = routService.getRoutSectionByRoutAndDestinationStation(rout, station);

        assertEquals("station1", result.getDeparture().getStationName());
        assertEquals("station2", result.getDestination().getStationName());
    }

    @Test
    public void testGetRoutSectionsInRoutBetweenDepartureAndDestination() throws DaoException {

        RoutSection routSection2 = new RoutSection(new Station("station3"), new Station("station4"),
                100, 50, LocalTime.of(17, 40), LocalTime.of(19, 50));

        RoutSection routSection3 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        Set<RoutSection> routSections = new HashSet<>();
        routSections.add(routSection2);
        routSections.add(routSection3);

        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        Station departure = new Station("station2");
        Station destination = new Station("station4");

        when(routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination)).thenReturn(routSections);

        List<RoutSection> result = routService.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination);
        assertEquals("station2", result.get(0).getDeparture().getStationName());
        assertEquals("station3", result.get(1).getDeparture().getStationName());
        assertEquals("station3", result.get(0).getDestination().getStationName());
        assertEquals("station4", result.get(1).getDestination().getStationName());
    }

    @Test
    public void testGetPriceInRoutBetweenDepartureAndDestination() throws DaoException {
        RoutSection routSection1 = new RoutSection(new Station("station1"), new Station("station2"),
                200, 100, LocalTime.of(10, 10), LocalTime.of(13, 50));

        RoutSection routSection2 = new RoutSection(new Station("station3"), new Station("station4"),
                100, 50, LocalTime.of(17, 40), LocalTime.of(19, 50));

        RoutSection routSection3 = new RoutSection(new Station("station2"), new Station("station3"),
                200, 150, LocalTime.of(14, 10), LocalTime.of(17,30));

        Set<RoutSection> routSections = new HashSet<>();
        routSections.add(routSection1);
        routSections.add(routSection2);
        routSections.add(routSection3);

        Rout rout = new Rout("testRout", new Station("station1"),
                new Station("station4"));

        Station departure = new Station("station1");
        Station destination = new Station("station4");

        when(routDao.getRoutSectionsInRoutBetweenDepartureAndDestination(rout, departure, destination)).thenReturn(routSections);

        Integer price = routService.getPriceInRoutBetweenDepartureAndDestination(rout, departure, destination);

        assertEquals(Long.valueOf(300), Long.valueOf(price));
    }
}
