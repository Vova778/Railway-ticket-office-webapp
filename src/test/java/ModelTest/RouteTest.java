package ModelTest;


import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Train;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {


    @Test
    void getAndSetTest() {
        Route route = new Route();
        assertEquals(0, route.getId());
        assertEquals(0, route.getAvailableSeats());
        assertEquals(0, route.getPrice());
        assertEquals(0, route.getDay());
        assertEquals(0, route.getStoppageNumber());
        assertNull(route.getTrain());
        assertNull(route.getArrivalTime());
        assertNull(route.getDepartureTime());
        assertNull(route.getTravelTime());
        assertNull(route.getSchedule());
        assertNull(route.getStartingStation());
        assertNull(route.getFinalStation());
        assertNull(route.getTravelTime());

        route = new Route();
        route.setId(1);
        route.setAvailableSeats(10);
        route.setPrice(12.5);
        route.setDay(1);
        route.setStoppageNumber(1);
        route.setTrain(new Train());
        route.setDepartureTime(new Time(1));
        route.setArrivalTime(new Time(2));
        route.setTravelTime(new Time(1));
        route.setSchedule(new Schedule());
        route.setStartingStation(new Station(1, "StartingStation"));
        route.setFinalStation(new Station(2, "FinalStation"));

        assertEquals(1, route.getId());
        assertEquals(10, route.getAvailableSeats());
        assertEquals(12.5, route.getPrice());
        assertEquals(1, route.getDay());
        assertEquals(1, route.getStoppageNumber());
        assertNotNull(route.getTravelTime());
        assertNotNull(route.getTrain());
        assertNotNull(route.getStartingStation());
        assertNotNull(route.getFinalStation());
    }


    @Test
    void wrongInputTest() {
        Route route = new Route();

        assertThrows(IllegalArgumentException.class, () -> route.setId(-1));
        assertThrows(IllegalArgumentException.class, () -> route.setDay(-1));
        assertThrows(IllegalArgumentException.class, () -> route.setStoppageNumber(-1));
        assertThrows(IllegalArgumentException.class, () -> route.setAvailableSeats(-1));
        assertThrows(IllegalArgumentException.class, () -> route.setPrice(-1.5));
        assertThrows(IllegalArgumentException.class, () -> route.setTrain(null));
        assertThrows(IllegalArgumentException.class, () -> route.setSchedule(null));
        assertThrows(IllegalArgumentException.class, () -> route.setStartingStation(null));
        assertThrows(IllegalArgumentException.class, () -> route.setFinalStation(null));
        assertThrows(IllegalArgumentException.class, () -> route.setDepartureTime(null));
        assertThrows(IllegalArgumentException.class, () -> route.setArrivalTime(null));
        assertThrows(IllegalArgumentException.class, () -> route.setTravelTime(null));
    }


}
