package ServiceTest;

import com.railway.ticket.office.webapp.db.dao.impl.StationDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.impl.RouteServiceImpl;
import com.railway.ticket.office.webapp.service.impl.StationServiceImpl;
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
public class StationServiceImplTest {

    @Mock
    private StationDAOImpl stationDAO;
    @InjectMocks
    private StationServiceImpl stationService;
    private Station expected;
    private final String NAME = "TestStation";


    @BeforeEach
    public void setUp() {
        expected = new Station(1, NAME);
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new RouteServiceImpl(null));
    }

    @Test
    void shouldSaveTest() throws SQLException, ServiceException {
        final int stationId = 1;

        when(stationDAO.insert(expected)).thenReturn(stationId);
        stationService.insert(expected);
        verify(stationDAO, times(1)).insert(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> stationService.insert(null));
    }


    @Test
    void shouldNotSaveTest() throws DAOException {
        when(stationDAO.insert(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> stationService.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        expected.setId(1);
        when(stationDAO.findById(expected.getId()))
                .thenReturn(expected);
        final Station actual = stationService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        final int stationId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> stationService.findById(stationId));
    }


    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(stationDAO.findById(1))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> stationService.findById(1));
    }
    @Test
    void shouldFindByNameTest() throws DAOException, ServiceException {
        when(stationDAO.findByName(NAME))
                .thenReturn(expected);
        final Station actual = stationService.findByName(NAME);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByNameIfIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> stationService.findByName(null));
        assertThrows(IllegalArgumentException.class,
                () -> stationService.findByName(""));
    }


    @Test
    void shouldNotFindByNameTest() throws DAOException {
        when(stationDAO.findByName(NAME))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> stationService.findByName(NAME));
    }



    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Station> expected = Arrays.asList(
                new Station(1, "Station1"),
                new Station(2, "Station2"),
                new Station(3, "Station3"));

        when(stationDAO.findAll()).thenReturn(expected);
        List<Station> actual = stationService.findAll();
        assertEquals(expected, actual);
    }


    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        final int stationId = 1;
        stationService.update(expected);
        verify(stationDAO, times(1))
                .update(expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> stationService.update(new Station()));
        assertThrows(IllegalArgumentException.class,
                () -> stationService.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> stationService.update(null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        final int stationId = 1;
        when(stationDAO.update(expected))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> stationService.update(expected));
    }


    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final int stationId = 1;

        stationService.delete(stationId);
        stationService.delete(stationId);
        verify(stationDAO, times(2))
                .delete(stationId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() throws ServiceException {
        final int stationId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> stationService.delete(stationId));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final int stationId = 1;
        doThrow(DAOException.class).when(stationDAO).delete(stationId);
        assertThrows(ServiceException.class,
                () -> stationService.delete(stationId));
    }

}
