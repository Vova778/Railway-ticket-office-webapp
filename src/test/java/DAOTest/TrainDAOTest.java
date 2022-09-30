package DAOTest;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.db.dao.TrainDAO;
import com.railway.ticket.officewebapp.db.dao.factory.DAOFactory;
import com.railway.ticket.officewebapp.model.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrainDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(TrainDAOTest.class);

    private static TrainDAO trainDAO;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting TrainDAO tests");
        trainDAO = DAOFactory.getInstance().getTrainDAO();
    }

    @Test
    void findTrainByIdTest() throws DBException {
        Train actual = trainDAO.getTrainById(201);
        assertNotNull(actual);
        Train expected = new Train(201,10);
        assertEquals(actual, expected);

    }
}
