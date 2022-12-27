package ServiceTest;

import com.railway.ticket.office.webapp.db.dao.impl.TrainDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.impl.TrainServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainServiceImplTest {

    @Mock
    private TrainDAOImpl trainDAO;
    @InjectMocks
    private TrainServiceImpl trainService;
    private Train expected;


    @BeforeEach
    public void setUp() {
        expected = new Train(100, 10);
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new TrainServiceImpl(null));
    }

    @Test
    void shouldSaveTest() throws SQLException, ServiceException {
        final int id = 1;

        when(trainDAO.insert(expected)).thenReturn(id);
        trainService.insert(expected);
        verify(trainDAO, times(1)).insert(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> trainService.insert(null));
    }


    @Test
    void shouldNotSaveTest() throws DAOException {
        when(trainDAO.insert(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> trainService.insert(expected));
    }

    @Test
    void shouldFindByNumberTest() throws DAOException, ServiceException {
        expected.setNumber(1);
        when(trainDAO.findByNumber(expected.getNumber()))
                .thenReturn(expected);
        final Train actual = trainService.findById(expected.getNumber());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfNumberIncorrectTest() {
        final int id = 0;
        assertThrows(IllegalArgumentException.class,
                () -> trainService.findById(id));
    }


    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(trainDAO.findByNumber(1))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> trainService.findById(1));
    }


    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Train> expected = Arrays.asList(
                new Train(100, 10),
                new Train(101, 15),
                new Train(102,20));
        when(trainDAO.findAll(10)).thenReturn(expected);
        List<Train> actual = trainService.findAll(10);
        assertEquals(expected, actual);
    }


    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        trainService.update(expected);
        verify(trainDAO, times(1))
                .update(expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> trainService.update(new Train()));
        assertThrows(IllegalArgumentException.class,
                () -> trainService.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> trainService.update(null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(trainDAO.update(expected))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> trainService.update(expected));
    }


    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final int id = 1;

        trainService.delete(id);
        trainService.delete(id);
        verify(trainDAO, times(2))
                .delete(id);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() {
        final int id = 0;
        assertThrows(IllegalArgumentException.class,
                () -> trainService.delete(id));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final int id = 1;
        doThrow(DAOException.class).when(trainDAO).delete(id);
        assertThrows(ServiceException.class,
                () -> trainService.delete(id));
    }
}
