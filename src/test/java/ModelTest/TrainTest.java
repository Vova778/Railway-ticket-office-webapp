package ModelTest;

import com.railway.ticket.office.webapp.model.Train;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TrainTest {
    @Test
    void getAndSetTest() {
        Train train = new Train();
        assertEquals(0, train.getNumber());
        assertEquals(0,train.getSeats());
        assertNull(train.getSchedules());

        train = new Train();
        train.setNumber(100);
        train.setSeats(10);
        train.setSchedules(new ArrayList<>());

        assertEquals(100, train.getNumber());
        assertEquals(10,train.getSeats());
        assertNotNull( train.getSchedules());
    }

    @Test
    void wrongInputTest() {
        Train train = new Train();
        assertThrows(IllegalArgumentException.class,
                () -> new Train(-1, -10));
        assertThrows(IllegalArgumentException.class,
                () -> train.setNumber(-1));
        assertThrows(IllegalArgumentException.class,
                () -> train.setSeats(-10));
        assertThrows(IllegalArgumentException.class,
                () -> train.setSchedules(null));


    }
}
