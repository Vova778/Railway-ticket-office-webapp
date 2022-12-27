package DAOTest;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.impl.RouteDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Train;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteDAOTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private RouteDAOImpl routeDAO;
    private Route expected;
    private final int routeId = 1;


    @BeforeEach
    public void setUp() {
        expected = Route.newBuilder()
                .setId(routeId)
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
    void getConnectionTest() {
        assertEquals(connection,
                routeDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException {

        when(connection.prepareStatement(anyString(),
                anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        routeDAO.insert(expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }


    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(),
                anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> routeDAO.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.ROUTE_ID)).thenReturn(expected.getId());
        when(resultSet.getInt(Fields.ROUTE_STOPPAGE_NUMBER)).thenReturn(expected.getStoppageNumber());
        when(resultSet.getInt(Fields.ROUTE_TRAIN_ID)).thenReturn(expected.getTrain().getNumber());
        when(resultSet.getInt(Fields.ROUTE_TOTAL_SEATS)).thenReturn(expected.getTrain().getSeats());
        when(resultSet.getInt(Fields.ROUTE_STARTING_STATION_ID)).thenReturn(expected.getStartingStation().getId());
        when(resultSet.getString(Fields.ROUTE_STARTING_STATION_NAME)).thenReturn(expected.getStartingStation().getName());
        when(resultSet.getInt(Fields.ROUTE_FINAL_STATION_ID)).thenReturn(expected.getFinalStation().getId());
        when(resultSet.getString(Fields.ROUTE_FINAL_STATION_NAME)).thenReturn(expected.getFinalStation().getName());
        when(resultSet.getInt(Fields.ROUTE_SCHEDULE_ID)).thenReturn(expected.getSchedule().getId());
        when(resultSet.getDate(Fields.ROUTE_SCHEDULE_DATE)).thenReturn(expected.getSchedule().getDate());
        when(resultSet.getInt(Fields.ROUTE_AVAILABLE_SEATS)).thenReturn(expected.getAvailableSeats());
        when(resultSet.getTime(Fields.ROUTE_DEPARTURE_TIME)).thenReturn(expected.getDepartureTime());
        when(resultSet.getTime(Fields.ROUTE_ARRIVAL_TIME)).thenReturn(expected.getArrivalTime());
        when(resultSet.getInt(Fields.ROUTE_DAY)).thenReturn(expected.getDay());
        when(resultSet.getDouble(Fields.ROUTE_PRICE)).thenReturn(expected.getPrice());
        final Route actual = routeDAO.findById(routeId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }


    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> routeDAO.findById(routeId));
    }


    @Test
    void shouldUpdateTest() throws SQLException {
        final int rowsUpdatedTrue = 4;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        routeDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(routeDAO.update(expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException, DAOException {
        final int rowsUpdatedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        routeDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(routeDAO.update(expected));
    }


    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> routeDAO.update(expected));
    }

    @Test
    void shouldDeleteByRouteIdTest() throws SQLException {
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        routeDAO.delete(routeId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException {
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        routeDAO.delete(routeId);
        verify(preparedStatement, times(1)).executeUpdate();
    }



    @Test
    void shouldNotDeleteTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () ->  routeDAO.delete(routeId));
    }


}
