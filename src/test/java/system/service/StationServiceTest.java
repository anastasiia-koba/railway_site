package system.service;

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

    private Station station;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        station = new Station("testStation");
    }

    @Test
    public void testSave() throws DaoException {
        stationService.save(station);

        verify(stationDao, times(1)).create(station);
        verify(stationDao, never()).update(station);
        verify(stationDao, never()).remove(station);
    }

    @Test
    public void testDelete() throws DaoException {
        stationService.delete(station.getId());

        verify(stationDao, times(1)).remove(station);
    }

    @Test
    public void testFindById() throws DaoException {
        when(stationDao.findById(1L)).thenReturn(station);

        Station result = stationService.findById(1L);
        assertNotNull(result);
        assertEquals("testStation", result.getStationName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByName() throws DaoException {
        when(stationDao.findByName("testStation")).thenReturn(station);

        Station result = stationService.findByName("testStation");
        assertNotNull(result);
        assertEquals("testStation", result.getStationName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Station> stationList = new ArrayList<>();
        stationList.add(station);
        stationList.add(new Station("station 2"));
        stationList.add(new Station("station 3"));

        when(stationDao.findAll()).thenReturn(stationList);

        List<Station> result = stationService.findAll();
        assertEquals(3, result.size());
    }
}
