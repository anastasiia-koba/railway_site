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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private RoutSection routSection;
    private List<RoutSection> routSectionList;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        routSection = new RoutSection();
        routSection.setDeparture(new Station("station1"));
        routSection.setDestination(new Station("station2"));
        routSection.setPrice(150);
        routSection.setDistance(100);
        routSection.setDepartureTime(LocalTime.of(14, 10));
        routSection.setArrivalTime(LocalTime.of(17, 30));

        routSectionList = Stream.of(routSection).collect(Collectors.toList());
    }

    @Test
    public void testSave() throws DaoException {
        routSectionService.save(routSection);

        verify(routSectionDao, times(1)).create(routSection);
        verify(routSectionDao, never()).update(routSection);
        verify(routSectionDao, never()).remove(routSection);
    }

    @Test
    public void testDelete() throws DaoException {
        routSectionService.delete(routSection.getId());

        verify(routSectionDao, times(1)).remove(routSection);
    }

    @Test
    public void testFindById() throws DaoException {
        when(routSectionDao.findById(1L)).thenReturn(routSection);

        RoutSection result = routSectionService.findById(1L);
        assertEquals("station1", result.getDeparture().getStationName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByDeparture() throws DaoException {
        when(routSectionDao.findByDeparture(new Station("station1"))).thenReturn(routSectionList);

        List<RoutSection> result = routSectionService.findByDeparture(new Station("station1"));
        assertNotNull(result);
        assertEquals("station2", result.get(0).getDestination().getStationName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testFindByDestination() throws DaoException {
        when(routSectionDao.findByDestination(new Station("station2"))).thenReturn(routSectionList);

        List<RoutSection> result = routSectionService.findByDestination(new Station("station2"));
        assertNotNull(result);
        assertEquals("station1", result.get(0).getDeparture().getStationName());
        assertEquals(false, result.get(0).getDeleted());
    }

    @Test
    public void testFindByDepartureAndDestination() throws DaoException {
        when(routSectionDao.findByDepartureAndDestination(new Station("station1"), new Station("station2"))).
                thenReturn(routSectionList);

        List<RoutSection> result = routSectionService.findByDepartureAndDestination(new Station("station1"), new Station("station2"));
        assertNotNull(result);
        assertEquals("station1", result.get(0).getDeparture().getStationName());
        assertEquals("station2", result.get(0).getDestination().getStationName());
        assertEquals(false, result.get(0).getDeleted());
    }
}
