package DAOTest;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.db.dao.StationDAO;
import com.railway.ticket.officewebapp.db.dao.factory.DAOFactory;
import com.railway.ticket.officewebapp.model.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class StationDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(StationDAOTest.class);

    private static StationDAO stationDAO;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting StationDAO tests");
        stationDAO = DAOFactory.getInstance().getStationDAO();
    }

    @Test
    void findStationByIdTest() throws DBException {
        Station actual = stationDAO.getStationById(1);
        assertNotNull(actual);
        Station expected = new Station(1, "Vesele");
        assertEquals(actual, expected);

    }


}
