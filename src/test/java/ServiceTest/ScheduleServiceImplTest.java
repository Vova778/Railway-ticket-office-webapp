package ServiceTest;

import com.railway.ticket.office.webapp.db.dao.impl.ScheduleDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.service.impl.RouteServiceImpl;
import com.railway.ticket.office.webapp.service.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceImplTest {

    @Mock
    private ScheduleDAOImpl scheduleDAO;
    @InjectMocks
    private ScheduleServiceImpl scheduleService;
    private Schedule expected;


    @BeforeEach
    public void setUp() {
        expected = new Schedule(1, new Date(System.currentTimeMillis()));
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
        final int scheduleId = 1;

        when(scheduleDAO.insert(expected)).thenReturn(scheduleId);
        scheduleService.insert(expected);
        verify(scheduleDAO, times(1)).insert(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> scheduleService.insert(null));
    }


    @Test
    void shouldNotSaveTest() throws DAOException {
        when(scheduleDAO.insert(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> scheduleService.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        expected.setId(1);
        when(scheduleDAO.findById(expected.getId()))
                .thenReturn(expected);
        final Schedule actual = scheduleService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        final int scheduleId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> scheduleService.findById(scheduleId));
    }


    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(scheduleDAO.findById(1))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> scheduleService.findById(1));
    }


    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Schedule> expected = Arrays.asList(
                new Schedule(1, new Date(System.currentTimeMillis())),
                new Schedule(2, new Date(System.currentTimeMillis())),
                new Schedule(3, new Date(System.currentTimeMillis())));

        when(scheduleDAO.findAll()).thenReturn(expected);
        List<Schedule> actual = scheduleService.findAll();
        assertEquals(expected, actual);
    }


    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        scheduleService.update(expected);
        verify(scheduleDAO, times(1))
                .update(expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> scheduleService.update(new Schedule()));
        assertThrows(IllegalArgumentException.class,
                () -> scheduleService.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> scheduleService.update(null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(scheduleDAO.update(expected))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> scheduleService.update(expected));
    }


    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final int scheduleId = 1;

        scheduleService.delete(scheduleId);
        scheduleService.delete(scheduleId);
        verify(scheduleDAO, times(2))
                .delete(scheduleId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() {
        final int scheduleId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> scheduleService.delete(scheduleId));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final int scheduleId = 1;
        doThrow(DAOException.class).when(scheduleDAO).delete(scheduleId);
        assertThrows(ServiceException.class,
                () -> scheduleService.delete(scheduleId));
    }
}
