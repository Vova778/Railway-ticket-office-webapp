package DAOTest;

import com.railway.ticket.office.webapp.db.dao.TrainDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(TrainDAOTest.class);

    private static TrainDAO trainDAO;

   /* @BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting TrainDAO tests");
        trainDAO = DAOFactory.getInstance().getTrainDAO();
    }

    @Test
    void findTrainByIdTest() throws DAOException {
        Train actual = trainDAO.findTrainByNumber(201);
        assertNotNull(actual);
        Train expected = new Train(201,10);
        assertEquals(actual, expected);

    }*/
}
