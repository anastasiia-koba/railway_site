package system.service.impl;

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

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() throws DaoException {
        Train train = new Train("testTrain", 50);
        trainService.save(train);

        verify(trainDao, times(1)).create(train);
        verify(trainDao, never()).update(train);
        verify(trainDao, never()).remove(train);
    }

    @Test
    public void testDelete() throws DaoException {
        when(trainDao.findByName("testTrain")).thenReturn(new Train("testTrain", 50));

        Train train = trainService.findByName("testTrain");
        trainService.delete(train);

        verify(trainDao, times(1)).remove(train);
    }

    @Test
    public void testFindById() throws DaoException {
        Train train = new Train("train 1", 30);
        when(trainDao.findById(1L)).thenReturn(train);

        Train result = trainService.findById(1L);
        assertEquals("train 1", result.getTrainName());
        assertEquals(Long.valueOf(30), Long.valueOf(result.getPlacesNumber()));
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindByName() throws DaoException {
        Train train = new Train("train 2", 50);
        when(trainDao.findByName("train 2")).thenReturn(train);

        Train result = trainService.findByName("train 2");
        assertNotNull(result);
        assertEquals("train 2", result.getTrainName());
        assertEquals(Long.valueOf(50), Long.valueOf(result.getPlacesNumber()));
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Train> trainList = new ArrayList<>();
        trainList.add(new Train("train 1"));
        trainList.add(new Train("train 2"));
        trainList.add(new Train("train 3"));

        when(trainDao.findAll()).thenReturn(trainList);

        List<Train> result = trainService.findAll();
        assertEquals(3, result.size());
    }
}

