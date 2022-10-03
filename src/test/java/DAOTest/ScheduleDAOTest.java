package DAOTest;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.db.dao.ScheduleDAO;
import com.railway.ticket.office.webapp.db.dao.factory.DAOFactory;
import com.railway.ticket.office.webapp.model.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScheduleDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(ScheduleDAOTest.class);

    private static ScheduleDAO scheduleDAO;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting ScheduleDAO tests");
        scheduleDAO = DAOFactory.getInstance().getScheduleDAO();
    }

    @Test
    void findScheduleByIdTest() throws DAOException {
        Schedule actual = scheduleDAO.findScheduleById(1);
        assertNotNull(actual);
        Schedule expected = new Schedule(1, Date.valueOf("2022-09-08"), 201);
        assertEquals(actual, expected);

    }
}
