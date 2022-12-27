package ServiceTest;

import com.railway.ticket.office.webapp.db.dao.impl.RouteDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.impl.RouteServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteServiceImplTest {

    @Mock
    private RouteDAOImpl routeDAO;
    @InjectMocks
    private RouteServiceImpl routeService;
    private Route expected;


    @BeforeEach
    public void setUp() {
        expected = Route.newBuilder()
                .setId(1)
                .setAvailableSeats(10)
                .setPrice(12.5)
                .setDay(1)
                .setStoppageNumber(1)
                .setTrain(new Train(101, 10))
                .setDepartureTime(new Time(1))
                .setArrivalTime(new Time(2))
                .setSchedule(new Schedule(101, new Date(System.currentTimeMillis())))
                .setStartingStation(new Station(1, "StartingStation"))
                .setFinalStation(new Station(2, "FinalStation"))
                .build();
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
        final int routeId = 1;

        when(routeDAO.insert(expected)).thenReturn(routeId);
        routeService.insert(expected);
        verify(routeDAO, times(1)).insert(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> routeService.insert(null));
    }


    @Test
    void shouldNotSaveTest() throws DAOException {
        when(routeDAO.insert(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> routeService.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        expected.setId(1);
        when(routeDAO.findById(expected.getId()))
                .thenReturn(expected);
        final Route actual = routeService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        final int routeId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> routeService.findById(routeId));
    }
    @Test
    void shouldFindByTicketIdTest() throws DAOException, ServiceException {
        List<Route> expected = getRouteList();

        when(routeDAO.findRoutesByTicketId(1))
                .thenReturn(expected);
        final List<Route> actual = routeService.findRoutesByTicketId(1);
        assertEquals(expected, actual);
    }


    @Test
    void shouldNotFindByTicketIdIfIncorrectTest() {
        final int ticketId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> routeService.findRoutesByTicketId(ticketId));
    }

    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(routeDAO.findById(1))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> routeService.findById(1));
    }


    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Route> expected = getRouteList();
        when(routeDAO.findAll()).thenReturn(expected);
        List<Route> actual = routeService.findAll();
        assertEquals(expected, actual);
    }


    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        routeService.update(expected);
        verify(routeDAO, times(1))
                .update(expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> routeService.update(new Route()));
        assertThrows(IllegalArgumentException.class,
                () -> routeService.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> routeService.update(null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(routeDAO.update(expected))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> routeService.update(expected));
    }


    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final int routeId = 1;

        routeService.delete(routeId);
        routeService.delete(routeId);
        verify(routeDAO, times(2))
                .delete(routeId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() {
        final int routeId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> routeService.delete(routeId));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final int routeId = 1;
        doThrow(DAOException.class).when(routeDAO).delete(routeId);
        assertThrows(ServiceException.class,
                () -> routeService.delete(routeId));
    }

    private List<Route> getRouteList() {
        List<Route> expected = new ArrayList<>();
        expected.add(Route.newBuilder()
                .setId(1)
                .setAvailableSeats(10)
                .setPrice(12.5)
                .setDay(1)
                .setStoppageNumber(1)
                .setTrain(new Train(101, 10))
                .setDepartureTime(new Time(1))
                .setArrivalTime(new Time(2))
                .setSchedule(new Schedule(101, new Date(System.currentTimeMillis())))
                .setStartingStation(new Station(1, "Station1"))
                .setFinalStation(new Station(2, "Station2"))
                .build());
        expected.add(Route.newBuilder()
                .setId(2)
                .setAvailableSeats(10)
                .setPrice(12.5)
                .setDay(1)
                .setStoppageNumber(2)
                .setTrain(new Train(101, 10))
                .setDepartureTime(new Time(2))
                .setArrivalTime(new Time(3))
                .setSchedule(new Schedule(101, new Date(System.currentTimeMillis())))
                .setStartingStation(new Station(2, "Station2"))
                .setFinalStation(new Station(3, "Station3"))
                .build());
        expected.add(Route.newBuilder()
                .setId(3)
                .setAvailableSeats(10)
                .setPrice(12.5)
                .setDay(1)
                .setStoppageNumber(3)
                .setTrain(new Train(101, 10))
                .setDepartureTime(new Time(3))
                .setArrivalTime(new Time(4))
                .setSchedule(new Schedule(101, new Date(System.currentTimeMillis())))
                .setStartingStation(new Station(3, "Station3"))
                .setFinalStation(new Station(4, "Station4"))
                .build());
        return expected;
    }
}
