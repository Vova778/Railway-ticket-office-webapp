package DAOTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScheduleDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(ScheduleDAOTest.class);
/*
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
    }*/
}
