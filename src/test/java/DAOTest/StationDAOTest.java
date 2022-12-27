package DAOTest;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.impl.StationDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;
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
public class StationDAOTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private StationDAOImpl stationDAO;
    private Station expected;
    private final int stationId = 1;
    private final String NAME = "Test";

    @BeforeEach
    public void setUp() {
        expected = new Station(stationId, NAME);
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
        expected.setId(stationId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.STATION_ID)).thenReturn(expected.getId());
        when(resultSet.getString(Fields.STATION_NAME)).thenReturn(expected.getName());
        final Station actual = stationDAO.findById(stationId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery(); }


    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> stationDAO.findById(stationId));
    }


    @Test
    void shouldFindByNameTest() throws SQLException {
        expected.setName(NAME);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.STATION_ID)).thenReturn(expected.getId());
        when(resultSet.getString(Fields.STATION_NAME)).thenReturn(expected.getName());
        final Station actual = stationDAO.findByName(NAME);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery(); }


    @Test
    void shouldNotFindByName() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> stationDAO.findByName(NAME));
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
        stationDAO.delete(stationId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException {
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        stationDAO.delete(stationId);
        verify(preparedStatement, times(1)).executeUpdate();
    }



    @Test
    void shouldNotDeleteTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () ->  stationDAO.delete(stationId));
    }

}
