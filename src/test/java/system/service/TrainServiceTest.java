package system.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.DaoException;
import system.dao.api.TrainDao;
import system.entity.Train;
import system.service.impl.TrainServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class TrainServiceTest {

    @Mock
    private TrainDao trainDao;

    @InjectMocks
    private TrainServiceImpl trainService;

    private Train train;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        train = new Train("testTrain", 50);
    }

    @Test
    public void testSaveNew() throws DaoException {
        trainService.save(train);

        verify(trainDao, times(1)).create(train);
        verify(trainDao, never()).update(train);
        verify(trainDao, never()).remove(train);
    }

    @Test
    public void testDelete() throws DaoException {
        trainService.delete(train);

        verify(trainDao, times(1)).remove(train);
    }

    @Test
    public void testFindById() throws DaoException {
        train.setId(1L);
        when(trainDao.findById(1L)).thenReturn(train);

        Train result = trainService.findById(1L);
        assertEquals("testTrain", result.getTrainName());
        assertEquals(Long.valueOf(50), Long.valueOf(result.getPlacesNumber()));
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByName() throws DaoException {
        when(trainDao.findByName("testTrain")).thenReturn(train);

        Train result = trainService.findByName("testTrain");
        assertNotNull(result);
        assertEquals("testTrain", result.getTrainName());
        assertEquals(Long.valueOf(50), Long.valueOf(result.getPlacesNumber()));
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Train> trainList = new ArrayList<>();
        trainList.add(train);
        trainList.add(new Train("train 2"));
        trainList.add(new Train("train 3"));

        when(trainDao.findAll()).thenReturn(trainList);

        List<Train> result = trainService.findAll();
        assertEquals(3, result.size());
    }
}

