package system.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.DaoException;
import system.dao.api.RoutSectionDao;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.impl.RoutSectionServiceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class RoutSectionServiceTest {

    @Mock
    private RoutSectionDao routSectionDao;

    @InjectMocks
    private RoutSectionServiceImpl routSectionService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() throws DaoException {
        RoutSection routSection = new RoutSection(new Station("station1"), new Station("station2"),
                100, 200, LocalTime.of(10, 10), LocalTime.of(13, 50));
        routSectionService.save(routSection);

        verify(routSectionDao, times(1)).create(routSection);
        verify(routSectionDao, never()).update(routSection);
        verify(routSectionDao, never()).remove(routSection);
    }

    @Test
    public void testDelete() throws DaoException {
        when(routSectionDao.findById(1L)).thenReturn(new RoutSection(new Station("station1"), new Station("station2"),
                100, 200, LocalTime.of(10, 10), LocalTime.of(13, 50)));

        RoutSection routSection = routSectionService.findById(1L);
        routSectionService.delete(routSection);

        verify(routSectionDao, times(1)).remove(routSection);
    }

    @Test
    public void testFindById() throws DaoException {
        RoutSection routSection = new RoutSection(new Station("station1"), new Station("station2"),
                100, 200, LocalTime.of(10, 10), LocalTime.of(13, 50));
        when(routSectionDao.findById(1L)).thenReturn(routSection);

        RoutSection result = routSectionService.findById(1L);
        assertEquals("station1", result.getDeparture().getStationName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByDeparture() throws DaoException {
        RoutSection routSection = new RoutSection(new Station("station1"), new Station("station2"),
                100, 200, LocalTime.of(10, 10), LocalTime.of(13, 50));
        List<RoutSection> routSectionList = new ArrayList<>();
        routSectionList.add(routSection);
        when(routSectionDao.findByDeparture(new Station("station1"))).thenReturn(routSectionList);

        List<RoutSection> result = routSectionService.findByDeparture(new Station("station1"));
        assertNotNull(result);
        assertEquals("station2", result.get(0).getDestination().getStationName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testFindByDestination() throws DaoException {
        RoutSection routSection = new RoutSection(new Station("station1"), new Station("station2"),
                200, 100, LocalTime.of(8, 15), LocalTime.of(12, 50));
        List<RoutSection> routSectionList = new ArrayList<>();
        routSectionList.add(routSection);
        when(routSectionDao.findByDestination(new Station("station2"))).thenReturn(routSectionList);

        List<RoutSection> result = routSectionService.findByDestination(new Station("station2"));
        assertNotNull(result);
        assertEquals("station1", result.get(0).getDeparture().getStationName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testFindByDepartureAndDestination() throws DaoException {
        RoutSection routSection = new RoutSection(new Station("station1"), new Station("station2"),
                200, 100, LocalTime.of(10, 10), LocalTime.of(13, 50));
        List<RoutSection> routSectionList = new ArrayList<>();
        routSectionList.add(routSection);
        when(routSectionDao.findByDepartureAndDestination(new Station("station1"), new Station("station2"))).
                thenReturn(routSectionList);

        List<RoutSection> result = routSectionService.findByDepartureAndDestination(new Station("station1"), new Station("station2"));
        assertNotNull(result);
        assertEquals("station1", result.get(0).getDeparture().getStationName());
        assertEquals("station2", result.get(0).getDestination().getStationName());
        assertEquals(false, result.get(0).getDeleted());
    }
}
