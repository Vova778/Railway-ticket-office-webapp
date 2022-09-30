package DAOTest;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.db.dao.RouteDAO;
import com.railway.ticket.officewebapp.db.dao.factory.DAOFactory;
import com.railway.ticket.officewebapp.model.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RouteDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(RouteDAOTest.class);

    private static RouteDAO routeDAO;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        LOGGER.info("Starting RouteDAO tests");
        routeDAO = DAOFactory.getInstance().getRouteDAO();
    }

    @Test
    void findRouteByIdTest() throws DBException {
        Route actual = routeDAO.getRouteById(1);
        assertNotNull(actual);
        Route expected = Route.newBuilder()
                .setId(1)
                .setStoppageNumber(1)
                .setStartingStationId(1)
                .setFinalStationId(2)
                .setDepartureTime( Time.valueOf("10:00:00"))
                .setArrivalTime(Time.valueOf("11:30:00"))
                .setAvailableSeats(10)
                .setDay(1)
                .setScheduleId(1)
                .setTrainId(201)
                .setPrice(21.00)
                .build();
        assertEquals(actual, expected);
    }




}
