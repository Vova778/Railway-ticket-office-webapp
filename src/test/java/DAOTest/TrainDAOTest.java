package DAOTest;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.impl.TrainDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Train;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainDAOTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private TrainDAOImpl stationDAO;
    private Train expected;
    private final int trainNumber = 1;


    @BeforeEach
    public void setUp() {
        expected = new Train(100, 10);
    }
    @AfterEach
    public void tearDown() {
        expected = null;
    }


    @Test
    void getConnectionTest() {
        assertEquals(connection,
                stationDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException {

        when(connection.prepareStatement(anyString(),
                anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        stationDAO.insert(expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }


    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(),
                anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> stationDAO.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException {
        expected.setNumber(trainNumber);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.TRAIN_NUMBER)).thenReturn(expected.getNumber());
        when(resultSet.getInt(Fields.TRAIN_SEATS)).thenReturn(expected.getSeats());
        final Train actual = stationDAO.findByNumber(trainNumber);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery(); }


    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> stationDAO.findByNumber(trainNumber));
    }

    @Test
    void shouldNotFindAll() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> stationDAO.findAll(10));
    }


    @Test
    void shouldUpdateTest() throws SQLException {
        final int rowsUpdatedTrue = 4;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        stationDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(stationDAO.update(expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException {
        final int rowsUpdatedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        stationDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(stationDAO.update(expected));
    }


    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> stationDAO.update(expected));
    }

    @Test
    void shouldDeleteByStationIdTest() throws SQLException {
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        stationDAO.delete(trainNumber);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException {
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        stationDAO.delete(trainNumber);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () ->  stationDAO.delete(trainNumber));
    }
}
