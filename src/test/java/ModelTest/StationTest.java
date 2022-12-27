package ModelTest;

import com.railway.ticket.office.webapp.model.Station;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StationTest {
    @Test
    void getAndSetTest() {
        Station station = new Station();
        assertEquals(0, station.getId());
        assertNull(station.getName());

        station = new Station();
        station.setId(1);
        station.setName("TestTrain");

        assertEquals(1, station.getId());
        assertNotNull( station.getName());
    }


    @Test
    void wrongInputTest() {
        Station station = new Station();
        assertThrows(IllegalArgumentException.class,
                () -> new Station(-1, ""));
        assertThrows(IllegalArgumentException.class,
                () -> new Station(1, ""));
        assertThrows(IllegalArgumentException.class,
                () -> new Station(1, "Te"));
        assertThrows(IllegalArgumentException.class,
                () -> station.setId(-1));
        assertThrows(IllegalArgumentException.class,
                () -> station.setName(""));
        assertThrows(IllegalArgumentException.class,
                () -> station.setName(null));
        assertThrows(IllegalArgumentException.class,
                () -> station.setName("Te"));

    }


}
