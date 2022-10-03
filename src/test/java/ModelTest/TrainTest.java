package ModelTest;

import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrainTest {
    private static final Logger LOGGER = LogManager.getLogger(TrainTest.class);

    @BeforeAll
    static void logBefore () {
        LOGGER.info("Starting Train tests");
    }

    @Test
    void createTrain(){
        Train train = new Train();
        Map<Date, Schedule> scheduleHashMap = new HashMap<>();
        // scheduleHashMap.put(new Train(101,20));
        assertDoesNotThrow( () ->  train.setNumber(100));
        assertDoesNotThrow( () ->  train.setSeats(18));
        assertDoesNotThrow(() ->  train.setSchedules(scheduleHashMap));
    }

    @Test
    void createBadStation(){
        Train train = new Train();
        Map<Date, Schedule> scheduleHashMap = null;
        assertThrows( IllegalArgumentException.class,  () ->  train.setNumber(-1));
        assertThrows( IllegalArgumentException.class,() ->  train.setSeats(-5));
        assertThrows(IllegalArgumentException.class,() ->  train.setSchedules(scheduleHashMap));
    }
}
