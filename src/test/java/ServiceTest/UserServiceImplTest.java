package ServiceTest;

import com.railway.ticket.office.webapp.db.dao.impl.UserDAOImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.impl.RouteServiceImpl;
import com.railway.ticket.office.webapp.service.impl.UserServiceImpl;
import com.railway.ticket.office.webapp.utils.security.PasswordEncryption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDAOImpl userDAO;
    @InjectMocks
    private UserServiceImpl userService;
    private User expected;
    private final String password = "xxxxxxxx";


    @BeforeEach
    public void setUp() {
        expected = User.newBuilder()
                .setId(1)
                .setLogin("Test")
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
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new RouteServiceImpl(null));
    }

    @Test
    void shouldSaveTest() throws SQLException, ServiceException {
        final int id = 1;

        when(userDAO.insert(expected)).thenReturn(id);
        userService.insert(expected);
        verify(userDAO, times(1)).insert(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.insert(null));
    }


    @Test
    void shouldNotSaveTest() throws DAOException {
        when(userDAO.insert(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> userService.insert(expected));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        expected.setId(1);
        when(userDAO.findById(expected.getId()))
                .thenReturn(expected);
        final User actual = userService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        final int id = 0;
        assertThrows(IllegalArgumentException.class,
                () -> userService.findById(id));
    }


    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(userDAO.findById(1))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> userService.findById(1));
    }


    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<User> expected = new ArrayList<>();

        expected.add(User.newBuilder()
                .setId(1)
                .setLogin("Test")
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setPassword(PasswordEncryption.getEncrypted(password))
                .setPhone("3802211111")
                .setRole(User.Role.USER)
                .build());
        expected.add(User.newBuilder()
                .setId(2)
                .setLogin("Test2")
                .setFirstName("FirstName2")
                .setLastName("LastName2")
                .setPassword(PasswordEncryption.getEncrypted(password))
                .setPhone("3802211112")
                .setRole(User.Role.USER)
                .build());
        expected.add(User.newBuilder()
                .setId(1)
                .setLogin("Test")
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setPassword(PasswordEncryption.getEncrypted(password))
                .setPhone("3802211111")
                .setRole(User.Role.USER)
                .build());
        when(userDAO.findAll(10)).thenReturn(expected);
        List<User> actual = userService.findAll(10);
        assertEquals(expected, actual);
    }


    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        userService.update(expected);
        verify(userDAO, times(1))
                .update(expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.update(new User()));
        assertThrows(IllegalArgumentException.class,
                () -> userService.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> userService.update(null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(userDAO.update(expected))
                .thenThrow(DAOException.class);
        assertThrows(ServiceException.class,
                () -> userService.update(expected));
    }


    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final int id = 1;

        userService.delete(id);
        userService.delete(id);
        verify(userDAO, times(2))
                .delete(id);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() {
        final int id = 0;
        assertThrows(IllegalArgumentException.class,
                () -> userService.delete(id));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final int id = 1;
        doThrow(DAOException.class).when(userDAO).delete(id);
        assertThrows(ServiceException.class,
                () -> userService.delete(id));
    }
}
