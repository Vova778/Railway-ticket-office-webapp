package DAOTest;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.db.dao.UserDAO;
import com.railway.ticket.office.webapp.db.dao.factory.DAOFactory;
import com.railway.ticket.office.webapp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOTest.class);

    private static UserDAO userDAO;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting UserDAO tests");
        userDAO = DAOFactory.getInstance().getUserDAO();
    }

    @Test
    void findUserByIdTest() throws DAOException {
        User actual = userDAO.findUserById(1);
        assertNotNull(actual);
        User expected = User.newBuilder()
                .setId(1)
                .setLogin("Vova778")
                .setPassword("1111")
                .setFirstName("Vova1")
                .setLastName("Muradin1")
                .setPhone("1111111111")
                .setRole(User.Role.MANAGER)
                .build();
        assertEquals(actual, expected);

    }
}
