package ModelTest;

import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    @Test
    void getAndSetTest() {
        Schedule schedule = new Schedule();
        assertEquals(0, schedule.getId());
        assertNull(schedule.getDate());
        assertNull(schedule.getTrain());
        assertNull(schedule.getRoutes());

        schedule = new Schedule();
        schedule.setId(1);
        schedule.setTrain(new Train());
        schedule.setDate(Date.valueOf(LocalDate.now()));
        schedule.setRoutes(new ArrayList<>());

        assertEquals(1, schedule.getId());
        assertNotNull( schedule.getTrain());
        assertNotNull( schedule.getDate());
        assertNotNull( schedule.getRoutes());
    }
    @Test
    void wrongInputTest() {
        Schedule schedule = new Schedule();

        assertThrows(IllegalArgumentException.class, () -> schedule.setId(-1));
        assertThrows(IllegalArgumentException.class, () -> schedule.setDate(null));
        assertThrows(IllegalArgumentException.class, () -> schedule.setRoutes(null));
        assertThrows(IllegalArgumentException.class, () -> schedule.setTrain(null));
    }

}
