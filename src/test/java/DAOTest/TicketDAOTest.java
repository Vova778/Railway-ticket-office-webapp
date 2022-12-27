package DAOTest;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.impl.TicketDAOImpl;
import com.railway.ticket.office.webapp.db.dao.mapper.impl.TicketMapper;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private TicketMapper ticketMapper;
    @InjectMocks
    private TicketDAOImpl ticketDAO;
    private Ticket expected;
    private final int ticketId = 1;


    @BeforeEach
    public void setUp() {
        Route route = Route.newBuilder().setId(1)
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

        expected = new Ticket();
        expected.setTrainNumber(101);
        expected.setRoutes(Collections.singletonList(route));
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
    void getConnectionTest() {
        assertEquals(connection,
                ticketDAO.getConnection());
    }




    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(),
                anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> ticketDAO.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException {
        expected.setId(ticketId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.TICKET_ID)).thenReturn(expected.getId());
        when(resultSet.getDouble(Fields.TICKET_FARE)).thenReturn(expected.getFare());
        when(resultSet.getInt(Fields.TICKET_TRAIN_NUMBER)).thenReturn(expected.getTrainNumber());
        when(resultSet.getString(Fields.TICKET_STARTING_STATION)).thenReturn(expected.getStartingStation());
        when(resultSet.getString(Fields.TICKET_FINAL_STATION)).thenReturn(expected.getFinalStation());
        when(resultSet.getTimestamp(Fields.TICKET_DEPARTURE_TIME)).thenReturn(expected.getDepartureTime());
        when(resultSet.getTimestamp(Fields.TICKET_ARRIVAL_TIME)).thenReturn(expected.getArrivalTime());
        when(resultSet.getInt(Fields.TICKET_USER_ID)).thenReturn(expected.getUserId());
        when(resultSet.getInt(Fields.TICKET_STATUS_ID)).thenReturn(expected.getTicketStatus().getId());

        final Ticket actual = ticketDAO.findById(ticketId);
        actual.setRoutes(expected.getRoutes());
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }



    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> ticketDAO.findById(ticketId));
    }


    @Test
    void shouldUpdateTest() throws SQLException {
        final int rowsUpdatedTrue = 4;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        ticketDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(ticketDAO.update(expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException {
        final int rowsUpdatedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        ticketDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(ticketDAO.update(expected));
    }


    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> ticketDAO.update(expected));
    }

    @Test
    void shouldDeleteByStationIdTest() throws SQLException {
        final int addressId = 1;
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        ticketDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException {
        final int addressId = 1;
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        ticketDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }



    @Test
    void shouldNotDeleteTest() throws SQLException {
        final int addressId = 1;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () ->  ticketDAO.delete(addressId));
    }
}
