package ServiceTest;

import com.railway.ticket.office.webapp.db.dao.impl.TicketDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {


    @Mock
    private TicketDAOImpl ticketDAO;
    @InjectMocks
    private TicketServiceImpl ticketService;
    private Ticket expected;

    @BeforeEach
    public void setUp() {
        expected = new Ticket();
        expected.setTrainNumber(101);
        expected.setRoutes(Collections.singletonList(new Route()));
        expected.setStartingStation("StartingStation");
        expected.setTicketStatus(Ticket.TicketStatus.QUEUED);
        expected.setDepartureTime(new Timestamp(1));
        expected.setArrivalTime(new Timestamp(2));
        expected.setUserId(1);
        expected.setFare(100.5);
        expected.setId(1);
        expected.setFinalStation("FinalStation");
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new TicketServiceImpl(null));
    }

    @Test
    void shouldSaveTest() throws SQLException, ServiceException {
        final int id = 1;

        when(ticketDAO.insert(expected)).thenReturn(id);
        ticketService.insert(expected);
        verify(ticketDAO, times(1)).insert(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> ticketService.insert(null));
    }


    @Test
    void shouldNotSaveTest() throws DAOException {
        when(ticketDAO.insert(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> ticketService.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        expected.setId(1);
        when(ticketDAO.findById(expected.getId()))
                .thenReturn(expected);
        final Ticket actual = ticketService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        final int id = 0;
        assertThrows(IllegalArgumentException.class,
                () -> ticketService.findById(id));
    }


    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(ticketDAO.findById(1))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> ticketService.findById(1));
    }


    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Ticket> expected = Arrays.asList(
                new Ticket(),
                new Ticket(),
                new Ticket());
        when(ticketDAO.findAll()).thenReturn(expected);
        List<Ticket> actual = ticketService.findAll();
        assertEquals(expected, actual);
    }


    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        ticketService.update(expected);
        verify(ticketDAO, times(1))
                .update(expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> ticketService.update(new Ticket()));
        assertThrows(IllegalArgumentException.class,
                () -> ticketService.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> ticketService.update(null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(ticketDAO.update(expected))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> ticketService.update(expected));
    }


    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final int id = 1;

        ticketService.delete(id);
        ticketService.delete(id);
        verify(ticketDAO, times(2))
                .delete(id);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() {
        final int id = 0;
        assertThrows(IllegalArgumentException.class,
                () -> ticketService.delete(id));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final int id = 1;
        doThrow(DAOException.class).when(ticketDAO).delete(id);
        assertThrows(ServiceException.class,
                () -> ticketService.delete(id));
    }
}
