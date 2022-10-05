package DAOTest;

import com.railway.ticket.office.webapp.db.dao.TicketDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TicketDAOTest {
    private static final Logger LOGGER = LogManager.getLogger(TicketDAOTest.class);

    private static TicketDAO ticketDAO;

   /* @BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting TicketDAO tests");
        ticketDAO = DAOFactory.getInstance().getTicketDAO();
    }

    @Test
    void findTicketByIdTest() throws DAOException {
        Ticket actual = ticketDAO.findTicketById(1);
        assertNotNull(actual);
        Ticket expected = Ticket.newBuilder()
                .setId(1)
                .setFare(100)
                .setStartingStationId(1)
                .setFinalStationId(7)
                .setDepartureTime(Timestamp.valueOf("2022-09-08 22:21:08"))
                .setArrivalTime(Timestamp.valueOf("2022-09-08 22:21:09"))
                .setTrainNumber(201)
                .setUserId(1)
                .setTicketStatus(Ticket.TicketStatus.QUEUED)
                .build();
        assertEquals(actual, expected);

    }*/
}
