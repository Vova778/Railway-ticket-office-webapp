package DAOTest;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.impl.UserDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.utils.security.PasswordEncryption;
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
public class UserDAOTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private UserDAOImpl userDAO;
    private User expected;
    private final int userId = 1;
    private final String LOGIN = "Test";



    @BeforeEach
    public void setUp() {
        String password = "xxxxxxxx";
        expected = User.newBuilder()
                .setId(userId)
                .setLogin(LOGIN)
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setPassword(PasswordEncryption.getEncrypted(password))
                .setPhone("3802211111")
                .setRole(User.Role.USER)
                .build();
    }
    @AfterEach
    public void tearDown() {
        expected = null;
    }


    @Test
    void getConnectionTest() {
        assertEquals(connection,
                userDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(),
                anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        userDAO.insert(expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }


    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(),
                anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> userDAO.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.USER_ID)).thenReturn(expected.getId());
        when(resultSet.getString(Fields.USER_LOGIN)).thenReturn(expected.getLogin());
        when(resultSet.getString(Fields.USER_FIRST_NAME)).thenReturn(expected.getFirstName());
        when(resultSet.getString(Fields.USER_LAST_NAME)).thenReturn(expected.getLastName());
        when(resultSet.getString(Fields.USER_PASSWORD)).thenReturn(expected.getPassword());
        when(resultSet.getString(Fields.USER_PHONE)).thenReturn(expected.getPhone());
        when(resultSet.getInt(Fields.USER_ROLE_ID)).thenReturn(expected.getRole().getId());
        final User actual = userDAO.findById(userId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }


    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> userDAO.findById(userId));
    }

    @Test
    void shouldFindByLoginTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(Fields.USER_ID)).thenReturn(expected.getId());
        when(resultSet.getString(Fields.USER_LOGIN)).thenReturn(expected.getLogin());
        when(resultSet.getString(Fields.USER_FIRST_NAME)).thenReturn(expected.getFirstName());
        when(resultSet.getString(Fields.USER_LAST_NAME)).thenReturn(expected.getLastName());
        when(resultSet.getString(Fields.USER_PASSWORD)).thenReturn(expected.getPassword());
        when(resultSet.getString(Fields.USER_PHONE)).thenReturn(expected.getPhone());
        when(resultSet.getInt(Fields.USER_ROLE_ID)).thenReturn(expected.getRole().getId());
        final User actual = userDAO.findByLogin(LOGIN);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }


    @Test
    void shouldNotFindByLogin() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class,
                () -> userDAO.findByLogin(LOGIN));
    }


    @Test
    void shouldNotFindAll() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.findAll(10));
    }


    @Test
    void shouldUpdateTest() throws SQLException {
        final int rowsUpdatedTrue = 4;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        userDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(userDAO.update(expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException {
        final int rowsUpdatedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        userDAO.update(expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(userDAO.update(expected));
    }


    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.update(expected));
    }

    @Test
    void shouldDeleteByUserIdTest() throws SQLException {
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate())
                .thenReturn(rowsDeletedTrue);
        userDAO.delete(userId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException {
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        userDAO.delete(userId);
        verify(preparedStatement, times(1)).executeUpdate();
    }



    @Test
    void shouldNotDeleteTest() throws SQLException {
        final int addressId = 1;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () ->  userDAO.delete(addressId));
    }

}
