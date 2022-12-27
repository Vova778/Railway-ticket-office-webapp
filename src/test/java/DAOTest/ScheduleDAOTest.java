package DAOTest;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.impl.ScheduleDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleDAOTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private ScheduleDAOImpl scheduleDAO;
    private Schedule expected;
    private final int scheduleId = 1;


    @BeforeEach
    public void setUp() {
        expected = new Schedule(scheduleId,
                Date.valueOf(LocalDate.now()),
                new Train(100, 10));
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }



    @Test
    void getConnectionTest() {
        assertEquals(connection,
                scheduleDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException {

        when(connection.prepareStatement(anyString(),
                anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        scheduleDAO.insert(expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }


    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(),
                anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> scheduleDAO.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException {
        expected.setId(scheduleId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.SCHEDULE_ID)).thenReturn(expected.getId());
        when(resultSet.getDate(Fields.SCHEDULE_DATE)).thenReturn(expected.getDate());
        when(resultSet.getInt(Fields.SCHEDULE_TRAIN_NUMBER)).thenReturn(expected.getTrain().getNumber());
        when(resultSet.getInt(Fields.SCHEDULE_TRAIN_SEATS)).thenReturn(expected.getTrain().getSeats());
        final Schedule actual = scheduleDAO.findById(scheduleId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery(); }


    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> scheduleDAO.findById(scheduleId));
    }

    @Test
    void shouldUpdateTest() throws SQLException {
        final int rowsUpdatedTrue = 2;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        scheduleDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(scheduleDAO.update(expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException {
        final int rowsUpdatedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        scheduleDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(scheduleDAO.update(expected));
    }


    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> scheduleDAO.update(expected));
    }

    @Test
    void shouldDeleteByScheduleIdTest() throws SQLException {
        final int addressId = 1;
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        scheduleDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException {
        final int addressId = 1;
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        scheduleDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }



    @Test
    void shouldNotDeleteTest() throws SQLException {
        final int addressId = 1;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () ->  scheduleDAO.delete(addressId));
    }

}
