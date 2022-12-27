package ModelTest;

import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Ticket;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {
    @Test
    void getAndSetTest() {
        Ticket ticket = new Ticket();
        assertEquals(0, ticket.getId());
        assertEquals(0, ticket.getUserId());
        assertEquals( 0, ticket.getFare());
        assertEquals( 0, ticket.getTrainNumber());
        assertNull(ticket.getArrivalTime());
        assertNull(ticket.getDepartureTime());
        assertNull(ticket.getStartingStation());
        assertNull(ticket.getFinalStation());

        ticket = new Ticket();
        ticket.setTrainNumber(101);
        ticket.setRoutes(Collections.singletonList(new Route()));
        ticket.setStartingStation("StartingStation");
        ticket.setTicketStatus(Ticket.TicketStatus.QUEUED);
        ticket.setDepartureTime(new Timestamp(1));
        ticket.setArrivalTime(new Timestamp(2));
        ticket.setUserId(1);
        ticket.setFare(100.5);
        ticket.setId(1);
        ticket.setFinalStation("FinalStation");

        assertEquals(1, ticket.getId());
        assertEquals(1, ticket.getUserId());
        assertEquals( 100.5, ticket.getFare());
        assertEquals( 101, ticket.getTrainNumber());
        assertNotNull(ticket.getArrivalTime());
        assertNotNull(ticket.getDepartureTime());
        assertNotNull(ticket.getStartingStation());
        assertNotNull(ticket.getFinalStation());
    }

    @Test
    void wrongInputTest() {
        Ticket ticket = new Ticket();
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setId(-1));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setFare(-1.5));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setRoutes(null));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setStartingStation(null));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setStartingStation(""));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setFinalStation(null));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setFinalStation(""));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setDepartureTime(null));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setArrivalTime(null));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setUserId(-1));
        assertThrows(IllegalArgumentException.class,
                () -> ticket.setTrainNumber(-1));


    }


}
