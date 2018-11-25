package system.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.DaoException;
import system.dao.api.StationDao;
import system.entity.Station;
import system.service.impl.StationServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class StationServiceTest {

    @Mock
    private StationDao stationDao;

    @InjectMocks
    private StationServiceImpl stationService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() throws DaoException {
        Station station = new Station("testStation");
        stationService.save(station);

        verify(stationDao, times(1)).create(station);
        verify(stationDao, never()).update(station);
        verify(stationDao, never()).remove(station);
    }

    @Test
    public void testDelete() throws DaoException {
        when(stationDao.findByName("testStation")).thenReturn(new Station("testStation"));

        Station station = stationService.findByName("testStation");
        stationService.delete(station);

        verify(stationDao, times(1)).remove(station);
    }

    @Test
    public void testFindById() throws DaoException {
        Station station = new Station("station 1");
        when(stationDao.findById(1L)).thenReturn(station);

        Station result = stationService.findById(1L);
        assertEquals("station 1", result.getStationName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByName() throws DaoException {
        Station station = new Station("station 2");
        when(stationDao.findByName("station 2")).thenReturn(station);

        Station result = stationService.findByName("station 2");
        assertNotNull(result);
        assertEquals("station 2", result.getStationName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Station> stationList = new ArrayList<>();
        stationList.add(new Station("station 1"));
        stationList.add(new Station("station 2"));
        stationList.add(new Station("station 3"));

        when(stationDao.findAll()).thenReturn(stationList);

        List<Station> result = stationService.findAll();
        assertEquals(3, result.size());
    }
}
