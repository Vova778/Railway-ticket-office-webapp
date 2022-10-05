package DAOTest;

import com.railway.ticket.office.webapp.db.dao.StationDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StationDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(StationDAOTest.class);

    private static StationDAO stationDAO;

    /*@BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting StationDAO tests");
        stationDAO = DAOFactory.getInstance().getStationDAO();
    }

    @Test
    void findStationByIdTest() throws DAOException {
        Station actual = stationDAO.findStationById(1);
        assertNotNull(actual);
        Station expected = new Station(1, "Vesele");
        assertEquals(actual, expected);

    }*/


}
